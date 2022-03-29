package com.company.persons

class JuridicalPerson(private var x : Double, private var y: Double, override val timeOfBorn: Long,
                      override val timeOfLive: Long) : Person(x, y, timeOfBorn, timeOfLive,"JP.png")