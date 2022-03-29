package com.company.persons

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.MenuItem
import javafx.scene.control.TextArea
import javafx.scene.layout.AnchorPane
import javafx.scene.text.Font

class ModalWindowController {



    @FXML
    lateinit var pane: AnchorPane
    @FXML
    lateinit var okButton: Button
    @FXML
    lateinit var cancelButton: Button
    @FXML
    lateinit var okMenu: MenuItem
    @FXML
    lateinit var cancelMenu: MenuItem
    @FXML
    lateinit var modalTextArea: TextArea

    fun modalShowInfo(time: Long){
        val mainController:Controller = Habitat.instance.controller
        modalTextArea.apply {
            font = Font("Arial", 20.0)
            text = mainController.getInformationString(time)
            isEditable = false
        }
        ModalWindow.window.showAndWait()
    }

    fun okClick(){
        Habitat.instance.endSimulation()
        ModalWindow.window.close()
    }

    fun cancelClick(){
        Habitat.instance.resumeSimulation()
        ModalWindow.window.close()
    }

}