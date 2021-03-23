package Inheritance

open class Human(private var name:String, private var age:Int) {

    fun show(){
        println("Name: $name")
        println("Age: $age")

    }
}