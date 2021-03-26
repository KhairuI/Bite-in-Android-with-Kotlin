package MyArray

fun main(){
    val array= arrayOf(2,3,4,5,6,7,8,9)
    val arrayTest= ArrayClass(array)
    arrayTest.check(6){ index, element ->
        println("Index: $index")
        println("Element: $element")
    }




    /*val array= arrayOf(2,3,4,5,6,7,8,9)
    val test=Test(array)
    val value= test.check(5)
    println("Index: ${value.first}")
    println("Element: ${value.second}")*/

}