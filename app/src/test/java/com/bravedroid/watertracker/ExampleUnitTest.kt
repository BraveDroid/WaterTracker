package com.bravedroid.watertracker

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}


class TimerTache {

//    init {
//        val timer = Timer(true)
//        val timerTask: TimerTask = TaskCreator()
//        //shedule la tache pour une futur execution dans un temps prédifini
//        timer.schedule(timerTask, 0, 1 * 1000)
//        println("TimerTask démaré")
//        //annuler après un certain temps de 10s
//        Thread.sleep(10000)
//        timer.cancel()
//        println("TimerTask arreté")
//    }

    internal class TaskCreator : TimerTask() {
        override fun run() {
            println("Commence : " + Date())
            println("currentThread name is : ${Thread.currentThread().name}")
            //println("Finit : " + Date())
        }
    }

    private val timer = Timer(true)

    fun startTracking() {
        val timer = Timer(true)
        val timerTask: TimerTask = TimerTache.TaskCreator()
        timer.schedule(timerTask, 0, 1 * 1000)
        println("TimerTask démaré")
    }

    fun stopTracking() {
        timer.cancel()
        println("TimerTask arreté")
        println("Finit : " + Date())
    }

}

fun main() {
    val ref = TimerTache()
    ref.startTracking()
    Thread.sleep(10_000)
    ref.stopTracking()
}
