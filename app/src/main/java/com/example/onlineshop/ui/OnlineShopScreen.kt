package com.example.onlineshop.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.onlineshop.ui.navigation.OnlineShopNavHost

@Composable
fun OnlineShopApp(navController: NavHostController = rememberNavController()) {
    OnlineShopNavHost(navController = navController)
}
