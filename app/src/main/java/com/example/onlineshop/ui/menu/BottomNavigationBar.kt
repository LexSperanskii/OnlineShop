package com.example.onlineshop.ui.menu

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.onlineshop.ui.navigation.AccountDestination
import com.example.onlineshop.ui.navigation.CartDestination
import com.example.onlineshop.ui.navigation.CatalogDestination
import com.example.onlineshop.ui.navigation.GeneralDestination
import com.example.onlineshop.ui.navigation.SalesDestination


@Composable
fun NavigationBottomAppBar(
    navController: NavHostController,
    previousRoute : String = ""
) {
    val items = listOf(
        GeneralDestination,
        CatalogDestination,
        CartDestination,
        SalesDestination,
        AccountDestination
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar() {
        items.forEach { screen ->
            val isSelected = currentDestination?.hierarchy?.any {
                //Для того чтобы подсвечивалось красным экран с которого мы перешли
                if (previousRoute != "")
                    previousRoute == screen.route
                else
                    it.route == screen.route
            }
            NavigationBarItem(
                selected = isSelected == true,
                onClick = {
                    navController.navigate(screen.route)
                },
                label = {
                    Text(text = stringResource(id = screen.title))
                },
                icon = {
                    if (isSelected == true)
                        Icon(
                            painter = painterResource(screen.icon),
                            contentDescription = stringResource(id = screen.title),
                            tint = screen.tint
                        )
                    else
                        Icon(
                            painter = painterResource(screen.icon),
                            contentDescription = stringResource(id = screen.title),
                        )
                }
            )
        }
    }
}
