package com.example.myapplication

import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.UUID
import kotlin.system.exitProcess

fun main(args: Array<String>){
    var number = 1
    do {
        println("Enter any key to continue . Press 0 to end program")
        number = readln().toInt()
        if(number == 0){
            break
        }
        println("Let's create a bank account")
        println("Enter your ID")
        val id = readLine()?.toInt() ?: 0
        println("Enter your name")
        val name = readln().toString()
        println("Enter your date of birth")
        val date_of_birth = readln().toString()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val dateOfBirth: Date = dateFormat.parse(date_of_birth)
        println("Enter your pin")
        val PIN = readln().toInt()
        val back_account = BankAccount(id, name ,dateOfBirth, PIN)
        println(back_account)
        println("Bank account list : ${BankAccount.Companion.bank_accounts}" )
        println("Let's login to your new account")
        val account_number = readln().toInt()
        val account_PIN = readln().toInt()
        val result = back_account.login(account_number,account_PIN)
        println(result)
        println("Current User : ${BankAccount.Companion.bank_accounts.entries.firstOrNull{ it -> it.value.getStatus() }?.value?.getName()}")
        if(BankAccount.Companion.bank_accounts.entries.firstOrNull{ it -> it.value.getStatus() }?.value?.getName() !== null){
            var user = BankAccount.Companion.bank_accounts.entries.firstOrNull{ it -> it.value.getStatus() }?.value
            var keyword : String
            do {
                println("a.\tSee current account balance and account information ")
                println("b.\tList all transactions descending ")
                println("c.\tDeposit money")
                println("d.\tWithdraw money (cannot withdraw more than what he/she has in the account!)")
                println("e.\tTransfer money to another account in the same bank. He needs to provide account ID, owner’s name and amount to transfer. ")
                println("f.\tQuit ")
                keyword = readln().toString().toLowerCase()
                if(keyword == "f"){
                    break
                }
                when(keyword){
                    "a" -> println(user)
                    "b" -> println(user?.getTransactions())
                    "c" -> BankAccount.Companion.depositMoney(user?.getID())
                    "d" -> BankAccount.Companion.withdrawMoney(user?.getID())
                    "e" -> BankAccount.Companion.transferMoney(user?.getID())
                    else -> throw IllegalArgumentException("Wrong keyword")
                }
            }while (keyword !=="f")

        }
    }while (number !=0)

}



class BankAccount(ID : Int? , name: String? , date_of_birth : Date?, PIN : Int) {
    companion object {
        private val _bankAccounts = mutableMapOf<Int, BankAccount>()
        val bank_accounts: MutableMap<Int, BankAccount>
            get() = _bankAccounts
        fun depositMoney(id : Int?){
            println("Enter money amount to deposit")
            val money = readln().toInt()
            if(money <= 0){
                throw IllegalArgumentException("Money needs to be larger than 0")
            }
            _bankAccounts.entries.firstOrNull{it -> it.value.ID == id}?.value?.setBalance(money)
            println("Deposit money success")
        }
        fun withdrawMoney(id : Int?){
            println("Enter money amount to withdraw")
            val money = readln().toInt()
            val user_balance = _bankAccounts.entries.firstOrNull{ it -> it.value.ID == id}?.value?.getBalance() ?: 0
            println(user_balance)
            if(money > user_balance){
                throw IllegalArgumentException("Money needs to be smaller than the balance")
            }
            _bankAccounts.entries.firstOrNull{it -> it.value.ID == id}?.value?.setBalance(user_balance - money)
            println("Withdraw money success")
        }
        fun transferMoney(id : Int?){
            println("Enter your name")
            val name = readln()
            val users :List<BankAccount> = _bankAccounts.entries.filter { it -> it.value.ID !== id }.map { it -> it.value }
            val user_balance = _bankAccounts.entries.firstOrNull{ it -> it.value.ID == id}?.value?.getBalance() ?: 0
            println("Let choose a user to deposit money")
            for(user in users){
                println("${user.ID}, $user")
            }
            val user_id = readln().toInt()
            println("Enter money amount")
            val money = readln().toInt()
            if(money >= user_balance){
                throw IllegalArgumentException("Not enough money to transfer")
            }
            _bankAccounts.entries.firstOrNull{ it -> it.value.ID == id}?.value?.setBalance(user_balance - money)
            val receiver_balance = _bankAccounts.entries.firstOrNull { it -> it.value.ID == user_id }?.value?.getBalance() ?: 0
            _bankAccounts.entries.firstOrNull{ it -> it.value.ID == user_id}?.value?.setBalance(receiver_balance + money)
            println("Transfer Money success")
        }


    }
    private var ID : Int? = ID ?: generateRandomID()
    private var name : String? =name ?: ""
    private var date_of_birth: Date?= date_of_birth ?: Date()
    private var PIN : Int = PIN
    private var account_number : Int = 0
    private var status : Boolean = false
    private var balance : Int = 0
    private var transactions : MutableList<String> = mutableListOf()

    fun getStatus() = status
    fun getName() = name
    fun getBalance() = balance
    fun getTransactions() = transactions
    fun getID() = ID
    fun setBalance(money : Int){
        balance = money
    }
    init {
        if (!isSixDigitPIN(PIN)) {
            throw IllegalArgumentException("PIN must have exactly 6 digits")
        }
        account_number = generateRandomAccountNumber()
        _bankAccounts.put(ID ?:0,this)
    }

    private fun isSixDigitPIN(pin: Int): Boolean {
        val pinString = pin.toString()
        return pinString.length == 6
    }

    private fun generateRandomID(): Int{
        return (1 until 100).random()
    }
    private fun generateRandomAccountNumber() :Int = (1000 until 5000).random()

    override fun toString(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return " ${ID}, ${name}, ${dateFormat.format(date_of_birth)} , ${PIN}, ${account_number}, ${status}, ${balance}"
    }
    fun login(number: Int , PIN: Int) : Any{
        if(number.equals(this.account_number) && PIN.equals(this.PIN)){
            status = true
           val value = _bankAccounts.entries.firstOrNull {it -> it.value.PIN == PIN}?.value
            value?.status = true
            val update = _bankAccounts.entries.filter { it -> it.value.ID != value?.ID }.map { it -> it.value.status = false }
            return "Login successfully"
        }
        return "Login Failed"
    }

}

