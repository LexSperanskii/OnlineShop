package com.example.onlineshop.ui.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController

@Composable
fun CartScreen(
    navController: NavHostController,
    title :Int,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBarNameOnly(
                currentDestinationTitle = stringResource(title),
            )
        },
        bottomBar = {
            NavigationBottomAppBar(navController)
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = "Корзина",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

