package com.example.endo_ai.features.community.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.endo_ai.features.community.domain.model.Community
import com.example.endo_ai.navigation.Screens

@Composable
fun CommunityCard(
    modifier: Modifier = Modifier,
    communityData: Community,
    navController: NavController
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .height(100.dp)
            .clickable(
                onClick = {
                    navController.navigate(Screens.JoinCommunityScreen.createRoute(communityData.id))
                }
            ),
        shape = RoundedCornerShape(15.dp),
        backgroundColor = Color(240, 240, 240)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.People,
                contentDescription = "${communityData.title} icon",
                tint = Color(75, 0, 130),
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = communityData.title,
                    style = MaterialTheme.typography.subtitle1
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = communityData.description,
                    style = MaterialTheme.typography.caption
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Created by ${communityData.creatorName}",
                    style = MaterialTheme.typography.caption,
                    color = Color.Gray
                )
            }
        }
    }
}

