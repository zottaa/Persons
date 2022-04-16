package com.company.persons

import kotlin.concurrent.thread

class IndividualPerson(
    private val point: Point, override val timeOfBorn: Long,
    override val timeOfLive: Long, private val destinationPoint: Point
) : Person(point, timeOfBorn, timeOfLive, "IP.jpg", destinationPoint) {

    private var dx: Double = 0.0
    private var dy: Double = 0.0
    private val VELOCITY = 0.01
    private val startPoint: Point = point
    private lateinit var _thread: Thread
    private var waitFlag = false
    private var notifyFlag = false

    init {
        dx = destinationPoint.x - point.x
        dy = destinationPoint.y - point.y
        this.start()
    }

    override fun run() {
        super.run()
        while (!endOfMove()) {
            sleep(1000)
            this.move()
        }
    }


    override fun move() {
        point.x = point.x + dx * VELOCITY
        point.y = point.y + dy * VELOCITY
    }

    override fun endOfMove(): Boolean {
        val currentPoint = Point(point.x, point.y)
        return currentPoint.rangeTo(destinationPoint) < 10 || (startPoint.x in 250.0..500.0 && startPoint.y in 425.0..750.0)
    }
}