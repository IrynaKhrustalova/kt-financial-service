package mate.academy

class FinancialService {

    fun transferFunds(
        source: AccountNumber,
        destination: AccountNumber,
        amount: CurrencyAmount,
        currencyCode: CurrencyCode,
        transactionId: TransactionId
    ) : String {
        return "Transferred ${amount.amount} ${currencyCode.code} from ${source.accountNumber} to " +
                "${destination.accountNumber}. Transaction ID: ${transactionId.transactionId}"
    }

    fun convertCurrency(
        amount: CurrencyAmount,
        fromCurrency: CurrencyCode,
        toCurrency: CurrencyCode
    ): CurrencyAmount {
        val rate = getExchangeRate(fromCurrency, toCurrency)
        return CurrencyAmount(amount.amount.times(rate))
    }

    private fun getExchangeRate(fromCurrency: CurrencyCode, toCurrency: CurrencyCode): Double {
        // Placeholder exchange rate - in a real application, you'd fetch this from a financial API
        return when {
            fromCurrency.code == "USD" && toCurrency.code == "EUR" -> 0.93
            fromCurrency.code == "USD" && toCurrency.code == "GBP" -> 0.82
            else -> 1.0
        }
    }
}

@JvmInline
value class AccountNumber(val accountNumber: String) {
    init {
        val regex = "[A-Za-z]".toRegex()
        if (accountNumber.length != 10 || accountNumber.isEmpty() || accountNumber.contains(regex)) {
            throw IllegalArgumentException("Invalid account number format $accountNumber")
        }
    }
}

@JvmInline
value class CurrencyAmount(val amount: Double) {
    init {
        if (amount < 0.0) {
            throw IllegalArgumentException("Invalid amount format $amount")
        }
    }
}

@JvmInline
value class CurrencyCode(val code: String){
    init {
        if (code.isEmpty() || code.length != 3 || code != code.uppercase()) {
            throw IllegalArgumentException("Invalid code format $code")
        }
    }
}

@JvmInline
value class TransactionId(val transactionId: String) {
    init {
        if (transactionId.isEmpty()){
            throw IllegalArgumentException("Invalid transaction ID format $transactionId")
        }
    }
}
