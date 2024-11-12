package com.example.endo_ai.features.community

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Woman
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CommunityScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val communityItems = listOf(
        CommunityData(
            title = "EmpowerHer Nairobi",
            description = "Community of Women in Nairobi fighting endometriosis",
            icon = Icons.Default.People,
        ),
        CommunityData(
            title = "Endometriosis Research Group",
            description = "Researchers focused on Endometriosis",
            icon = Icons.Default.Woman
        ),
        CommunityData(
            title = "WHO Women Support",
            description = "World Health Organization support for women",
            icon = Icons.Default.People
        ),
        CommunityData(
            title = "Doctors against Endometriosis",
            description = "Doctors in Kenya providing resources on endometriosis",
            icon = Icons.Default.LocalHospital
        )
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .height(55.dp),
            shape = RoundedCornerShape(30.dp),
            backgroundColor = Color(255, 182, 193)
        ) {
            Row(
                modifier = modifier
                    .padding(start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "menu-icon",
                        tint = Color.Black
                    )
                }
                Text(
                    text = "Find communities near you"
                )
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search-icon",
                        tint = Color.Black
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(15.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        ) {
            items(communityItems) { item ->
                CommunityCard(item = item, navController = navController)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

    }


}