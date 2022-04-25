package com.company.persons

class IndividualPersonThread : BaseAI() {

    private var isWait: Boolean = false

    override fun run() {
        while (true) {
            synchronized(this) {
                if (isWait)
                    with(this as Object) {
                        wait()
                    }
                super.run()
                Thread.sleep(1000)
                move()
            }
        }
    }

    override fun move() {
        Collections.vectorOfPersons.forEach {
            if (it is IndividualPerson) {
                if (!it.endOfMove()) {
                    it.calculateNextStep()
                }
            }
        }
    }

    override fun threadWait() {
        isWait = isWait.not()
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