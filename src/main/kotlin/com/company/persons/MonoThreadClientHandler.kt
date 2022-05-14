package com.company.persons

import javafx.application.Platform
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

class MonoThreadClientHandler(
    private val clientDialog: Socket, private val key: Int
) : Runnable {
    override fun run() {
        val outputStream = DataOutputStream(clientDialog.getOutputStream())
        val inputStream = DataInputStream(clientDialog.getInputStream())

        while (!clientDialog.isClosed) {
            val message = inputStream.readUTF()
            if (message.contains("quit")) {
                Server.hashMapOfClients.remove(key)
                Server.hashMapOfClients.forEach {
                    val clientOutput = DataOutputStream(it.value.getOutputStream())
                    clientOutput.writeUTF(Server.hashMapOfClients.keys.toString())
                    clientOutput.flush()
                }
                outputStream.writeUTF("close")
                clientDialog.close()
            }

            if (message.contains("param")) {
                val index = message.indexOf("\n")
                val str = message.substring(index + 1)
                val clientNumber = str.substring(0, str.indexOf("\n")).toInt()
                val client = Server.hashMapOfClients[clientNumber]

                val probability = str.substring(str.indexOf("\n") + 1).toInt()

                val clientOutput = DataOutputStream(client!!.getOutputStream())

                val parameterMessage = if (message.contains("IP"))
                    "paramIP\n$probability"
                else
                    "paramJP\n$probability"

                clientOutput.writeUTF(parameterMessage)
            }
        }
        inputStream.close()
        println("All closed")
    }
}