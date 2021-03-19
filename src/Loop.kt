import java.util.*

fun main(){


   /* for (i in 1..10){ // include 1 & 10
        println(i)
    }*/
    /*for (i in 10 downTo 1){ // include 1 & 10
        println(i)
    }*/
    val read= Scanner(System.`in`)
    val a= read.nextInt()
    /*for (i in 1 until a){ // not included a=10
        println(i)
    }*/

    for (i in a downTo  1 step 2){ // not included a=10
        println(i)
    }

}