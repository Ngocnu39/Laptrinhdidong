package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipTimeLayout()
        }
    }
}

@Composable
fun TipTimeLayout() {

    var amountInput by remember { mutableStateOf("") }
    var tipInput by remember { mutableStateOf("") }
    var roundUp by remember { mutableStateOf(false) }

    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val tipPercent = tipInput.toDoubleOrNull() ?: 0.0

    val tip = calculateTip(amount, tipPercent, roundUp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {

        Text(
            text = "Calculate Tip",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = amountInput,
            onValueChange = { amountInput = it },
            label = { Text("Bill Amount") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = tipInput,
            onValueChange = { tipInput = it },
            label = { Text("Tip Percentage") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text("Round up tip")
            Switch(
                checked = roundUp,
                onCheckedChange = { roundUp = it }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Tip Amount: $tip",
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

fun calculateTip(amount: Double, tipPercent: Double, roundUp: Boolean): String {

    var tip = tipPercent / 100 * amount

    if (roundUp) {
        tip = ceil(tip)
    }

    return NumberFormat.getCurrencyInstance().format(tip)
}