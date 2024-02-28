package com.example.onlineshop.ui.menu.favoriteScreen

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
import com.example.onlineshop.ui.menu.TopAppBarBackAndName

@Composable
fun FavoritesScreen(
    navController: NavHostController,
    title :Int,
    onClickNavigateBack: ()->Unit,
    previousRoute : String,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBarBackAndName(
                currentDestinationTitle = stringResource(id = title),
                onClickNavigateBack = onClickNavigateBack
            )
        },
        bottomBar = {
            NavigationBottomAppBar(
                navController = navController,
                previousRoute = previousRoute
            )
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = "Избранное",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
