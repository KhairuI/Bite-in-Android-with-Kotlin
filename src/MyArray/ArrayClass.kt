package MyArray

class ArrayClass(private val array:Array<Int>) {

    fun check(a:Int,found:(index:Int,element:Int?) -> Unit){
        for(i in array.indices){
            if(array[i] == a){
                found(i,array[i])
                return
            }
        }
        found(-1,null)
        return

    }
}