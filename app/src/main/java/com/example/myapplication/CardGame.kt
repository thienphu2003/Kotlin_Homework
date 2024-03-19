package com.example.myapplication

import java.lang.IllegalArgumentException

class PlayingCard {
    private var Card : String =""

    fun getCard() = Card

    fun setCard(card : String) {
        Card = card
    }

    fun getValue() :Int{
        val value :String = Card[0].toString()
        return if(arrayOf("J","Q","K").find { it -> it == value } !=null){
            10
        }else{
            value.toInt()
        }

    }

    override fun toString(): String {
        return "Card : $Card"
    }
}

class PlayingCardDeck{
    private var deck : MutableList<PlayingCard> = mutableListOf()
    private var storage : MutableList<PlayingCard> = mutableListOf()
    fun getDeck() = deck

    fun setDeck(deck : MutableList<PlayingCard>){
        this.deck = deck
    }

    fun generateCardDeck(){
        val arrayOfNumber : Array<Any> = arrayOf(1,2,3,4,5,6,7,8,9,10,'J','Q','K')
        val symbols : Array<Char> = arrayOf('♠','♥','♦','♣')
        val cards : MutableList<PlayingCard> = mutableListOf()
        for(i in arrayOfNumber.indices){
            for(k in symbols.indices){
                val string = arrayOfNumber[i].toString() + symbols[k].toString()
                val card = PlayingCard()
                card.setCard(string)
                cards.add(card)
            }
        }
        this.setDeck(cards)
    }

    fun drawRandomCard() : PlayingCard{
        val random = deck.random()
        deck.remove(random)
        storage.add(random)
        println(random)
        return random
    }
}

class Player(Name : String){
    private var name = Name
    constructor(): this("No Name")
    private var card_list : MutableList<PlayingCard> = mutableListOf()
    fun getCardList() = card_list
    fun getName() = name
    fun setName(name : String){
        this.name = name
    }
    fun setCardList(card : PlayingCard){
        this.card_list.add(card)
    }

    fun getTotalPoint() : Int{
        return this.card_list.sumOf{it -> it.getValue()}
    }


}

class Game (player : MutableList<Player>){
    private var players : MutableList<Player> = player
    init {
        if(player.size !in 2..6){
            throw IllegalArgumentException("Game should have 2 to 6 players")
        }
    }

    override fun toString(): String {
        return "Game started with ${players.size} players."
    }

    private fun checkWinner(player : Player) : Boolean{
        val maxPoint = players.maxBy { it -> it.getTotalPoint() }.getTotalPoint()
        if(player.getTotalPoint() <=21 && player.getTotalPoint() == maxPoint){
            return true
        }
        return false
    }

    private fun checkFinalWinner(player : MutableList<Player>) : Unit{
        for(i in player){
            if(this.checkWinner(i)){
                println("${i.getName()} is the winner with total point of ${i.getTotalPoint()}")
                break
            }
        }
    }

    fun start(deck : PlayingCardDeck){
        for(i in 0 until players.size){
            for(k in 0 .. 1){
                val card = deck.drawRandomCard()
                players[i].setCardList(card)
            }
            println("2 cards for player $i are ${players[i].getCardList()}")
        }
        val options : Array<String> = arrayOf("1.Draw a card", "2.Passed")
        var loop = 0
        while (loop < players.size){
            var turn = 1
            var status = true
            while (status){
                println("This is the $turn turn of player $loop ")
                println(options[0])
                println(options[1])
                val value = readln().toInt()
                when(value){
                    1 -> {
                        val card = deck.drawRandomCard()
                        players[loop].setCardList(card)
                        turn ++
                        if(players[loop].getCardList().size >=5){
                            println("You have collected 5 cards")
                            status = false
                        }
                    }
                    2 -> {
                        status = false
                    }
                    else -> {
                        status = false
                    }
                }
            }
            loop ++
        }
        println("End the game !!")
        println("All cards for user ${players[0].getName()} are ${players[0].getCardList()}")
        println("All cards for user ${players[1].getName()} are ${players[1].getCardList()}")
        println("Total point of user ${players[0].getName()} is ${players[0].getTotalPoint()}")
        println("Total point of user ${players[1].getName()} is ${players[1].getTotalPoint()}")
        this.checkFinalWinner(players)
    }

}



fun main(args: Array<String>){
    val deck = PlayingCardDeck()
    deck.generateCardDeck()
    val players : MutableList<Player> = mutableListOf()
    println("Let create a card game")
    println("Nhập số lượng player")
    val n = readln().toInt()
    for(i in 1..n){
        println("Nhập tên player $i")
        val name = readln()
        players.add(Player(name))
    }
    val game = Game(players)
    println(game)
    game.start(deck)


}

