import java.util.*

fun main(){
    val read= Scanner(System.`in`)
    println("Bite in Android")
    print("With Kotlin")

    // User Input...
    println("Enter your name: ")
    //val name= readLine()
    val name= read.nextLine()
    println("Enter your age: ")
    //val age= Integer.valueOf(readLine())
    val  age= read.nextInt()
    println("Your name is $name and Age is $age")

  /*  // difference val and var
    var a=5
    println("The value of a= $a")
    a=7
    println("The value of a= ")*/
}