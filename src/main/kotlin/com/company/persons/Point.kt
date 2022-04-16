package com.company.persons

import kotlin.math.pow
import kotlin.math.sqrt

data class Point(var x: Double, var y: Double) {

    fun rangeTo(otherPoint: Point) = sqrt((otherPoint.x - x).pow(2) + (otherPoint.y - y).pow(2))
}
