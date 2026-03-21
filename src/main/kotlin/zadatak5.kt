object TransactionLogger {

    fun log(message: String) {
        println(message)
    }

}


class BankingAccount(
    val accountNumber: Int
) {
    var balance = 0.0

    companion object {
        var totalAccounts: Int = 0
    }

    init {
        totalAccounts++
    }

    fun deposit(ammount: Double) {
        balance += ammount
        TransactionLogger.log("$accountNumber deposited $ammount")
    }

    fun withdraw(ammount: Double) {
        if(balance >= ammount) {
            balance -= ammount
            TransactionLogger.log("$accountNumber withdrawed $ammount")
            TransactionLogger.log("Current balance: $balance")
        }
        else {
            TransactionLogger.log("Account $accountNumber doesn't have enough balance!")
        }
    }
}


fun main() {

    var account1 = BankingAccount(1001)
    var account2 = BankingAccount(1002)
    var account3 = BankingAccount(1003)

    account1.deposit(1000.0)
    account2.deposit(2000.0)
    account3.deposit(3000.0)

    account3.withdraw(1000.0)
    account2.withdraw(3000.0)
    account1.withdraw(100.0)

    println("There is ${BankingAccount.totalAccounts} accounts.")


}