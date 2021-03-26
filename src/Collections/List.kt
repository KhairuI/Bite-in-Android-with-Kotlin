package Collections

fun main(){
    val names= mutableListOf<String>("Alex","Rahim","Karim")
    names.add(2,"Jhon")
    names.forEach {
        println(it)
    }



  /*  val names= listOf<String>("Alex","Rahim","Karim")
    for (i in names){
        println(i)
    }
    println(names[2])

    names.forEach {
        println(it)
    }*/
}