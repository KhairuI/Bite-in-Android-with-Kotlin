package DataClass

fun main() {

    var user=MyUser("Rahim","23")
    println(user)

    user.name="Alex"
    user.age="25"

    println("Name: ${user.component1()}")
    println("Age: ${user.component2()}")


}
