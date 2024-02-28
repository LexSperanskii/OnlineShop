package com.example.onlineshop.ui.menu.accountScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.onlineshop.R
import com.example.onlineshop.ui.AppViewModelProvider
import com.example.onlineshop.ui.menu.NavigationBottomAppBar
import com.example.onlineshop.ui.menu.OnlineShopTopAppBar

@Composable
fun AccountScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    navigateToExit: ()->Unit,
    accountScreenViewModel: AccountScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val accountUiState = accountScreenViewModel.uiState.collectAsState().value
    Scaffold(
        topBar = {
            OnlineShopTopAppBar(
                title = stringResource(R.string.accountTitle),
                navigateBack = {}
            )
        },
        bottomBar = {
            NavigationBottomAppBar(navController)
        }
    ) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            val formattedPhoneNumber = if(accountUiState.accountPhoneNumber.length == 12) { buildString {
                append(accountUiState.accountPhoneNumber.substring(0, 2)) // Добавляем код страны
                append(" ")
                append(accountUiState.accountPhoneNumber.substring(2, 5)) // Добавляем первые три цифры номера
                append(" ")
                append(accountUiState.accountPhoneNumber.substring(5, 8)) // Добавляем первые три цифры номера
                append("-")
                append(accountUiState.accountPhoneNumber.substring(8, 10)) // Добавляем две цифры перед дефисом
                append("-")
                append(accountUiState.accountPhoneNumber.substring(10)) // Добавляем две последние цифры номера
            }}else { "какая то лажа!"}
            AccountScreenBody(
                accountName = stringResource(id = R.string.account_name_surname, accountUiState.accountName, accountUiState.accountSurname),
                accountNumber = formattedPhoneNumber,
                favoriteDescription = LocalContext.current.resources.getQuantityString(
                    R.plurals.account_quantity_favorite_items,
                    accountUiState.favoritesQuantity,
                    accountUiState.favoritesQuantity
                ),
                navigateToExit = {
                    accountScreenViewModel.deleteUserAndFavorites()
                    navigateToExit()
                },
                navigateToFavorites = {}
            )
        }
    }
}

@Composable
fun AccountScreenBody(
    accountName: String,
    accountNumber: String,
    favoriteDescription: String,
    navigateToExit: () -> Unit,
    navigateToFavorites: () -> Unit,
    modifier: Modifier = Modifier,
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 32.dp)
    ) {
        AccountCard(
            leadingIcon = painterResource(id = R.drawable.icon_account),
            leadingIconTint = Color(0xFF333333),
            trailingIcon = painterResource(R.drawable.ic_exit),
            trailingIconTint = Color(0xFF333333),
            name = accountName,
            description = accountNumber,
            navigateClick = {},
            modifier = Modifier.padding(bottom = 24.dp)
        )
        AccountCard(
            leadingIcon = painterResource(id = R.drawable.heart_outlined),
            leadingIconTint = Color(0xFFD62F89),
            trailingIcon = painterResource(id = R.drawable.ic_arrow_forward),
            trailingIconTint = Color(0xFF000000),
            name = stringResource(id = R.string.favorite),
            description = favoriteDescription,
            navigateClick = navigateToFavorites,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        AccountCard(
            leadingIcon = painterResource(id = R.drawable.ic_shops),
            leadingIconTint = Color(0xFFD62F89),
            trailingIcon = painterResource(id = R.drawable.ic_arrow_forward),
            trailingIconTint = Color(0xFF000000),
            name = stringResource(id = R.string.shops),
            navigateClick = {},
            modifier = Modifier.padding(bottom = 8.dp)
        )
        AccountCard(
            leadingIcon = painterResource(id = R.drawable.ic_feedback),
            leadingIconTint = Color(0xFFF9A249),
            trailingIcon = painterResource(id = R.drawable.ic_arrow_forward),
            trailingIconTint = Color(0xFF000000),
            name = stringResource(id = R.string.feedback),
            navigateClick = {},
            modifier = Modifier.padding(bottom = 8.dp)
        )
        AccountCard(
            leadingIcon = painterResource(id = R.drawable.ic_offer),
            leadingIconTint = Color(0xFFA0A1A3),
            trailingIcon = painterResource(id = R.drawable.ic_arrow_forward),
            trailingIconTint = Color(0xFF000000),
            name = stringResource(id = R.string.offer),
            navigateClick = {},
            modifier = Modifier.padding(bottom = 8.dp)
        )
        AccountCard(
            leadingIcon = painterResource(id = R.drawable.ic_purchase_return),
            leadingIconTint = Color(0xFFA0A1A3),
            trailingIcon = painterResource(id = R.drawable.ic_arrow_forward),
            trailingIconTint = Color(0xFF000000),
            name = stringResource(id = R.string.purchase_return),
            navigateClick = {},
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        ExitButton(
            navigateToExit = navigateToExit,
        )
    }
}

@Composable
fun AccountCard(
    leadingIcon: Painter,
    leadingIconTint: Color,
    trailingIcon:  Painter,
    trailingIconTint: Color,
    name: String,
    description: String = "",
    navigateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Box(
            contentAlignment = Alignment.Center ,
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .background(Color(0xFFF8F8F8))
                .height(49.dp)
                .fillMaxWidth()
                .clickable { navigateClick() }
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Icon(
                    painter = leadingIcon,
                    contentDescription = null,
                    tint = leadingIconTint,
                    modifier = Modifier
                        .size(24.dp)
                )
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(
                        text = name,
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color(0xFF000000),
                        )
                    )
                    if (description != "")
                    Text(
                        text = description,
                        style = TextStyle(
                            fontSize = 10.sp,
                            color = Color(0xFFA0A1A3),
                        ),
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = trailingIcon,
                    contentDescription = null,
                    tint = trailingIconTint,
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        }
    }
}

@Composable
fun ExitButton(
    navigateToExit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center ,
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .background(Color(0xFFF8F8F8))
            .height(51.dp)
            .fillMaxWidth()
            .clickable { navigateToExit() }
    ) {
        Text(
            text = stringResource(R.string.exit),
            style = TextStyle(
                fontSize = 14.sp,
                color = Color(0xFF000000),
            ),
            modifier = Modifier
        )
    }
}
