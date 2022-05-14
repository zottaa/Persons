package com.company.persons

import java.util.Vector
import java.util.TreeSet


object Collections {
    var vectorOfPersons = Vector<Person>()
    var treeSetOfHashCodes = TreeSet<Int>()
    var hashMapOfPersons = HashMap<Int, Person>()

    fun removeFromCollections(obj: Person) {
            vectorOfPersons.remove(obj)
            treeSetOfHashCodes.remove(obj.hashCode())
            hashMapOfPersons.remove(obj.hashCode())
    }

    fun addIntoCollections(obj: Person) {
        vectorOfPersons.add(obj)
        treeSetOfHashCodes.add(obj.hashCode())
        hashMapOfPersons[obj.hashCode()] = obj
    }

    fun clearCollections() {
        vectorOfPersons.clear()
        treeSetOfHashCodes.clear()
        hashMapOfPersons.clear()
    }
}