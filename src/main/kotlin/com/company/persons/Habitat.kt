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
import kotlin.random.Random
import kotlin.system.exitProcess


class Habitat : Application() {
    private var timer : Timer = Timer()
    private var simulationTime: Long = 0
    private lateinit var pane : Pane
    private lateinit var scene : Scene
    lateinit var controller : Controller
    var simulationStartTime: Long = 0
    var timeToSpawnIP : Int = 1
    var timeToSpawnJP : Int = 1
    var chanceOfSpawnIP : Int = 50
    var chanceOfSpawnJP : Int = 50
    var timeOfliveIP: Long = 10
    var timeOfliveJP: Long = 10
    lateinit var window: Stage
    companion object {
        lateinit var instance: Habitat
    }



    override fun start(stage: Stage) {
        instance = this
        val loader = FXMLLoader(javaClass.getResource("View.fxml"))
        val root: Parent = loader.load()
        controller = loader.getController()
        scene = Scene(root, 800.0, 800.0)
        pane = loader.getRoot()
        scene.setOnKeyPressed { event ->
            when(event.code){
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
            setOnCloseRequest { _->
                Platform.exit()
                exitProcess(0)
            }
            show()
        }
        window = stage
        controller.addComboBoxes()
        controller.addTextFields()
    }

    fun time() : Long{
        return if (simulationStartTime.toInt() != 0)
            (System.currentTimeMillis() - simulationStartTime)
        else
            0.toLong()
    }

    fun resumeSimulation(){
        startSimulation(simulationTime)
    }

    fun stopSimulation(objectsModal: Boolean = false){
        simulationTime = System.currentTimeMillis() - simulationStartTime
        if (objectsModal){
            timer.cancel()
        }
        else {
            if (controller.timeFlag) controller.showTime()
            if (controller.showInfo.isSelected) {
                println(1)
                timer.cancel()
                controller.createModal(time())
            } else {
                endSimulation()
            }
        }
    }

    fun endSimulation(){
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

    fun startSimulation(previousSimulationTime: Long = 0){
        timer = Timer()
        controller.submit()
        controller.hideStatistics()
        if (previousSimulationTime.toInt() == 0)
            clearHabitat()
        simulationStartTime = System.currentTimeMillis() - previousSimulationTime
        val task: TimerTask = object : TimerTask() {
            override fun run() {
                Platform.runLater { update(System.currentTimeMillis() - simulationStartTime) }
            }
        }
        timer.scheduleAtFixedRate(task, 1000, 1000)
    }

    private fun update(currentTime: Long){
        if (((currentTime / 1000) % timeToSpawnIP).toInt() == 0){
            if (Random.nextInt(0, 99) < chanceOfSpawnIP){
                createIP()
            }
        }
        if (((currentTime / 1000) % timeToSpawnJP).toInt() == 0){
            if (Random.nextInt(0, 99) < chanceOfSpawnJP){
                createJP()
            }
        }
        if (controller.timeFlag) controller.showTime()
        checkObjectsTime()
    }

    private fun checkObjectsTime(){
        val removeObjects: MutableList<Person> = mutableListOf()
        Collections.vectorOfPersons.forEach {
            if (it.timeOfBorn + it.timeOfLive <= (System.currentTimeMillis() - simulationStartTime) / 1000){
                pane.children.remove(it.getImageView())
                removeObjects.add(it)
            }
        }

        removeObjects.forEach{
            Collections.removeFromCollections(it)
        }
        removeObjects.clear()
    }

    private fun clearHabitat() {
        Collections.vectorOfPersons.forEach {
            pane.children.remove(it.getImageView())
        }
    }

    private fun createIP(){
        val x = Random.nextDouble(0.0, scene.width - 300)
        val y = Random.nextDouble(100.0, scene.height - 50)
        val individualPerson = IndividualPerson(x, y, (System.currentTimeMillis() - simulationStartTime) / 1000, timeOfliveIP)
        pane.children.add(individualPerson.getImageView())
        Collections.addIntoCollections(individualPerson)
    }

    private fun createJP(){
        val x = Random.nextDouble(0.0, scene.width - 300)
        val y = Random.nextDouble(100.0, scene.height - 50)
        val juridicalPerson = JuridicalPerson(x, y, (System.currentTimeMillis() - simulationStartTime) / 1000, timeOfliveJP)
        pane.children.add(juridicalPerson.getImageView())
        Collections.addIntoCollections(juridicalPerson)
    }
}

fun main(args: Array<String>){
    Application.launch(Habitat::class.java, *args)
}
