package com.example.myapplicationcompose

import android.content.Intent
import android.os.Bundle
import android.widget.TextClock
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.myapplicationcompose.ui.theme.MyApplicationComposeTheme

class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoginScreen()
            }
        }
    }
}

//pantalla de login con sus campos
@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Spacer(modifier = Modifier.padding(8.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuario") },
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.padding(8.dp))

        TextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = validatePassword(it)
            },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = passwordError != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        passwordError?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, bottom = 16.dp)
            )
        }

        Spacer(modifier = Modifier.padding(16.dp))

        Button(
            onClick = {
                if (username.isNotBlank() && passwordError == null && password.isNotBlank()) {
                    val intent = Intent(context, MainActivity2::class.java)
                    intent.putExtra("username", username)
                    context.startActivity(intent)
                } else {
                    if (username.isBlank()) {
                        Toast.makeText(context, "El usuario no puede estar vacío", Toast.LENGTH_SHORT).show()
                    } else if (password.isBlank()) {
                        Toast.makeText(context, "La contraseña no puede estar vacía", Toast.LENGTH_SHORT).show()
                    } else if (passwordError != null) {
                        Toast.makeText(context, "La contraseña no cumple con los requisitos", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(0.5f)
        ) {
            Text(text = "Acceder")
        }
    }
}

//funcion para validar si la contraseña es valina o no
fun validatePassword(password: String): String? {
    val passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[_]).{8,}$".toRegex()

    return when {
        password.length < 8 -> "La contraseña debe tener al menos 8 caracteres"
        !password.contains(Regex("[A-Z]")) -> "Debe contener al menos una letra mayúscula"
        !password.contains(Regex("[a-z]")) -> "Debe contener al menos una letra minúscula"
        !password.contains(Regex("[0-9]")) -> "Debe contener al menos un número"
        !password.contains("_") -> "Debe contener al menos un guión bajo (_)"
        !passwordPattern.matches(password) -> "La contraseña no cumple todos los requisitos"
        else -> null
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    LoginScreen()
}