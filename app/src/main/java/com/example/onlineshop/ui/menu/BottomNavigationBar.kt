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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.onlineshop.ui.navigation.BottomBarScreens

@Composable
fun NavigationBottomAppBar(
    navController: NavHostController,
) {
    val items = listOf(
        BottomBarScreens.GeneralDestination,
        BottomBarScreens.CatalogDestination,
        BottomBarScreens.CartDestination,
        BottomBarScreens.SalesDestination,
        BottomBarScreens.AccountDestination
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar() {
        items.forEach { screen ->
            val isSelected = currentDestination?.hierarchy?.any {
                it.route == screen.route
            }
            NavigationBarItem(
                selected = isSelected == true,
                onClick = {
                    navController.navigate(screen.route){
                        //Для того чтобы при нажатии на назад не двигаться обратно по вызовам а сразу на начальный экран
//                        popUpTo(navController.graph.findStartDestination().id)
//                        launchSingleTop = true
                    }
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
