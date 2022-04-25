package com.company.persons

import javafx.application.Application
import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.input.KeyCode
import javafx.scene.layout.Pane
import javafx.stage.Stage
import java.util.*
import kotlin.concurrent.thread
import kotlin.random.Random
import kotlin.system.exitProcess


class Habitat : Application() {
    private var timer: Timer = Timer()
    private var simulationTime: Long = 0
    private lateinit var pane: Pane
    private lateinit var scene: Scene
    private lateinit var threadJP: JuridicalPersonThread
    private lateinit var threadIP: IndividualPersonThread
    val lock = Object()
    lateinit var mainThread: Thread
    lateinit var controller: Controller
    var simulationStartTime: Long = 0
    var timeToSpawnIP: Int = 5
    var timeToSpawnJP: Int = 3
    var chanceOfSpawnIP: Int = 0
    var chanceOfSpawnJP: Int = 100
    var timeOfLiveIP: Long = 1000
    var timeOfLiveJP: Long = 1000
    lateinit var window: Stage

    companion object {
        lateinit var instance: Habitat
    }


    override fun start(stage: Stage) {
        instance = this
        mainThread = Thread.currentThread()
        val loader = FXMLLoader(javaClass.getResource("View.fxml"))
        val root: Parent = loader.load()
        controller = loader.getController()
        scene = Scene(root, 800.0, 800.0)
        threadIP = IndividualPersonThread()
        threadJP = JuridicalPersonThread()
        pane = loader.getRoot()
        scene.setOnKeyPressed { event ->
            when (event.code) {
                KeyCode.B -> {
                    if (simulationStartTime.toInt() == 0) controller.hideStatistics()
                    controller.startClick()
                }

                KeyCode.E -> {
                    controller.stopClick()
                }

                KeyCode.T -> {
                    controller.switchTimeFlag()
                    if (controller.timeFlag)
                        controller.showTime()
                    else
                        controller.hideTime()
                }

                else -> println("Unknown button")
            }
        }
        stage.apply {
            scene = this@Habitat.scene
            title = "Persons"
            icons.add(Image("icon.png"))
            setOnCloseRequest { _ ->
                Platform.exit()
                exitProcess(0)
            }
            show()
        }
        window = stage
        controller.addComboBoxes()
        controller.addTextFields()
    }

    fun time(): Long {
        return if (simulationStartTime.toInt() != 0)
            (System.currentTimeMillis() - simulationStartTime)
        else
            0.toLong()
    }

    fun resumeSimulation() {
        startSimulation(simulationTime)
    }

    fun stopSimulation(objectsModal: Boolean = false) {
        simulationTime = System.currentTimeMillis() - simulationStartTime
        if (objectsModal) {
            timer.cancel()
        } else {
            if (controller.timeFlag) controller.showTime()
            if (controller.showInfo.isSelected) {
                timer.cancel()
                controller.createModal(time())
            } else {
                endSimulation()
            }
        }
    }

    fun endSimulation() {
        controller.stopButton.isDisable = true
        controller.startButton.isDisable = false
        if (simulationStartTime.toInt() != 0) {
            controller.showInformation(simulationTime)
            clearHabitat()
            timer.cancel()
            timer = Timer()
            Collections.clearCollections()
            simulationStartTime = 0
        }
    }

    fun startSimulation(previousSimulationTime: Long = 0) {
        threadIP.start()
        threadJP.start()
        this.setPriorityIP(controller.leftThreadComboBox.value)
        this.setPriorityJP(controller.rightThreadComboBox.value)
        timer = Timer()
        controller.submit()
        controller.hideStatistics()
        if (previousSimulationTime.toInt() == 0)
            clearHabitat()
        simulationStartTime = System.currentTimeMillis() - previousSimulationTime
        val task: TimerTask = object : TimerTask() {
            override fun run() {
                Platform.runLater {
                    update(System.currentTimeMillis() - simulationStartTime)
                    moveObjects()
                }
            }
        }
        timer.scheduleAtFixedRate(task, 1000, 1000)
    }

    fun threadIPWait() {
        threadIP.threadWait()
    }

    fun threadJPWait() {
        threadJP.threadWait()
    }

    fun threadIPNotify() {
        threadIP.threadNotify()
    }

    fun threadJPNotify() {
        threadJP.threadNotify()
    }

    fun setPriorityIP(str: String) {
        when(str) {
            "MAX" -> threadIP.priority = Thread.MAX_PRIORITY

            "NORM" -> threadIP.priority = Thread.NORM_PRIORITY

            "MIN" -> threadIP.priority = Thread.MIN_PRIORITY
        }
    }

    fun setPriorityJP(str: String) {
        when(str) {
            "MAX" -> threadJP.priority = Thread.MAX_PRIORITY

            "NORM" -> threadJP.priority = Thread.NORM_PRIORITY

            "MIN" -> threadJP.priority = Thread.MIN_PRIORITY
        }
    }

    private fun update(currentTime: Long) {
        if (((currentTime / 1000) % timeToSpawnIP).toInt() == 0) {
            if (Random.nextInt(0, 99) < chanceOfSpawnIP) {
                createIP()
            }
        }
        if (((currentTime / 1000) % timeToSpawnJP).toInt() == 0) {
            if (Random.nextInt(0, 99) < chanceOfSpawnJP) {
                createJP()
            }
        }
        if (controller.timeFlag) controller.showTime()

        checkObjectsTime()
    }

    private fun moveObjects() {
        Collections.vectorOfPersons.forEach {
            it.getImageView()
        }
    }

    private fun checkObjectsTime() {
        val removeObjects: MutableList<Person> = mutableListOf()
        Collections.vectorOfPersons.forEach {
            if (it.timeOfBorn + it.timeOfLive <= (System.currentTimeMillis() - simulationStartTime) / 1000) {
                pane.children.remove(it.getImageView())
                removeObjects.add(it)
            }
        }

        removeObjects.forEach {
            Collections.removeFromCollections(it)
        }
        removeObjects.clear()
    }

    private fun clearHabitat() {
        Collections.vectorOfPersons.forEach {
            pane.children.remove(it.getImageView())
        }
    }

    private fun createIP() {
        val x = Random.nextDouble(0.0, scene.width - 300)
        val y = Random.nextDouble(50.0, scene.height - 50)
        val destinationPoint = Point(Random.nextDouble(250.0, 500.0), Random.nextDouble(425.0, 750.0))
        val individualPerson =
            IndividualPerson(
                Point(x, y),
                (System.currentTimeMillis() - simulationStartTime) / 1000,
                timeOfLiveIP,
                destinationPoint
            )
        pane.children.add(individualPerson.getImageView())
        Collections.addIntoCollections(individualPerson)
    }

    private fun createJP() {
        val x = Random.nextDouble(0.0, scene.width - 300)
        val y = Random.nextDouble(50.0, scene.height - 50)
        val destinationPoint = Point(Random.nextDouble(0.0, 250.0), Random.nextDouble(50.0, 425.0))
        val juridicalPerson =
            JuridicalPerson(
                Point(x, y),
                (System.currentTimeMillis() - simulationStartTime) / 1000,
                timeOfLiveJP,
                destinationPoint
            )
        pane.children.add(juridicalPerson.getImageView())
        Collections.addIntoCollections(juridicalPerson)
    }
}

fun main(args: Array<String>) {
    Application.launch(Habitat::class.java, *args)
}
