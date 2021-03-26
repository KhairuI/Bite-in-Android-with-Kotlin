package MyArray

class Test(private val array:Array<Int>) {
    var index:Int= -1
    var element:Int?= null

    fun check(a:Int):Pair<Int,Int?>{
        for(i in array.indices){
            if(array[i]==a){
                index=i
                element= array[i]
                return Pair(index,element)
            }
        }
        return Pair(index,element)
    }

 //  [2,3,5,6,8,9] ---> 6

}