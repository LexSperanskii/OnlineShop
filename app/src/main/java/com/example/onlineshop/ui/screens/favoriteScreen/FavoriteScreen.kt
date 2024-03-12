package com.example.onlineshop.ui.screens.favoriteScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.onlineshop.R
import com.example.onlineshop.model.CommodityItem
import com.example.onlineshop.ui.AppViewModelProvider
import com.example.onlineshop.ui.screens.NavigationBottomAppBar
import com.example.onlineshop.ui.screens.TopAppBarBackAndName
import com.example.onlineshop.ui.screens.catalogAndProductScreen.CommodityItemsGridScreen
import com.example.onlineshop.ui.screens.catalogAndProductScreen.ErrorScreen
import com.example.onlineshop.ui.screens.catalogAndProductScreen.LoadingScreen
import kotlinx.coroutines.launch

enum class FavoriteScreenTabs(val text: String){
    Goods(text = "Товары"),
    Brands(text = "Бренды")
}
@Composable
fun FavoritesScreen(
    navController: NavHostController,
    title: Int,
    onClickNavigateBack: () -> Unit,
    previousRoute: String,
    modifier: Modifier = Modifier,
    favoriteScreenViewModel: FavoriteScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val favoriteScreenUiState = favoriteScreenViewModel.favoriteScreenUiState.collectAsState().value
    val favoriteScreenNetworkUiState = favoriteScreenViewModel.favoriteScreenNetworkUiState
    Scaffold(
        topBar = {
            TopAppBarBackAndName(
                currentDestinationTitle = stringResource(id = title),
                onClickNavigateBack = onClickNavigateBack
            )
        },
        bottomBar = {
            NavigationBottomAppBar(
                navController = navController,
                previousRoute = previousRoute
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            FavoritesScreenBodyWithTabs(
                favoriteScreenNetworkUiState = favoriteScreenNetworkUiState,
                productItems = favoriteScreenUiState.listOfProducts,
                onHeartSignClick = { favoriteScreenViewModel.deleteFromFavorites(it) },
                retryAction = {favoriteScreenViewModel.getCommodityItemsInfo()}
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoritesScreenBodyWithTabs(
    favoriteScreenNetworkUiState: FavoriteScreenNetworkUiState,
    productItems: List<CommodityItem>,
    onHeartSignClick: (CommodityItem)->Unit,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
){
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = {FavoriteScreenTabs.values().size})
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex.value,
            containerColor = colorResource(id = R.color.white_grey),
            indicator = {},
            divider = {},
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.size_16))
        ) {
            FavoriteScreenTabs.values().forEachIndexed { index, currentTab ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    selectedContentColor = colorResource(id = R.color.black),
                    unselectedContentColor = colorResource(id = R.color.light_grey),
                    onClick = {
                        //для анимации прокрутки
                        scope.launch {
                            pagerState.animateScrollToPage(currentTab.ordinal)
                        }
                    },
                    text = {
                        Text(
                            text = currentTab.text,
                            style = TextStyle(
                                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                            ),
                            modifier = Modifier
                        )
                    },
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.size_4))
                        .clip(MaterialTheme.shapes.small)
                        .background(
                            if (selectedTabIndex.value == index) colorResource(id = R.color.white) else colorResource(id = R.color.white_grey)
                        )

                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
        ) { page ->
            when (page) {
                0 -> FavoritesScreenBody(
                    favoriteScreenNetworkUiState = favoriteScreenNetworkUiState,
                    productItems = productItems,
                    onHeartSignClick = onHeartSignClick,
                    retryAction = retryAction
                )
                1 -> BrandsScreen()
            }
        }
    }
}

@Composable
fun FavoritesScreenBody(
    favoriteScreenNetworkUiState: FavoriteScreenNetworkUiState,
    productItems: List<CommodityItem>,
    onHeartSignClick: (CommodityItem)->Unit,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        when (favoriteScreenNetworkUiState) {
            is FavoriteScreenNetworkUiState.Loading ->
                LoadingScreen(modifier = modifier.fillMaxSize())

            is FavoriteScreenNetworkUiState.Success ->
                CommodityItemsGridScreen(
                    productItems = productItems,
                    onHeartSignClick = onHeartSignClick,
                    addToCart = {}, //Не функциональная кнопка
                    onCardClick = {},//Не функциональная кнопка
                    modifier = Modifier.fillMaxSize()
                )

            is FavoriteScreenNetworkUiState.Error ->
                ErrorScreen(
                    retryAction = retryAction,
                    modifier = modifier.fillMaxSize()
                )
        }
    }
}

@Composable
fun BrandsScreen(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.brands),
            modifier = Modifier
        )
    }
}
