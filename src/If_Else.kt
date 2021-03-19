import java.util.*

fun main(){
    val read= Scanner(System.`in`)
    // check vowel
    println("Enter a Character: ")
    val ch= read.next().single()
    val res= when(ch){
        'a' -> "Vowel"
        'e' -> "Vowel"
        'i' -> "Vowel"
        'o' -> "Vowel"
        'u' -> "Vowel"
        'A' -> "Vowel"
        'E' -> "Vowel"
        'I' -> "Vowel"
        'O' -> "Vowel"
        'U' -> "Vowel"
        else -> "Consonant"
    }
    println(res)

    println("Enter a number:")
    val b= read.nextInt()
    when(b){
        in 1..5 -> println("$b is between 1 and 5")
        in 5..10 -> println("$b is between 5 and 10")
        else -> println("$b is more than 10 or less then 0")
    }


    // check odd Even...
   /* println("Enter a number: ")
    val a= read.nextInt()
    println(if(a%2==0) "$a is Even" else "$a is Odd")*/

    // Character
    /*println("Enter a Character: ")
    val ch= read.next().single()*/

    /*if( ch in 'a'..'z'){  // in - include a & z
        println("$ch is small letter")

    }
    else if(ch in 'A'..'Z'){
        println("$ch is capital letter")
    }
    else{
        println("$ch is not Capital or Small letter")
    }*/
  /*  val result= if(ch in 'a'..'z'){
        "$ch is small letter"
    }
    else if(ch in 'A'..'Z'){
        "$ch is Capital letter"
    }
    else{
        "$ch is not Capital or Small letter"
    }
    println(result)*/



}