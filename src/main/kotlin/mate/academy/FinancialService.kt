package mate.academy

const val TO_EURO_RATE = 0.93
const val TO_USD_RATE = 0.82
const val DEFAULT_RATE = 1.0
const val ZERO_AMOUNT = 0.0
const val CODE_LENGTH = 3

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
            fromCurrency.code == "USD" && toCurrency.code == "EUR" -> TO_EURO_RATE
            fromCurrency.code == "USD" && toCurrency.code == "GBP" -> TO_USD_RATE
            else -> DEFAULT_RATE
        }
    }
}

@JvmInline
value class AccountNumber(val accountNumber: String) {
    init {
        val regex = "[A-Za-z]".toRegex()
        require (accountNumber.length == 10 && accountNumber.isNotEmpty() && !accountNumber.contains(regex)) {
            "Invalid account number format $accountNumber"
        }
    }
}

@JvmInline
value class CurrencyAmount(val amount: Double) {
    init {
        require(amount > ZERO_AMOUNT) {
            "Invalid amount format $amount"
        }
    }
}

@JvmInline
value class CurrencyCode(val code: String){
    init {
        require(code.isNotEmpty() && code.length == CODE_LENGTH && code == code.uppercase()) {
            "Invalid code format $code"
        }
    }
}

@JvmInline
value class TransactionId(val transactionId: String) {
    init {
        require (transactionId.isNotEmpty()){
            "Invalid transaction ID format $transactionId"
        }
    }
}
