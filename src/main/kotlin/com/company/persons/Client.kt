package com.company.persons

import java.io.*
import java.net.Socket
import javafx.application.Platform

class Client(private val socket: Socket = Socket("localhost", 8000)) : Runnable {

    var message: String = ""

    override fun run() {
        val input = DataInputStream(socket.getInputStream())
        while (!socket.isClosed) {
            var message = input.readUTF()
            if (message.contains("close")) {
                socket.close()
            }
            if (message.contains("param")) {
                val probability = message.substring(message.indexOf("\n") + 1).toInt()
                println(probability)
                if (message.contains("IP")) {
                    Platform.runLater {
                        Habitat.instance.controller.changeComboBoxLeft(probability)
                    }
                } else {
                    Platform.runLater {
                        Habitat.instance.controller.changeComboBoxRight(probability)
                    }
                }
            } else {
                message = message.replace("[\\[\\]]".toRegex(), "")
                Platform.runLater {
                    Habitat.instance.controller.addListView(message)
                }
            }
        }
        input.close()
    }

    fun close() {
        val out = DataOutputStream(socket.getOutputStream())
        out.writeUTF("quit")
    }

    fun change(message: String) {
        val out = DataOutputStream(socket.getOutputStream())
        out.writeUTF(message)
    }
}