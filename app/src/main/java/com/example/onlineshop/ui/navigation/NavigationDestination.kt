package com.example.onlineshop.ui.navigation

import androidx.compose.ui.graphics.Color
import com.example.onlineshop.R

interface NavigationDestination {
    val route: String
    val title: Int
}
interface NavigationDestinationForBottomBar : NavigationDestination {
    val icon : Int
    val tint : Color
}
object RegistrationDestination : NavigationDestination {
    override val route = "registration"
    override val title = R.string.registration
}
object ProductDestination : NavigationDestination {
    override val route = "product"
    override val title = R.string.product
}
object FavoritesDestination : NavigationDestination {
    override val route = "favorite"
    override val title = R.string.favorite
}
object GeneralDestination : NavigationDestinationForBottomBar {
    override val route = "general"
    override val title = R.string.general
    override val icon = R.drawable.ic_home
    override val tint = Color.Red
}
object CatalogDestination : NavigationDestinationForBottomBar{
    override val route = "catalog"
    override val title = R.string.catalog
    override val icon = R.drawable.ic_catalog
    override val tint = Color.Red
}
object CartDestination : NavigationDestinationForBottomBar {
    override val route = "cart"
    override val title = R.string.cart
    override val icon = R.drawable.ic_cart
    override val tint = Color.Red
}
object SalesDestination : NavigationDestinationForBottomBar {
    override val route = "sales"
    override val title = R.string.sales
    override val icon = R.drawable.ic_sales
    override val tint = Color.Red
}
object AccountDestination : NavigationDestinationForBottomBar {
    override val route = "account"
    override val title = R.string.account
    override val icon = R.drawable.ic_account
    override val tint = Color.Red
}

