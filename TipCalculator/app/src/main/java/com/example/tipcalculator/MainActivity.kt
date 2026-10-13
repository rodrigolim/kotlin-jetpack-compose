package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.ceil
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Switch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipCalculatorTheme {
                TipTimeLayout()
            }
        }
    }
}

@Composable
fun TipTimeLayout() {

    var valorContaInput by remember { mutableStateOf("") }
    var percentualGorjetaInput by remember { mutableStateOf("") }

    val valorConta = valorContaInput.toDoubleOrNull() ?: 0.0
    val percentualGorjeta = percentualGorjetaInput.toDoubleOrNull() ?: 0.0

    var arredondarGorjeta by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Calculadora de Gorjeta",
            fontSize = 24.sp,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(alignment = Alignment.Start)
        )

        EditNumberField(
            label = "Valor da conta",
            value = valorContaInput,
            onValueChanged = { valorContaInput = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        EditNumberField(
            label = "Porcentagem da gorjeta",
            value = percentualGorjetaInput,
            onValueChanged = { percentualGorjetaInput = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )

        RoundTheTipRow(
            arredondar = arredondarGorjeta,
            onArredondarChanged = { arredondarGorjeta = it },
            modifier = Modifier.padding(bottom = 32.dp)
        )


        // 1) junto das outras variáveis de estado, antes do Column:
        var gorjeta by remember { mutableStateOf(calcularGorjeta(0.0, 0.0, false)) }

        // 2) dentro do Column, logo após os dois campos:
        Button(
            onClick = {
                gorjeta = calcularGorjeta(valorConta, percentualGorjeta, arredondarGorjeta)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular")
        }
        Text(
            text = "Gorjeta: $gorjeta",
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 16.dp)
        )


    }
}

@Composable
fun EditNumberField(
    label: String,
    value: String,
    onValueChanged: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChanged,
        singleLine = true,
        label = { Text(label) },
        keyboardOptions = keyboardOptions,
        modifier = modifier
    )
}

fun calcularGorjeta(
    valorConta: Double,
    percentualGorjeta: Double,
    arredondar: Boolean
): String {
    var gorjeta = percentualGorjeta / 100 * valorConta
    if (arredondar) {
        gorjeta = ceil(gorjeta)
    }
    return NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(gorjeta)
}


@Composable
fun RoundTheTipRow(
    arredondar: Boolean,
    onArredondarChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Arredondar gorjeta?")
        Switch(
            checked = arredondar,
            onCheckedChange = onArredondarChanged
        )
    }
}


