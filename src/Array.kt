fun main(){
    val name= arrayOf("ALex", "jsdjk","sfgius","sdhfsu")
    /*for(i in name){
        println(i)
    }*/

    println(name.size)
    name.set(1,"Khairul")
    for(i in name){
        println(i)
    }

    val info=
        arrayListOf("khairul",23,'A',23.5,true)
    for(i in info){
        println(i)
    }
}