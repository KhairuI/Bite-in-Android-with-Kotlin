package Collections

fun main(){
  /*  val user= mapOf<Int,String>(1 to "Alex",2 to "Jhon")

    user.forEach { t, u ->
        println("$t -> $u")
    }*/

    val user2=
            mutableMapOf<String,String>("one" to "Alex","two" to "Jhon")
    user2.forEach { t, u ->
        println("$t -> $u")
    }

    user2["three"]="Rahim"

    user2.forEach { t, u ->
        println("$t -> $u")
    }




   /* (1,"Alex")
    (2,"Jhon")

    ("name1","Alex")
    ("name2","Alex")*/
}