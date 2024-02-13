package com.example.onlineshop.ui.navigation

import androidx.compose.ui.graphics.Color
import com.example.onlineshop.R

interface NavigationDestination {
    val route: String
    val title: Int
}
object RegistrationDestination : NavigationDestination {
    override val route = "registration"
    override val title = R.string.registration
}

sealed class BottomBarScreens(
    val route: String,
    val title: Int,
    val icon: Int,
    val tint : Color
) {
    object GeneralDestination : BottomBarScreens(
        route = "general",
        title = R.string.general,
        icon = R.drawable.icon_home,
        tint = Color.Red
    )
    object CatalogDestination : BottomBarScreens(
        route = "catalog",
        title = R.string.catalog,
        icon = R.drawable.icon_catalog,
        tint = Color.Red
    )
    object CartDestination : BottomBarScreens(
        route = "cart",
        title = R.string.cart,
        icon = R.drawable.icon_cart,
        tint = Color.Red
    )
    object SalesDestination : BottomBarScreens(
        route = "sales",
        title = R.string.sales,
        icon = R.drawable.icon_sales,
        tint = Color.Red
    )
    object AccountDestination : BottomBarScreens(
        route = "account",
        title = R.string.account,
        icon = R.drawable.icon_account,
        tint = Color.Red
    )

}