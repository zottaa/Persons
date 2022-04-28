package com.company.persons

import javafx.application.Application
import java.io.*
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.security.auth.login.AppConfigurationEntry

class Server {
    companion object {
        var execute: ExecutorService = Executors.newFixedThreadPool(5)
    }

    fun process() {
        val server = ServerSocket(8000)
        println("Server socket created")
        var clientCount = 0
        val hashMapOfClients = HashMap<Int, Socket>()
        while (!server.isClosed) {
            println("Waiting for client..")
            val client: Socket = server.accept()
            clientCount++
            hashMapOfClients[clientCount] = client
            println("LIST OF CLIENTS:\n")
            hashMapOfClients.forEach {
                println("${it.key}: ${it.value}\n")
            }
            println("if u want to set P of spawn on current client and client from list print YES, either print NO")
            val serverCommand = readLine()
            if (serverCommand.equals("YES", ignoreCase = true)){
                execute.execute(MonoThreadClientHandler(client,hashMapOfClients,true))
            }
            else {
                execute.execute(MonoThreadClientHandler(client, hashMapOfClients, false))
            }
            println("Connection accepted")
        }
        execute.shutdown()
    }
}

fun main() {
    //val server = Server()
    //server.process()
}
