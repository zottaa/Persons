package com.company.persons

import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.stage.Stage
import javafx.scene.Scene
import javafx.stage.Modality
import javafx.stage.StageStyle


object ModalWindow {
    var window: Stage = Stage()

    private var controller: ModalWindowController

    init {
        val loader = FXMLLoader(javaClass.getResource("ModalWindow.fxml"))
        val root: Parent = loader.load()
        window.initModality(Modality.APPLICATION_MODAL)
        window.initOwner(Habitat.instance.window)
        window.initStyle(StageStyle.UNDECORATED)
        val _scene = Scene(root, 600.0, 400.0)
        controller = loader.getController()
        window.apply {
            scene = _scene
            title = "Simulation info"
        }
    }

    fun createWindow(time: Long){
        controller.modalShowInfo(time)
    }

}