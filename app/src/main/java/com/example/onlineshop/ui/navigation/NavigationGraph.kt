package com.example.onlineshop.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.onlineshop.ui.menu.AccountScreen
import com.example.onlineshop.ui.menu.CartScreen
import com.example.onlineshop.ui.menu.catalog.CatalogScreen
import com.example.onlineshop.ui.menu.GeneralScreen
import com.example.onlineshop.ui.menu.SalesScreen
import com.example.onlineshop.ui.menu.catalog.ProductScreen
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
                navigateToGeneral = {navController.navigate(GeneralDestination.route)},
                navigateToCatalog = {navController.navigate(CatalogDestination.route)}
            )
        }
        composable(
            route = ProductDestination.route,
//            route = ProductDestination.routeWithArgs, //Нужно чтобы прокинуть состояние с одного экрана на другой
//            arguments = listOf(navArgument(ProductDestination.itemIdArg) {
//                type = NavType.StringType
//            })
        ) {
            ProductScreen(
                title = ProductDestination.title,
                navController = navController,
                navigateBack = {navController.popBackStack()},
                previousRoute = CatalogDestination.route
            )
        }
        composable(route = GeneralDestination.route) {
            GeneralScreen(
                title = GeneralDestination.title,
                navController = navController
            )
        }
        composable(route = CatalogDestination.route) {
            CatalogScreen(
                title = CatalogDestination.title,
//                navigateToProductPage = { navController.navigate("${ProductDestination.route}/${it}")},
                navigateToProductPage = { navController.navigate(ProductDestination.route) },
                navController = navController
            )
        }
        composable(route = CartDestination.route) {
            CartScreen(
                title = CartDestination.title,
                navController = navController
            )
        }
        composable(route = SalesDestination.route) {
            SalesScreen(
                title = SalesDestination.title,
                navController = navController
            )
        }
        composable(route = AccountDestination.route) {
            AccountScreen(
                navController = navController
            )
        }
    }
}