package GetterSetter

class Model {
    var a:Int=0
    set(value) {
        field= value
    }

    var b:Int=0
        set(value) {
            field= value
        }

    val sum
        get()= a+b
}


