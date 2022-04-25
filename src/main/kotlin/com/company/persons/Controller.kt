package com.company.persons

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.BorderPane
import javafx.scene.text.Font
import java.lang.NumberFormatException


class Controller {

    var timeFlag: Boolean = false

    private var comboBoxFlag = false

    private var threadIPFlag = true

    private var threadJPFlag = true

    @FXML
    lateinit var showInfo: CheckBox

    @FXML
    private lateinit var mainPane: AnchorPane

    @FXML
    private lateinit var menuPane: BorderPane

    @FXML
    private lateinit var simulationStatistics: Label

    @FXML
    private lateinit var timeOutput: Label

    @FXML
    lateinit var startButton: Button

    @FXML
    lateinit var stopButton: Button

    @FXML
    private lateinit var showTimeButton: RadioButton

    @FXML
    private lateinit var hideTimeButton: RadioButton

    @FXML
    private lateinit var menuShowTimeButton: RadioMenuItem

    @FXML
    private lateinit var menuHideTimeButton: RadioMenuItem

    @FXML
    private lateinit var comboBoxLeft: ComboBox<String>

    @FXML
    private lateinit var comboBoxRight: ComboBox<String>

    @FXML
    private lateinit var leftTextFieldLive: TextField

    @FXML
    private lateinit var rightTextFieldLive: TextField

    @FXML
    private lateinit var leftTextFieldSpawn: TextField

    @FXML
    private lateinit var rightTextFieldSpawn: TextField

    @FXML
    private lateinit var menuShowTime: RadioMenuItem

    @FXML
    lateinit var leftThreadComboBox: ComboBox<String>

    @FXML
    lateinit var rightThreadComboBox: ComboBox<String>

    fun showInfoMenuSwitch() {
        showInfo.isSelected = showInfo.isSelected.not()
    }

    fun showInfoSwitch() {
        menuShowTime.isSelected = menuShowTime.isSelected.not()
    }

    fun addTextFields() {
        leftTextFieldSpawn.text = Habitat.instance.timeToSpawnIP.toString()
        rightTextFieldSpawn.text = Habitat.instance.timeToSpawnJP.toString()
        leftTextFieldLive.text = Habitat.instance.timeOfLiveIP.toString()
        rightTextFieldLive.text = Habitat.instance.timeOfLiveJP.toString()
    }

    private fun createAlert(str: String) {
        val alert = Alert(Alert.AlertType.ERROR)
        alert.contentText = str
        alert.showAndWait()
    }

    fun submit() {
        val defaultLive: Pair<Long, Long> = Habitat.instance.timeOfLiveIP to Habitat.instance.timeOfLiveJP
        val defaultSpawn: Pair<Int, Int> = Habitat.instance.timeToSpawnIP to Habitat.instance.timeToSpawnJP
        try {
            Habitat.instance.timeOfLiveIP = leftTextFieldLive.text.toLong()
        } catch (e: NumberFormatException) {
            createAlert("You entered the wrong number in left time of live field\nvalue of field set on previous")
            Habitat.instance.timeOfLiveIP = defaultLive.first
            leftTextFieldLive.text = defaultLive.first.toString()
        }
        try {
            Habitat.instance.timeOfLiveJP = rightTextFieldLive.text.toLong()
        } catch (e: NumberFormatException) {
            createAlert("You entered the wrong number in right time of live field\nvalue of field set on previous")
            Habitat.instance.timeOfLiveJP = defaultLive.second
            rightTextFieldLive.text = defaultLive.first.toString()
        }
        try {
            Habitat.instance.timeToSpawnIP = leftTextFieldSpawn.text.toInt()
        } catch (e: NumberFormatException) {
            createAlert("You entered the wrong number in left time to spawn field\nvalue of field set on previous")
            Habitat.instance.timeToSpawnIP = defaultSpawn.first
            leftTextFieldSpawn.text = defaultSpawn.first.toString()
        }
        try {
            Habitat.instance.timeToSpawnJP = rightTextFieldSpawn.text.toInt()
        } catch (e: NumberFormatException) {
            createAlert("You entered the wrong number in right time to spawn field\nvalue of field set on previous")
            Habitat.instance.timeToSpawnJP = defaultSpawn.second
            rightTextFieldSpawn.text = defaultSpawn.second.toString()
        }
    }

    fun selectThreadComboBoxLeft() {
        val str = leftThreadComboBox.value
        Habitat.instance.setPriorityIP(str)
    }

