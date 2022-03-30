package com.company.persons

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.scene.control.MenuItem
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory

class ObjectsInfoModalWindowController {
    inner class Information(val id: Int, val type: String, val bornTime: Long, val liveTime: Long)

    @FXML
    private lateinit var table: TableView<Information>

    @FXML
    private lateinit var bornTimeColumn: TableColumn<Information, Long>

    @FXML
    private lateinit var idColumn: TableColumn<Information, Int>

    @FXML
    private lateinit var liveTimeColumn: TableColumn<Information, Long>

    @FXML
    private lateinit var typeColumn: TableColumn<Information, String>

    @FXML
    private lateinit var menuCloseButton: MenuItem

    fun closeWindow(){
        ObjectsInfoModalWindow.window.close()
        Habitat.instance.resumeSimulation()
    }

    fun showInfo(hashMapOfPersons: HashMap<Int, Person>) {
        val personsList:ObservableList<Information> = FXCollections.observableArrayList()
        hashMapOfPersons.forEach{
            if (it.value is IndividualPerson)
                personsList.add(Information(it.key, "Individual Person", it.value.timeOfBorn, it.value.timeOfLive))
            else
                personsList.add(Information(it.key, "Juridical Person", it.value.timeOfBorn, it.value.timeOfLive))
        }

        idColumn.cellValueFactory = PropertyValueFactory("Id")
        bornTimeColumn.cellValueFactory = PropertyValueFactory("bornTime")
        liveTimeColumn.cellValueFactory = PropertyValueFactory("liveTime")
        typeColumn.cellValueFactory = PropertyValueFactory("Type")
        table.items = personsList
        ObjectsInfoModalWindow.window.showAndWait()
    }
}