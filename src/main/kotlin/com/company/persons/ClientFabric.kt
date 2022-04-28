package com.company.persons

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

fun main() {
    val exec: ExecutorService = Executors.newFixedThreadPool(10)
    exec.execute(Client())
    Thread.sleep(10)
}