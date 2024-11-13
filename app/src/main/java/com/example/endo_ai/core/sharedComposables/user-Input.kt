package com.example.endo_ai.core.sharedComposables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    error: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = label, style = MaterialTheme.typography.h3) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color(0xFFFCFCFC),
                focusedBorderColor = Color(0xFFFFB6C1),
                unfocusedBorderColor = Color(0xFFE2E2E2),
                errorBorderColor = Color.Red.copy(alpha = 0.8f),
                cursorColor = Color(0xFF7C7C7C)
            ),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = when {
                isPassword && !passwordVisible -> PasswordVisualTransformation()
                else -> VisualTransformation.None
            },
            isError = error != null,
            trailingIcon = {
                if (isPassword) {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            tint = Color(0xFF7C7C7C)
                        )
                    }
                } else if (error != null) {
                    Icon(
                        Icons.Filled.Error,
                        contentDescription = "Error",
                        tint = MaterialTheme.colors.error
                    )
                }
            }
        )

        AnimatedVisibility(visible = error != null) {
            error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun AuthButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        enabled = enabled && !isLoading,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFFFFB6C1),
            disabledBackgroundColor = Color(0xFFFFB6C1).copy(alpha = 0.6f)
        )
    ) {
        if (isLoading) {
            LoadingState()
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.button,
                color = Color.Black
            )
        }
    }
}