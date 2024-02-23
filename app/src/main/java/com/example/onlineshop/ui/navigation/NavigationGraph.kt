package com.example.onlineshop.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.onlineshop.ui.menu.AccountScreen
import com.example.onlineshop.ui.menu.CartScreen
import com.example.onlineshop.ui.menu.catalog.CatalogScreen
import com.example.onlineshop.ui.menu.GeneralScreen
import com.example.onlineshop.ui.menu.SalesScreen
import com.example.onlineshop.ui.productScreen.ProductScreen
import com.example.onlineshop.ui.registrationScreen.RegistrationScreen

/**
 * Provides Navigation graph for the application.
 */
@Composable
fun OnlineShopNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = RegistrationDestination.route,
        modifier = modifier
    ) {
        composable(route = RegistrationDestination.route) {
            RegistrationScreen(
                title = RegistrationDestination.title,
                navigateToGeneral = {navController.navigate(BottomBarScreens.GeneralDestination.route)},
                navigateToCatalog = {navController.navigate(BottomBarScreens.CatalogDestination.route)}
            )
        }
        composable(route = ProductDestination.route) {
            ProductScreen(
                title = ProductDestination.title,
                navController = navController,
                navigateBack = {navController.popBackStack()},
                previousRoute = BottomBarScreens.CatalogDestination.route
            )
        }
        composable(route = BottomBarScreens.GeneralDestination.route) {
            GeneralScreen(
                title = BottomBarScreens.GeneralDestination.title,
                navController = navController
            )
        }
        composable(route = BottomBarScreens.CatalogDestination.route) {
            CatalogScreen(
                title = BottomBarScreens.CatalogDestination.title,
                navigateToProductPage = {navController.navigate(ProductDestination.route)},
                navController = navController
            )
        }
        composable(route = BottomBarScreens.CartDestination.route) {
            CartScreen(
                title = BottomBarScreens.CartDestination.title,
                navController = navController
            )
        }
        composable(route = BottomBarScreens.SalesDestination.route) {
            SalesScreen(
                title = BottomBarScreens.SalesDestination.title,
                navController = navController
            )
        }
        composable(route = BottomBarScreens.AccountDestination.route) {
            AccountScreen(
                navController = navController
            )
        }
    }
}