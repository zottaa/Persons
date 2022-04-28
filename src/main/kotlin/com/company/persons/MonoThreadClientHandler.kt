package com.company.persons

import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

class MonoThreadClientHandler(
    private val clientDialog: Socket,
    private val hashMap: HashMap<Int, Socket>,
    private val isSet: Boolean = false
) : Runnable {
    override fun run() {
        val outputStream = DataOutputStream(clientDialog.getOutputStream())
        val inputStream = DataInputStream(clientDialog.getInputStream())

        while (!clientDialog.isClosed) {
            val message = "list${hashMap.toString()}"
            val entry = inputStream.readUTF()
            println("Read from client $entry")

            if (entry.equals("close", ignoreCase = true)) {
                println("Client initialize connections suicide))..")
                break
            }
            outputStream.writeUTF(message)
            outputStream.flush()
        }
        outputStream.close()
        inputStream.close()
        clientDialog.close()
        println("All closed")
    }

}