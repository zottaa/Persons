package com.company.persons

import javafx.application.Application
import java.io.*
import java.net.Socket
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Client(private val socket: Socket = Socket("localhost", 8000)) : Runnable {

    override fun run() {
        val out = DataOutputStream(socket.getOutputStream())
        val input = BufferedReader(InputStreamReader(socket.getInputStream()))

        if (input.ready()) {
            //check for list or params
            if (input.readText().substring(0, 3) == "list") {
                //set list as list of other clients
                TODO()
            }
            if (input.readText().substring(0, 5) == "params") {
                //set params to curr and client with number that goes first in str
                TODO()
            }
        }
    }
}

fun main() {
    val exec: ExecutorService = Executors.newFixedThreadPool(10)
    exec.execute(Client())
}