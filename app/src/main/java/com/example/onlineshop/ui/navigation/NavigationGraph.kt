package com.example.onlineshop.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.onlineshop.ui.menu.AccountScreen
import com.example.onlineshop.ui.menu.CartScreen
import com.example.onlineshop.ui.menu.CatalogScreen
import com.example.onlineshop.ui.menu.GeneralScreen
import com.example.onlineshop.ui.menu.SalesScreen
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
                title = stringResource(RegistrationDestination.title),
                navigateToGeneral = {navController.navigate(BottomBarScreens.GeneralDestination.route)},
                navigateToCatalog = {navController.navigate(BottomBarScreens.CatalogDestination.route)}
            )
        }
        composable(route = BottomBarScreens.GeneralDestination.route) {
            GeneralScreen(
                title = stringResource(BottomBarScreens.GeneralDestination.title),
                navController = navController
            )
        }
        composable(route = BottomBarScreens.CatalogDestination.route) {
            CatalogScreen(
                title = stringResource(BottomBarScreens.CatalogDestination.title),
                navController = navController
            )
        }
        composable(route = BottomBarScreens.CartDestination.route) {
            CartScreen(
                title = stringResource(BottomBarScreens.CartDestination.title),
                navController = navController
            )
        }
        composable(route = BottomBarScreens.SalesDestination.route) {
            SalesScreen(
                title = stringResource(BottomBarScreens.SalesDestination.title),
                navController = navController
            )
        }
        composable(route = BottomBarScreens.AccountDestination.route) {
            AccountScreen(
                title = stringResource(BottomBarScreens.AccountDestination.title),
                navController = navController
            )
        }
    }
}