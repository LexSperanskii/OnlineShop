package com.example.onlineshop.ui.screens.accountScreen

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.onlineshop.R
import com.example.onlineshop.ui.AppViewModelProvider
import com.example.onlineshop.ui.navigation.FavoritesDestination
import com.example.onlineshop.ui.screens.NavigationBottomAppBar
import com.example.onlineshop.ui.screens.TopAppBarNameOnly

@Composable
fun AccountScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    navigateToExit: ()->Unit,
    navigateToFavorites: ()->Unit,
    accountScreenViewModel: AccountScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val accountUiState = accountScreenViewModel.uiState.collectAsState().value
    val formattedPhoneNumber = if (accountUiState.accountPhoneNumber.length == 12) {
        buildString {
            append(accountUiState.accountPhoneNumber.substring(0, 2)) // Добавляем код страны
            append(" ")
            append(accountUiState.accountPhoneNumber.substring(2, 5)) // Добавляем первые три цифры номера
            append(" ")
            append(accountUiState.accountPhoneNumber.substring(5, 8)) // Добавляем первые три цифры номера
            append("-")
            append(accountUiState.accountPhoneNumber.substring(8, 10)) // Добавляем две цифры перед дефисом
            append("-")
            append(accountUiState.accountPhoneNumber.substring(10)) // Добавляем две последние цифры номера
        }
    } else {
        accountUiState.accountPhoneNumber
    }
    Scaffold(
        topBar = {
            TopAppBarNameOnly(
                currentDestinationTitle = stringResource(id = R.string.accountTitle),
            )
        },
        bottomBar = {
            NavigationBottomAppBar(navController)
        }
    ) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            AccountScreenBody(
                accountName = stringResource(id = R.string.account_name_surname, accountUiState.accountName, accountUiState.accountSurname),
                accountNumber = formattedPhoneNumber,
                favoriteDescription = if (accountUiState.favoritesQuantity != 0){
                    LocalContext.current.resources.getQuantityString(
                        R.plurals.account_quantity_favorite_items,
                        accountUiState.favoritesQuantity,
                        accountUiState.favoritesQuantity
                    )
                } else "",
                navigateToExit = {
                    accountScreenViewModel.deleteUserAndFavorites()
                    navigateToExit()
                },
                navigateToFavorites = navigateToFavorites
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
            .padding(
                top = dimensionResource(id = R.dimen.size_24),
                start = dimensionResource(id = R.dimen.size_16),
                end = dimensionResource(id = R.dimen.size_16),
                bottom = dimensionResource(id = R.dimen.size_32)
            )
    ) {
        AccountCard(
            leadingIcon = painterResource(id = R.drawable.ic_account),
            leadingIconTint = colorResource(id = R.color.red),
            trailingIcon = painterResource(R.drawable.ic_exit),
            trailingIconTint = colorResource(id = R.color.red),
            name = accountName,
            description = accountNumber,
            navigateClick = {},
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.size_24))
        )
        AccountCard(
            leadingIcon = painterResource(id = R.drawable.ic_heart_outlined),
            leadingIconTint = colorResource(id = R.color.pink),
            trailingIcon = painterResource(id = R.drawable.ic_arrow_forward),
            trailingIconTint = colorResource(id = R.color.black),
            name = stringResource(id = FavoritesDestination.title),
            description = favoriteDescription,
            navigateClick = navigateToFavorites,
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.size_8))
        )
        AccountCard(
            leadingIcon = painterResource(id = R.drawable.ic_shops),
            leadingIconTint = colorResource(id = R.color.pink),
            trailingIcon = painterResource(id = R.drawable.ic_arrow_forward),
            trailingIconTint = colorResource(id = R.color.black),
            name = stringResource(id = R.string.shops),
            navigateClick = {},
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.size_8))
        )
        AccountCard(
            leadingIcon = painterResource(id = R.drawable.ic_feedback),
            leadingIconTint = colorResource(id = R.color.yellow),
            trailingIcon = painterResource(id = R.drawable.ic_arrow_forward),
            trailingIconTint = colorResource(id = R.color.black),
            name = stringResource(id = R.string.feedback),
            navigateClick = {},
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.size_8))
        )
        AccountCard(
            leadingIcon = painterResource(id = R.drawable.ic_offer),
            leadingIconTint = colorResource(id = R.color.light_grey),
            trailingIcon = painterResource(id = R.drawable.ic_arrow_forward),
            trailingIconTint = colorResource(id = R.color.black),
            name = stringResource(id = R.string.offer),
            navigateClick = {},
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.size_8))
        )
        AccountCard(
            leadingIcon = painterResource(id = R.drawable.ic_purchase_return),
            leadingIconTint = colorResource(id = R.color.light_grey),
            trailingIcon = painterResource(id = R.drawable.ic_arrow_forward),
            trailingIconTint = colorResource(id = R.color.black),
            name = stringResource(id = R.string.purchase_return),
            navigateClick = {},
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.size_8))
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
                .background(colorResource(R.color.white_grey))
                .height(dimensionResource(id = R.dimen.size_49))
                .fillMaxWidth()
                .clickable { navigateClick() }
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.size_8))
            ) {
                Icon(
                    painter = leadingIcon,
                    contentDescription = null,
                    tint = leadingIconTint,
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.size_24))
                )
                Column(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.size_16))) {
                    Text(
                        text = name,
                        style = TextStyle(
                            fontSize = dimensionResource(id = R.dimen.text_size_14).value.sp,
                            color = colorResource(id = R.color.black),
                        )
                    )
                    if (description.isNotEmpty())
                        Text(
                            text = description,
                            style = TextStyle(
                                fontSize = dimensionResource(id = R.dimen.text_size_10).value.sp,
                                color = colorResource(id = R.color.light_grey),
                            ) ,
                            modifier = Modifier.padding(top = dimensionResource(R.dimen.size_6))
                        )
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = trailingIcon,
                    contentDescription = null,
                    tint = trailingIconTint,
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.size_24))
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
            .background(colorResource(R.color.white_grey))
            .height(dimensionResource(R.dimen.size_51))
            .fillMaxWidth()
            .clickable { navigateToExit() }
    ) {
        Text(
            text = stringResource(R.string.exit),
            style = TextStyle(
                fontSize = dimensionResource(id = R.dimen.text_size_14).value.sp,
                color = colorResource(R.color.black),
            ),
            modifier = Modifier
        )
    }
}
