import java.util.*

fun main(){
    val read= Scanner(System.`in`)
    val a= read.nextInt()
    println(findValue(a))
}

/*String findValue(int a){


    return ""
}*/

fun findValue(a:Int):String{
    val result= if(a%2==0){
        "$a is Even"
    }
    else{
        "$a is ODD"
    }
    return result
}
