package com.example.myapplicationcompose

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplicationcompose.ui.theme.MyApplicationComposeTheme


class MainActivity2 : ComponentActivity() {

    private var username: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            Log.i(TAG, "se creo?")
            val myIntent = intent
            username = myIntent.getStringExtra("username") ?: "Usuario"
            Toast.makeText(this, "Bienvenido, $username", Toast.LENGTH_SHORT).show()

            MyApplicationComposeTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxHeight()
                ) { innerPadding ->
                    CalculatorScreen(
                        welcomeName = username.toString(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@SuppressLint("ContextCastToActivity")
@Composable
fun CalculatorScreen(welcomeName: String, modifier: Modifier = Modifier) {
    val activity = (LocalContext.current as? Activity)

    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "calculadora",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "usuario:    $welcomeName!",
            modifier = modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = num1,
            onValueChange = { num1 = it },
            label = { Text("Primer numero") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = num2,
            onValueChange = { num2 = it },
            label = { Text("Segundo numero") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    if (validateInput(num1, num2)) {
                        val n1 = num1.toDouble()
                        val n2 = num2.toDouble()
                        result = (n1 + n2).toString()
                    }
                }
            ) {
                Text(text = "+")
            }

            Button(
                onClick = {
                    if (validateInput(num1, num2)) {
                        val n1 = num1.toDouble()
                        val n2 = num2.toDouble()
                        result = (n1 - n2).toString()
                    }
                }
            ) {
                Text(text = "-")
            }

            Button(
                onClick = {
                    if (validateInput(num1, num2)) {
                        val n1 = num1.toDouble()
                        val n2 = num2.toDouble()
                        result = (n1 * n2).toString()
                    }
                }
            ) {
                Text(text = "×")
            }

            Button(
                onClick = {
                    if (validateInput(num1, num2)) {
                        val n1 = num1.toDouble()
                        val n2 = num2.toDouble()
                        if (n2 != 0.0) {
                            result = (n1 / n2).toString()
                        } else {
                            result = "Error: Division by zero"
                        }
                    }
                }
            ) {
                Text(text = "÷")
            }
        }

        OutlinedTextField(
            value = result,
            onValueChange = { /* Read only */ },
            label = { Text("resultado") },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )

        Button(
            onClick = {
                activity?.finish()
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = "Salir"
            )
        }
    }
}

private fun validateInput(num1: String, num2: String): Boolean {
    return try {
        num1.toDouble()
        num2.toDouble()
        true
    } catch (e: NumberFormatException) {
        false
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    MyApplicationComposeTheme {
        CalculatorScreen("Android")
    }
}