package com.example.onlineshop.ui

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.onlineshop.ui.navigation.OnlineShopNavHost


@Composable
fun OnlineShopApp(navController: NavHostController = rememberNavController()) {
    OnlineShopNavHost(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnlineShopTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color(0xFF000000
                    ),
                ),
                modifier = Modifier
                    .height(21.dp)
            )},
        modifier = modifier,
    )
}