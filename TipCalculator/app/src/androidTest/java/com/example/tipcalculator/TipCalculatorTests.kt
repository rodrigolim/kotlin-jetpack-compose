package com.example.tipcalculator


import com.example.tipcalculator.calcularGorjeta
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.NumberFormat
import java.util.Locale


class TipCalculatorTests {

    @Test
    fun calcularGorjeta_semArredondar() {
        val valorConta = 10.00
        val percentualGorjeta = 20.00
        val gorjetaEsperada = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(2.00)

        val gorjetaReal = calcularGorjeta(valorConta, percentualGorjeta, false)

        assertEquals(gorjetaEsperada, gorjetaReal)
    }
}

