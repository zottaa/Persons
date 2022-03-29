package com.company.persons

import java.util.Vector
import java.util.TreeSet


/**
 * Collections and some methods for them
 */
object Collections {
    var vectorOfPersons = Vector<Person>()
    var treeSetOfHashCodes = TreeSet<Int>()
    var hashMapOfPersons = HashMap<Int, Person>()

    fun removeFromCollections(obj: Person){
        vectorOfPersons.remove(obj)
        treeSetOfHashCodes.remove(obj.hashCode())
        hashMapOfPersons.remove(obj.hashCode())
    }
}