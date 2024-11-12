package com.example.endo_ai.features.auth.login

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardDoubleArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.endo_ai.R
import com.example.endo_ai.core.sharedComposables.ButtonLoading
import com.example.endo_ai.core.sharedComposables.EmailUsernameInput
import com.example.endo_ai.core.sharedComposables.PasswordInput
import com.example.endo_ai.navigation.Screens

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    var isLoading by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardDoubleArrowUp,
            contentDescription = null,
            tint = Color(255, 182, 193),
            modifier = Modifier
                .size(70.dp)
        )
        Spacer(Modifier.height(15.dp))
        Text(
            text = "ENDOMEET",
            style = MaterialTheme.typography.h2
        )
        Spacer(Modifier.height(20.dp))
        Card(
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .padding(horizontal = 25.dp)
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = MaterialTheme.shapes.large
                ),
            backgroundColor = Color.White
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                EmailUsernameInput()
                PasswordInput()
                ButtonLoading(
                    name = "Login",
                    isLoading = isLoading,
                    enabled = true,
                    onClicked = {
                        isLoading = !isLoading
                        navController.navigate(Screens.Community.route)
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Don't have an account?",
                        style = MaterialTheme.typography.h6,
                        fontFamily = FontFamily(
                            Font(R.font.gilroysemibold, weight = FontWeight.SemiBold)
                        ),
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                            navController.navigate(Screens.SignUpScreen.route)
                        }
                    ) {
                        Text(
                            text = "Log in",
                            style = MaterialTheme.typography.h6,
                            fontFamily = FontFamily(
                                Font(
                                    R.font.gilroysemibold,
                                    weight = FontWeight.SemiBold
                                )
                            ),
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colors.primary,
                        )
                    }
                }
            }
        }
    }
}