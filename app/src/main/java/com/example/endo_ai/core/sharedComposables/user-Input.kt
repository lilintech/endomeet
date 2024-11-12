package com.example.endo_ai.core.sharedComposables


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.Rgb
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun EmailUsernameInput() {
    val (email, setEmail) = remember { mutableStateOf("") }
    val (username, setUsername) = remember { mutableStateOf("") }

    OutlinedTextField(
        value = email,
        onValueChange = setEmail,
        label = { TextFieldLabel(name = "Email") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email
        ),
        colors = fieldColors()
    )

    OutlinedTextField(
        value = username,
        onValueChange = setUsername,
        label = { TextFieldLabel(name = "Name") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text
        ),
        colors = fieldColors()
    )
}

@Composable
fun PasswordInput() {
    val (password, setPassword) = remember { mutableStateOf("") }
    val (isPasswordVisible, setPasswordVisibility) = remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = setPassword,
        textStyle = MaterialTheme.typography.h4,
        label = { TextFieldLabel(name = "Password") },
        singleLine = true,
        colors = fieldColors(),
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Transparent)
            .padding(16.dp),
        trailingIcon = {
            IconButton(onClick = { setPasswordVisibility(!isPasswordVisible) }) {
                Icon(
                    imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                    tint = if (isPasswordVisible) Color(0xff7c7c7c) else Color(0xff7c7c7c)
                )
            }
        },
        visualTransformation = if (isPasswordVisible) androidx.compose.ui.text.input.VisualTransformation.None
        else androidx.compose.ui.text.input.PasswordVisualTransformation()
    )
}

@Composable
fun ButtonLoading(
    name: String,
    isLoading: Boolean,
    enabled: Boolean,
    onClicked: () -> Unit,
) {
    var loading by remember { mutableStateOf(isLoading) }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 25.dp, start = 20.dp, end = 20.dp)
                .height(40.dp),
            enabled = enabled,
            shape = RoundedCornerShape(10),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(255, 182, 193),
            ),
            onClick = {
                loading = !loading
                onClicked()
            }
        ) {
            if (!loading) Text(
                text = name,
                style = MaterialTheme.typography.button.copy(
                    color = Color.Black
                )
            ) else LoadingState()
        }

    }
}

@Composable
fun TextFieldLabel(name: String) {
    Text(
        text = name, style = MaterialTheme.typography.h3,
        color = Color(0xff727272),
        textAlign = TextAlign.Start,
        lineHeight = 29.sp
    )
}

@Composable
fun fieldColors() = TextFieldDefaults.textFieldColors(
    backgroundColor = Color(0xfffcfcfc),
    cursorColor = Color(0xff7c7c7c),
    focusedIndicatorColor = Color(0xffe2e2e2),
    unfocusedIndicatorColor = Color(0xffe2e2e2)
)