package com.company.persons

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.BorderPane
import javafx.scene.text.Font
import java.lang.NumberFormatException
import kotlin.system.exitProcess


class Controller {

    var timeFlag : Boolean = false

    var comboBoxFlag = false

    @FXML
    private lateinit var showInfo: CheckBox

    @FXML
    private lateinit var mainPane: AnchorPane

    @FXML
    private lateinit var menuPane: BorderPane

    @FXML
    private lateinit var simulationStatistics: Label

    @FXML
    private lateinit var timeOutput: Label

    @FXML
    private lateinit var startButton: Button

    @FXML
    private lateinit var stopButton: Button

    @FXML
    private lateinit var showTimeButton: RadioButton

    @FXML
    private lateinit var hideTimeButton: RadioButton

    @FXML
    private lateinit var menuShowTimeButton: RadioMenuItem

    @FXML
    private lateinit var menuHideTimeButton: RadioMenuItem

    @FXML
    private lateinit var comboBoxLeft : ComboBox<String>

    @FXML
    private lateinit var comboBoxRight : ComboBox<String>

    @FXML
    private lateinit var leftTextField: TextField

    @FXML
    private lateinit var rightTextField: TextField


    fun showInformationCheckBox(){

    }

    fun addTextFields(){
        leftTextField.text = Habitat.instance.timeToSpawnIP.toString()
        rightTextField.text = Habitat.instance.timeToSpawnJP.toString()
    }

    fun createAlert(str: String){
        val alert = Alert(Alert.AlertType.ERROR)
        alert.contentText = str
        alert.showAndWait()
    }

    fun submit(){
        val default : Pair<Int, Int> = Habitat.instance.timeToSpawnIP to Habitat.instance.timeToSpawnJP
        try {
            Habitat.instance.timeToSpawnIP = leftTextField.text.toInt()
        } catch (e : NumberFormatException){
            createAlert("You entered the wrong number in left field\nvalue of field set on previous")
            Habitat.instance.timeToSpawnIP = default.first
            leftTextField.text = default.first.toString()
        }
        try {
            Habitat.instance.timeToSpawnJP = rightTextField.text.toInt()
        } catch (e : NumberFormatException){
            createAlert("You entered the wrong number in right field\nvalue of field set on previous")
            Habitat.instance.timeToSpawnJP = default.second
            rightTextField.text = default.second.toString()
        }
    }

    fun selectComboBoxLeft(){
        val str = comboBoxLeft.value
        val index = str.indexOf("%")
        val value = str.substring(0 until index)
        Habitat.instance.chanceOfSpawnIP = value.toInt()
    }

    fun selectComboBoxRight(){
        val str = comboBoxRight.value
        val index = str.indexOf("%")
        val value = str.substring(0 until index)
        Habitat.instance.chanceOfSpawnJP = value.toInt()
    }

    fun addComboBoxes(){
        if (!comboBoxFlag) {
            val listOfProbability = FXCollections.observableArrayList(
                "0%",
                "10%",
                "20%",
                "30%",
                "40%",
                "50%",
                "60%",
                "70%",
                "80%",
                "90%",
                "100%"
            )
            comboBoxLeft.items = listOfProbability
            comboBoxRight.items = listOfProbability
            comboBoxFlag = true
            comboBoxRight.value = (Habitat.instance.chanceOfSpawnJP.toString() + "%")
            comboBoxLeft.value = (Habitat.instance.chanceOfSpawnIP.toString() + "%")
        }
    }

    fun startClick(){
        Habitat.instance.startSimulation()
        startButton.isDisable = true
        stopButton.isDisable = false
    }

    fun switchTimeFlag(){
        timeFlag = timeFlag.not()
        if (timeFlag) {
            showTimeButton.isSelected = true
            menuShowTimeButton.isSelected = true
        }
        else {
            hideTimeButton.isSelected = true
            menuHideTimeButton.isSelected = true
        }

    }

    fun stopClick(){
        Habitat.instance.stopSimulation()
        stopButton.isDisable = true
        startButton.isDisable = false
    }

    fun menuTimeRadioButtonToggle(){
        if (menuShowTimeButton.isSelected){
            showTimeButton.isSelected = true
            timeFlag = true
            showTime()
        } else {
            hideTimeButton.isSelected = true
            timeFlag = false
            hideTime()
        }
    }

    fun timeRadioButtonToggle(){
        if (showTimeButton.isSelected){
            menuShowTimeButton.isSelected = true
            timeFlag = true
            showTime()
        } else {
            menuHideTimeButton.isSelected = true
            timeFlag = false
            hideTime()
        }
    }

    fun hideTime(){
        timeOutput.text = ""
    }

    fun hideStatistics() {
        simulationStatistics.text = ""
    }

    fun showTime(){
        val time = Habitat.instance.time()
        val str = "Time: ${time / 1000} seconds"
        timeOutput.apply {
            font = Font("Arial", 20.0)
            layoutX = menuPane.width - 200.0
            layoutY = 100.0
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

        simulationStatistics.apply {
            layoutX = mainPane.width / 2 - 300
            layoutY = mainPane.height / 2 - 50
            font = Font("Arial", 20.0)
            text = str
        }
    }
}