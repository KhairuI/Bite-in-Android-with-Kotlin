package Collections

fun main(){
    /*val names= setOf<Int>(1,2,3,3,4,4,5,6,7)
    names.forEach {
        println(it)
    }*/

    val names2= mutableSetOf<Int>(1,2,4,4,5,6,7)
    names2.add(3)
    names2.add(3)

    names2.forEach {
        println(it)
    }
}