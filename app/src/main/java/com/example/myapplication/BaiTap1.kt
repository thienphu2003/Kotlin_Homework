package com.example.myapplication

fun main(array: Array<String>){
    println("Enter a char or string to string to test")
    val test = readLine()?.filter { !it.isWhitespace() } ?: ""
    val converter = MorseConverter()
    var array = test.map { value -> converter.converterFromStringToMorse(value) }
    var result =array.joinToString(" ")
    println("The morse result is: $result")
    val test2 = readln()?.split(" ")
    val result2 =test2?.map { value -> converter.covertFromMorseToString(value) }
    println(result2?.joinToString(""))

}
class MorseConverter{

    private var map : MutableMap<Char,String>

    constructor() {
        map = mutableMapOf()
        map.putAll(mapOf('a' to ".-",
            'b' to "-...",
            'c' to "-.-.",
            'd' to "-..",
            'e' to ".",
            'f' to "..-.",
            'g' to "--.",
            'h' to "....",
            'i' to "..",
            'j' to ".---",
            'k' to "-.-",
            'l' to ".-..",
            'm' to "--",
            'n' to "-.",
            'o' to "---",
            'p' to ".--.",
            'q' to "--.-",
            'r' to ".-.",
            's' to "...",
            't' to "-",
            'u' to "..-",
            'v' to "...-",
            'w' to ".--",
            'x' to "-..-",
            'y' to "-.--",
            'z' to "--..",
            '1' to ".----",
            '2' to "..---",
            '3' to "...--",
            '4' to "....-",
            '5' to ".....",
            '6' to "-....",
            '7' to "--...",
            '8' to "---..",
            '9' to "----.",
            '0' to "-----"))

    }

    fun converterFromStringToMorse(item: Char): String {
        return map[item].toString()
    }
    fun covertFromMorseToString(morse: String): String?{
        val result = map.entries.firstOrNull { it.value == morse }
        return result?.key?.toString()
    }
}