package com.company.persons

import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.stage.StageStyle

object ObjectsInfoModalWindow {
    var window: Stage = Stage()

    private var controller: ObjectsInfoModalWindowController

    init {
        val loader = FXMLLoader(javaClass.getResource("ObjectsInfoModalWindow.fxml"))
        val root: Parent = loader.load()
        window.initModality(Modality.APPLICATION_MODAL)
        window.initOwner(Habitat.instance.window)
        window.initStyle(StageStyle.UTILITY)
        val _scene = Scene(root, 600.0, 400.0)
        controller = loader.getController()
        window.apply {
            scene = _scene
            title = "Objects info"
            setOnCloseRequest { controller.closeWindow() }
        }
    }

    fun createWindow(){
        controller.showInfo(Collections.hashMapOfPersons)
    }
}