package com.company.persons

import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import javafx.scene.text.Font


class Controller {
    @FXML
    private lateinit var pane: AnchorPane

    @FXML
    private lateinit var output: Label

    fun hideLabel() {
        output.text = ""
    }

    fun showTime(time: Long){
        val str = "Time: ${time / 1000} seconds"
        output.apply {
            font = Font("Arial", 20.0)
            layoutX = pane.width - 200.0
            layoutY = 50.0
            text = str
        }
    }

    fun showInformation(time: Long, listOfPersons: MutableList<Person>){
        var totalOfIndividualPersons = 0
        var totalOfJuridicalPersons = 0
        listOfPersons.forEach {
            if (it is IndividualPerson) totalOfIndividualPersons++
            else totalOfJuridicalPersons++
        }

        val str = "Time of simulation: ${time/1000}\nNumber of spawned persons: ${listOfPersons.size}\nNumber of spawned " +
                "individual persons $totalOfIndividualPersons\nNumber of spawned juridical persons $totalOfJuridicalPersons"

        output.apply {
            layoutX = pane.width / 2 - 150
            layoutY = pane.height / 2 - 50
            font = Font("Arial", 20.0)
            text = str
        }
    }
}