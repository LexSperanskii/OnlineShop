package com.example.onlineshop.ui.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.onlineshop.ui.OnlineShopTopAppBar
import com.example.onlineshop.ui.theme.OnlineShopTheme


@Composable
fun GeneralScreen(
    navController: NavHostController,
    title :String,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            OnlineShopTopAppBar(
                title = title,
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
                text = "Главная",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GeneralScreenPreview() {
//    OnlineShopTheme {
//        GeneralScreen()
//    }
//}