package com.company.persons

abstract class BaseAI : Thread() {
    abstract fun move()
    abstract fun endOfMove() : Boolean
}