package com.company.persons

abstract class BaseAI : Thread() {
    abstract fun move()

    abstract fun threadWait()

    abstract fun threadNotify()
}