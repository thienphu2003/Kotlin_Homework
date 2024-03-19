
package com.example.myapplication

import android.icu.text.ListFormatter.Width

fun main(array: Array<String>) {
    println("Hello World")
    val r = Rectangle()
    print("Width: ")
    r.width = readLine()?.toDouble() ?: 0.0
    print("Height: ")
    r.setHeight(readLine()?.toDouble() ?: 0.0)
    println("Area : ${r.area}")
    val r1 = Rectangle()
    val r2 = Rectangle(5.0,6.0)
    print("r1 : ${r1.area}")
    print("r2 : ${r2.area}")
    println("Object : $r")
    val shapes = ArrayList<Shape>()
    shapes.add(Rectangle(3.0,4.0))
    shapes.add(Circle(1.0))
    shapes.add(Circle(2.0))
    var total = 0.0
    for(s in shapes){
        println(s.area)
        total +=s.area
    }
    println("Total: ${total}")

    val numbers :MutableList<Double> = mutableListOf()
    numbers.add(5.0)
    for(i in 0..<numbers.size){
        println(numbers[i])
    }
    val ints : MutableSet<Int> = mutableSetOf()
    ints.addAll(arrayOf(1,2,2))
    for(i in ints)
        println(i)
    val string : MutableMap<String,String> = mutableMapOf()
    string["Words"]= "Let's Play"
    for((key ,value) in string){
        println("$key -> $value")
    }
}

class Rectangle (w: Double , h : Double) : Shape() {

    constructor(): this(1.0,1.0)

    var width: Double = w
        set(value) {
            field = 1.0
            if (value <= 0) {
                println("Width value must be greater than 0")
            } else {
                field = value
            }
        }
    var height: Double = h
        private set

    override val area: Double
        get() = width*height

    fun setHeight(value: Double) {
        if (value <= 0) {
            println("Height value must be greater than 0")
        } else {
            height = value
        }
    }

    override fun toString(): String {
        return "Rectangle(Width : ${width} , Height: ${height}, Area : ${area})"
    }
}

abstract class Shape(){
    abstract val area : Double
}

class Circle(r:Double) : Shape(){
    var radius : Double = r
    constructor(): this(0.0)

    override val area: Double
        get() = Math.abs(radius)*3.14
}





