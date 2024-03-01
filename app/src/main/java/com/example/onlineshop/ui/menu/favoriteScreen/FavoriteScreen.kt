package com.example.onlineshop.ui.menu.favoriteScreen

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.onlineshop.model.CommodityItem
import com.example.onlineshop.ui.AppViewModelProvider
import com.example.onlineshop.ui.menu.NavigationBottomAppBar
import com.example.onlineshop.ui.menu.TopAppBarBackAndName
import com.example.onlineshop.ui.menu.catalogScreen.CommodityItemsGridScreen
import com.example.onlineshop.ui.menu.catalogScreen.ErrorScreen
import com.example.onlineshop.ui.menu.catalogScreen.LoadingScreen
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
                favoriteScreenUiState = favoriteScreenUiState,
                onHeartSignClick = { favoriteScreenViewModel.deleteFromFavorites(it) }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoritesScreenBodyWithTabs(
    favoriteScreenNetworkUiState: FavoriteScreenNetworkUiState,
    favoriteScreenUiState: FavoriteScreenUiState,
    onHeartSignClick: (CommodityItem)->Unit,
    modifier: Modifier = Modifier
){
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = {FavoriteScreenTabs.values().size})
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex.value,
            containerColor = Color(0xFFF8F8F8),
            indicator = {},
            divider = {},
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .fillMaxWidth()
        ) {
            FavoriteScreenTabs.values().forEachIndexed { index, currentTab ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    selectedContentColor = Color(0xFF000000),
                    unselectedContentColor = Color(0xFFA0A1A3),
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
                                fontSize = 16.sp,
                            ),
                            modifier = Modifier
                        )
                    },
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(
                            if (selectedTabIndex.value == index) Color(0xFFFFFFFF) else Color(
                                0xFFF8F8F8
                            )
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
                    favoriteScreenUiState = favoriteScreenUiState,
                    onHeartSignClick = onHeartSignClick
                )
                1 -> BrandsScreen()
            }
        }
    }
}

@Composable
fun FavoritesScreenBody(
    favoriteScreenNetworkUiState: FavoriteScreenNetworkUiState,
    favoriteScreenUiState: FavoriteScreenUiState,
    onHeartSignClick: (CommodityItem)->Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        when (favoriteScreenNetworkUiState) {
            is FavoriteScreenNetworkUiState.Loading ->
                LoadingScreen(modifier = modifier.fillMaxSize())

            is FavoriteScreenNetworkUiState.Success ->
                CommodityItemsGridScreen(
                    productItems = favoriteScreenUiState.listOfProducts,
                    onHeartSignClick = onHeartSignClick,
                    addToCart = {}, //Не функциональная кнопка
                    onCardClick = {},
                    modifier = Modifier.fillMaxSize()
                )

            is FavoriteScreenNetworkUiState.Error ->
                ErrorScreen(modifier = modifier.fillMaxSize())
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
            text = "Бренды",
            modifier = Modifier
        )
    }
}
