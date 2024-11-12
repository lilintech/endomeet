package com.example.endo_ai.features.community

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.endo_ai.core.sharedComposables.BottomNavBar

@Composable
fun JoinCommunityScreen(
    navController: NavController,
    item: CommunityData,
) {
    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                elevation = 8.dp,
                backgroundColor = Color.LightGray
            ) {
                BottomNavBar(
                    modifier = Modifier.fillMaxWidth(),
                    navController = navController
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = "${item.title} icon",
                tint = Color(75, 0, 130),
                modifier = Modifier.size(40.dp)
            )
            Spacer(Modifier.height(20.dp))
            Text(
                text = item.title,
                style = MaterialTheme.typography.h2
            )
            Spacer(Modifier.height(20.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 35.dp)
                    .height(45.dp)
                    .border(
                        width = 1.5.dp,
                        color = Color(255, 182, 193),
                    )
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Join Community",
                        style = MaterialTheme.typography.body1.copy(
                            color = Color(255, 182, 193),
                            fontSize = 20.sp
                        )
                    )
                }
            }
        }
    }
}