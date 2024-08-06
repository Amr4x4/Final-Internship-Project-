package com.example.speedotransfer.data_source

import com.example.speedotransfer.R


class DataSource {
    fun getCurrencies(): List<Currency>{
        val currencies = mutableListOf<Currency>()
        for(i in 1..5)
            currencies.add(Currency(R.drawable.united_states, "United States"))

        return currencies
    }
}