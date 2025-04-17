package com.bhj.perfectcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bhj.perfectcalculator.ui.theme.PerfectPercentageCalculatorTheme
import java.text.DecimalFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PerfectPercentageCalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavigator(navController)
                }
            }
        }
    }
}

@Composable
fun AppNavigator(navController: NavHostController) {
    NavHost(navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("percent") { PercentCalculatorScreen() }
        composable("increase") { PercentageIncreaseScreen() }
        composable("decrease") { PercentageDecreaseScreen() }
        composable("tip") { TipCalculatorScreen() }
        composable("margin") { PercentageMarginScreen() }
        composable("discount") { DiscountScreen() }
        composable("whatpercent") { WhatPercentOfScreen() }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    val buttons = listOf(
        Triple("Percent Calculator", R.drawable.ic_percent, "percent"),
        Triple("Percentage Increase", R.drawable.ic_increase, "increase"),
        Triple("Percentage Decrease", R.drawable.ic_decrease, "decrease"),
        Triple("Tip Calculator", R.drawable.ic_tip, "tip"),
        Triple("Percentage Margin", R.drawable.ic_margin, "margin"),
        Triple("Discount", R.drawable.ic_discount, "discount"),
        Triple("Percentage (What % of)", R.drawable.ic_what_percent, "whatpercent")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Perfect % Calculator",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        buttons.forEach { (label, iconRes, route) ->
            Button(
                onClick = { navController.navigate(route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = iconRes),
                        contentDescription = label,
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 8.dp)
                    )
                    Text(
                        text = label,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun CalculatorScaffold(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        content()
    }
}

fun formatNumber(number: Float): String {
    val df = DecimalFormat("#.##")
    return df.format(number)
}

@Composable
fun PercentCalculatorScreen() {
    CalculatorScaffold(title = "Percent Calculator") {
        var base by remember { mutableStateOf("") }
        var percent by remember { mutableStateOf("") }
        val result = base.toFloatOrNull()?.let { b -> percent.toFloatOrNull()?.let { p -> b * p / 100 } }

        CalculatorInputField("Base Value", base) { base = it }
        CalculatorInputField("Percentage %", percent) { percent = it }
        Spacer(Modifier.height(16.dp))
        result?.let {
            Text("Result: ${formatNumber(it)}", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun PercentageIncreaseScreen() {
    CalculatorScaffold(title = "Percentage Increase") {
        var original by remember { mutableStateOf("") }
        var increase by remember { mutableStateOf("") }
        val result = original.toFloatOrNull()?.let { o -> increase.toFloatOrNull()?.let { i -> o + (o * i / 100) } }

        CalculatorInputField("Original Value", original) { original = it }
        CalculatorInputField("Increase %", increase) { increase = it }
        Spacer(Modifier.height(16.dp))
        result?.let {
            Text("Result: ${formatNumber(it)}", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun PercentageDecreaseScreen() {
    CalculatorScaffold(title = "Percentage Decrease") {
        var original by remember { mutableStateOf("") }
        var decrease by remember { mutableStateOf("") }
        val result = original.toFloatOrNull()?.let { o -> decrease.toFloatOrNull()?.let { d -> o - (o * d / 100) } }

        CalculatorInputField("Original Value", original) { original = it }
        CalculatorInputField("Decrease %", decrease) { decrease = it }
        Spacer(Modifier.height(16.dp))
        result?.let {
            Text("Result: ${formatNumber(it)}", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun TipCalculatorScreen() {
    CalculatorScaffold(title = "Tip Calculator") {
        var amount by remember { mutableStateOf("") }
        var tip by remember { mutableStateOf("") }
        val result = amount.toFloatOrNull()?.let { a -> tip.toFloatOrNull()?.let { t -> a + (a * t / 100) } }

        CalculatorInputField("Amount", amount) { amount = it }
        CalculatorInputField("Tip %", tip) { tip = it }
        Spacer(Modifier.height(16.dp))
        result?.let {
            Text("Total with Tip: ${formatNumber(it)}", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun PercentageMarginScreen() {
    CalculatorScaffold(title = "Percentage Margin") {
        var cost by remember { mutableStateOf("") }
        var margin by remember { mutableStateOf("") }
        val result = cost.toFloatOrNull()?.let { c -> margin.toFloatOrNull()?.let { m -> c / (1 - m / 100) } }

        CalculatorInputField("Cost", cost) { cost = it }
        CalculatorInputField("Margin %", margin) { margin = it }
        Spacer(Modifier.height(16.dp))
        result?.let {
            Text("Selling Price: ${formatNumber(it)}", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun DiscountScreen() {
    CalculatorScaffold(title = "Discount Calculator") {
        var price by remember { mutableStateOf("") }
        var discount by remember { mutableStateOf("") }
        val result = price.toFloatOrNull()?.let { p -> discount.toFloatOrNull()?.let { d -> p - (p * d / 100) } }

        CalculatorInputField("Price", price) { price = it }
        CalculatorInputField("Discount %", discount) { discount = it }
        Spacer(Modifier.height(16.dp))
        result?.let {
            Text("Discounted Price: ${formatNumber(it)}", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun WhatPercentOfScreen() {
    CalculatorScaffold(title = "What % of") {
        var part by remember { mutableStateOf("") }
        var total by remember { mutableStateOf("") }
        val result = part.toFloatOrNull()?.let { p -> total.toFloatOrNull()?.let { t -> (p / t) * 100 } }

        CalculatorInputField("Part", part) { part = it }
        CalculatorInputField("Total", total) { total = it }
        Spacer(Modifier.height(16.dp))
        result?.let {
            Text("Percentage: ${formatNumber(it)} %", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun CalculatorInputField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    )
}
