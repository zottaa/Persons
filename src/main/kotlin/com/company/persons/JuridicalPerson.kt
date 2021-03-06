package com.company.persons

class JuridicalPerson(
    private val point: Point, override val timeOfBorn: Long,
    override val timeOfLive: Long, private val destinationPoint: Point
) : Person(point, timeOfBorn, timeOfLive, "JP.png", destinationPoint) {

    private var dx: Double = 0.0
    private var dy: Double = 0.0
    private val VELOCITY = 0.01
    private val startPoint: Point = point

    init {
        dx = destinationPoint.x - point.x
        dy = destinationPoint.y - point.y
    }

    override fun calculateNextStep() {
        point.x = point.x + dx * VELOCITY
        point.y = point.y + dy * VELOCITY
    }

    override fun endOfMove(): Boolean {
        val currentPoint = Point(point.x, point.y)
        return currentPoint.rangeTo(destinationPoint) < 10 || (startPoint.x in 0.0..250.0 && startPoint.y in 125.0..425.0)
    }
}