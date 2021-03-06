package com.company.persons

class JuridicalPersonThread : BaseAI() {

    private var isWait: Boolean = false

    override fun run() {
        while (true) {
            synchronized(this) {
                if (isWait)
                    with(this as Object) {
                        wait()
                    }
            }
            synchronized(Collections.vectorOfPersons) {
                move()
            }
            Thread.sleep(1000)
        }
    }

    override fun move() {
        Collections.vectorOfPersons.forEach {
            if (it is JuridicalPerson) {
                if (!it.endOfMove()) {
                    it.calculateNextStep()
                }
            }
        }
    }

    override fun threadWait() {
        synchronized(Collections.vectorOfPersons) {
            isWait = isWait.not()
        }
    }

    override fun threadNotify() {
        synchronized(this) {
            with(this as Object) {
                notify()
            }
            isWait = false
        }
    }
}