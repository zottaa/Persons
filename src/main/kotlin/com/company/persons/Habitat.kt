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


class Habitat : Application() {

    private var listOfPersons : MutableList<Person> = mutableListOf()
    private var timer : Timer = Timer()
    private var simulationStartTime: Long = 0
    private lateinit var _pane : Pane
    private lateinit var _scene : Scene
    private lateinit var _controller : Controller
    private val timeToSpawnIP : Int = 1
    private val timeToSpawnJP : Int = 1
    private val chanceOfSpawnIP : Int = 50
    private val chanceOfSpawnJP : Int = 50
    private var timeFlag: Boolean = false

    override fun start(stage: Stage) {
        val loader = FXMLLoader(javaClass.getResource("View.fxml"))
        val root: Parent = loader.load()
        _controller = loader.getController()
        _scene = Scene(root, 900.0, 900.0)
        _pane = loader.getRoot()

        _scene.setOnKeyPressed { event ->
            when(event.code){
                KeyCode.B -> {
                    if (simulationStartTime.toInt() == 0) _controller.hideLabel()
                    startSimulation()
                }

                KeyCode.E -> {
                    _controller.showInformation(System.currentTimeMillis() - simulationStartTime, listOfPersons)
                    clearHabitat()
                    timer.cancel()
                    timer = Timer()
                    listOfPersons.clear()
                    simulationStartTime = 0
                }

                KeyCode.T -> {
                    timeFlag = timeFlag.not()
                    if (!timeFlag) _controller.hideLabel()
                    else _controller.showTime(System.currentTimeMillis() - simulationStartTime)
                }

                else -> println("Unknown button")
            }
        }
        stage.apply {
            scene = _scene
            title = "Persons"
            icons.add(Image("icon.png"))
            show()
        }
    }

    private fun startSimulation(){
        simulationStartTime = System.currentTimeMillis()
        val task: TimerTask = object : TimerTask() {
            override fun run() {
                Platform.runLater { update(System.currentTimeMillis() - simulationStartTime) }
            }
        }
        timer.scheduleAtFixedRate(task, 1000, 1000)
    }

    private fun update(currentTime: Long){
        if (((currentTime / 1000) % timeToSpawnIP).toInt() == 0){
            if (Random.nextInt(0, 100) < chanceOfSpawnIP){
                createIP()
            }
        }

        if (((currentTime / 1000) % timeToSpawnJP).toInt() == 0){
            if (Random.nextInt(0, 99) < chanceOfSpawnJP){
                createJP()
            }
        }
        if (timeFlag) _controller.showTime(currentTime)
    }

    private fun clearHabitat() {
        listOfPersons.forEach {
            _pane.children.remove(it.getImageView())
        }
    }

    private fun createIP(){
        val x = Random.nextDouble(0.0, _scene.width - 300)
        val y = Random.nextDouble(0.0, _scene.height - 50)

        val individualPerson = IndividualPerson(x, y)

        _pane.children.add(individualPerson.getImageView())
        listOfPersons.add(individualPerson)
    }

    private fun createJP(){
        val x = Random.nextDouble(0.0, _scene.width - 250)
        val y = Random.nextDouble(0.0, _scene.height - 50)

        val juridicalPerson = JuridicalPerson(x, y)
        _pane.children.add(juridicalPerson.getImageView())
        listOfPersons.add(juridicalPerson)
    }

    companion object{
        @JvmStatic
        fun main(args: Array<String>){
            launch (Habitat::class.java, *args)
        }
    }
}

