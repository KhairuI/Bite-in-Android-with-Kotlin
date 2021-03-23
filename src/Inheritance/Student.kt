package Inheritance

class Student(private var name:String, private var age:Int,
              private var cgpa:String):Human(name, age) {
    fun studentShow(){
        show()
        println("The CGPA is: $cgpa")
    }
}