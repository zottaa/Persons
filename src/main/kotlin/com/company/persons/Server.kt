package com.company.persons

import java.io.DataInputStream
import java.io.DataOutput
import java.io.DataOutputStream
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Server {
    companion object {
        var execute: ExecutorService = Executors.newFixedThreadPool(10)
        var hashMapOfClients = HashMap<Int, Socket>()
    }

    fun process() {
        val server = ServerSocket(8000)
        println("Server socket created")
        var clientCount = 0
        while (!server.isClosed) {
            println("Waiting for client..")
            val client: Socket = server.accept()
            clientCount++
            hashMapOfClients[clientCount] = client
            println("LIST OF CLIENTS:\n")
            hashMapOfClients.forEach {
                println("${it.key}: ${it.value}\n")
            }
            execute.execute(MonoThreadClientHandler(client, clientCount))
            hashMapOfClients.forEach {
                val clientOutput = DataOutputStream(it.value.getOutputStream())
                clientOutput.writeUTF(hashMapOfClients.keys.toString())
                clientOutput.flush()
            }
            println("Connection accepted")
        }
        execute.shutdown()
    }
}

fun main() {
    val server = Server()
    server.process()
}
