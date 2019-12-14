package com.wutsi.core.util

import java.lang.IllegalArgumentException
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*
import java.util.Locale





class Money (
        val value:BigDecimal? = null,
        val currencyCode: String? = null
) : Comparable<Money> {

    companion object {
        private val localeByCurrency = mutableMapOf<String, Locale> ()

        init{
            for (country in Locale.getISOCountries()) {
                val locale = Locale("en", country)
                val code = NumberFormat.getCurrencyInstance(locale).currency.currencyCode
                localeByCurrency[code] = locale
            }
        }
    }

    var str: String? = null

    fun multiply(factor: BigDecimal) = Money(value?.multiply(factor), currencyCode)

    fun toBigDecimal(): BigDecimal {
        val currency = if (currencyCode == null) null else Currency.getInstance(currencyCode)
        return toBigDecimal(currency)
    }

    fun toDouble (): Double {
        return toBigDecimal().toDouble()
    }

    override fun compareTo(other: Money): Int {
        if (value == null) {
            if (other.value == null) {
                return 0
            } else {
                return Integer.MIN_VALUE
            }
        }  else if (other.value == null){
            return Integer.MAX_VALUE
        }

        return value.compareTo(other.value)
    }

    override fun toString() : String {
        if (str != null) {
            return str!!
        }

        if (value == null || currencyCode == null || currencyCode.isEmpty()){

            str = ""

        } else {

            try {

                val currency = Currency.getInstance(currencyCode)
                val pattern = if (currency.defaultFractionDigits == 0) "###,###" else "###,###.00"
                val locale = localeByCurrency[currencyCode]

                val formatter = DecimalFormat(pattern, DecimalFormatSymbols(locale))
                val text = formatter.format(value?.toDouble())
                str = "$text ${currency.getSymbol(locale)}"

            } catch (ex: IllegalArgumentException){
                str = ""
            }
        }
        return str!!
    }

    private fun toBigDecimal(currency: Currency?): BigDecimal {
        if (value == null || currency == null){
            return BigDecimal.ZERO
        }

        return value.setScale(currency.defaultFractionDigits, BigDecimal.ROUND_UP)
    }

}
