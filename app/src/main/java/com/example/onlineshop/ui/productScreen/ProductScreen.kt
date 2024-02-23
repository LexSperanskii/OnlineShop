package com.example.onlineshop.ui.productScreen

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
import com.example.onlineshop.ui.menu.NavigationBottomAppBar
import com.example.onlineshop.ui.menu.OnlineShopTopAppBar

@Composable
fun ProductScreen(
    title :Int,
    navController: NavHostController,
    navigateBack: ()->Unit,
    modifier: Modifier = Modifier,
    previousRoute: String
) {
    Scaffold(
        topBar = {
            OnlineShopTopAppBar(
                title = stringResource(id = title),
                navigateBack = navigateBack,
                canNavigateBack = true
            )
        },
        bottomBar = {
            NavigationBottomAppBar(navController, previousRoute = previousRoute)
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = stringResource(id = title),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}