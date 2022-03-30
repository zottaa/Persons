package com.company.persons

class IndividualPerson(private var x : Double, private var y: Double, override val timeOfBorn: Long,
                       override val timeOfLive: Long) : Person(x, y, timeOfBorn, timeOfLive, "IP.png")