    fun selectThreadComboBoxRight() {
        val str = rightThreadComboBox.value
        Habitat.instance.setPriorityJP(str)
    }


    fun selectComboBoxLeft() {
        val str = comboBoxLeft.value
        val index = str.indexOf("%")
        val value = str.substring(0 until index)
        Habitat.instance.chanceOfSpawnIP = value.toInt()
    }

    fun selectComboBoxRight() {
        val str = comboBoxRight.value
        val index = str.indexOf("%")
        val value = str.substring(0 until index)
        Habitat.instance.chanceOfSpawnJP = value.toInt()
    }

    fun addComboBoxes() {
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
            comboBoxRight.value = (Habitat.instance.chanceOfSpawnJP.toString() + "%")
            comboBoxLeft.value = (Habitat.instance.chanceOfSpawnIP.toString() + "%")

            val listOfPriorities = FXCollections.observableArrayList(
                "MAX",
                "NORM",
                "MIN"
            )

            leftThreadComboBox.items = listOfPriorities
            rightThreadComboBox.items = listOfPriorities
            leftThreadComboBox.value = "NORM"
            rightThreadComboBox.value = "NORM"

            comboBoxFlag = true
        }
    }

    fun startClick() {
        Habitat.instance.startSimulation()
        startButton.isDisable = true
        stopButton.isDisable = false
    }

    fun switchTimeFlag() {
        timeFlag = timeFlag.not()
        if (timeFlag) {
            showTimeButton.isSelected = true
            menuShowTimeButton.isSelected = true
        } else {
            hideTimeButton.isSelected = true
            menuHideTimeButton.isSelected = true
        }

    }

    fun stopClick() {
        Habitat.instance.stopSimulation()
    }

    fun menuTimeRadioButtonToggle() {
        if (menuShowTimeButton.isSelected) {
            showTimeButton.isSelected = true
            timeFlag = true
            showTime()
        } else {
            hideTimeButton.isSelected = true
            timeFlag = false
            hideTime()
        }
    }

    fun timeRadioButtonToggle() {
        if (showTimeButton.isSelected) {
            menuShowTimeButton.isSelected = true
            timeFlag = true
            showTime()
        } else {
            menuHideTimeButton.isSelected = true
            timeFlag = false
            hideTime()
        }
    }

    fun hideTime() {
        timeOutput.text = ""
    }

    fun hideStatistics() {
        simulationStatistics.text = ""
    }

    fun showTime() {
        val time = Habitat.instance.time()
        val str = "Time: ${time / 1000} seconds"
        timeOutput.apply {
            font = Font("Arial", 20.0)
            layoutX = menuPane.width - 200.0
            layoutY = 100.0
            text = str
        }
    }

    fun getInformationString(time: Long): String {
        var totalOfIndividualPersons = 0
        var totalOfJuridicalPersons = 0
        Collections.vectorOfPersons.forEach {
            if (it is IndividualPerson) totalOfIndividualPersons++
            else totalOfJuridicalPersons++
        }

        return "Time of simulation: ${time / 1000}\nNumber of spawned persons: ${Collections.vectorOfPersons.size}\nNumber of spawned " +
                "individual persons $totalOfIndividualPersons\nNumber of spawned juridical persons $totalOfJuridicalPersons"
    }

    fun showInformation(time: Long) {
        simulationStatistics.apply {
            layoutX = mainPane.width / 2 - 300
            layoutY = mainPane.height / 2 - 50
            font = Font("Arial", 20.0)
            text = getInformationString(time)
        }
    }

    fun createModal(time: Long) {
        if (showInfo.isSelected)
            ModalWindow.createWindow(time)
    }

    fun currentObjectsModalWindow() {
        if (Habitat.instance.simulationStartTime.toInt() != 0) {
            Habitat.instance.stopSimulation(true)
            ObjectsInfoModalWindow.createWindow()
        } else {
            createAlert("Simulation is not started!")
        }
    }

    fun threadIPAction() {
        if (threadIPFlag) {
            Habitat.instance.threadIPWait()
            threadIPFlag = false
        } else {
            Habitat.instance.threadIPNotify()
            threadIPFlag = true
        }
    }

    fun threadJPAction() {
        if (threadJPFlag) {
            Habitat.instance.threadJPWait()
            threadJPFlag = false
        } else {
            Habitat.instance.threadJPNotify()
            threadJPFlag = true
        }
    }
}