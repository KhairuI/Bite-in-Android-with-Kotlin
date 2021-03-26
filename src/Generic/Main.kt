package Generic

fun main(){

    val array= arrayOf(2,3,4,5,6,7,8,9)
    val array2= arrayOf("rahim","Alex","Jhon")
    val ar= Gene(array)
    ar.check(4){ index, element ->
        println("Index: $index")
        println("Element: $element")

    }

    val ar2= Gene(array2)
    ar2.check("Alex"){ index, element ->
        println("Index: $index")
        println("Element: $element")
    }
}