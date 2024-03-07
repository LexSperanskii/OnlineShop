package com.example.onlineshop.ui.screens.catalogAndProductScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.onlineshop.R
import com.example.onlineshop.model.CommodityItem
import com.example.onlineshop.ui.screens.NavigationBottomAppBar
import com.example.onlineshop.ui.screens.TopAppBarNameOnly
import com.example.onlineshop.ui.theme.OnlineShopTheme


@Composable
fun CatalogScreen(
    navController: NavHostController,
    title :Int,
    navigateToProductPage : () -> Unit,
    modifier: Modifier = Modifier,
    catalogProductScreenViewModel: CatalogProductScreenViewModel
) {
    val catalogScreenUiState = catalogProductScreenViewModel.catalogScreenUiState.collectAsState().value
    val catalogScreenNetworkUiState = catalogProductScreenViewModel.catalogScreenNetworkUiState
    Scaffold(
        topBar = {
            TopAppBarNameOnly(
                currentDestinationTitle = stringResource(title),
            )
        },
        bottomBar = {
            NavigationBottomAppBar(navController)
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SortingRow(
                isExpanded = catalogScreenUiState.isExpanded,
                sortType = catalogScreenUiState.sortType,
                expandChange = {catalogProductScreenViewModel.expandChange()},
                listOfTypes = catalogScreenUiState.listOfTypes,
                onDropdownMenuItemClick = {catalogProductScreenViewModel.onDropdownMenuItemClick(it)},
                modifier = Modifier.fillMaxWidth()
            )
            TagRow(
                listOfTags = catalogScreenUiState.listOfTags,
                currentTag = catalogScreenUiState.currentTag,
                onTagClick = { catalogProductScreenViewModel.onTagClick(it) },
                onEraseTagClick = {catalogProductScreenViewModel.onEraseTagClick()},
                modifier = Modifier.fillMaxWidth()
            )
            when (catalogScreenNetworkUiState) {
                is CatalogScreenNetworkUiState.Loading ->
                    LoadingScreen(modifier = modifier.fillMaxSize())
                is CatalogScreenNetworkUiState.Success ->
                    CommodityItemsGridScreen(
                        productItems = catalogScreenUiState.listOfProducts,
                        onHeartSignClick = { catalogProductScreenViewModel.saveOrDeleteFromFavorites(it) },
                        addToCart = {}, //Не функциональная кнопка
                        onCardClick = {
                            catalogProductScreenViewModel.chooseCommodityItem(it)
                            navigateToProductPage()
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                is CatalogScreenNetworkUiState.Error ->
                    ErrorScreen(
                        retryAction = {catalogProductScreenViewModel.getCommodityItemsInfo()},
                        modifier = modifier.fillMaxSize()
                    )
            }
        }
    }
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.ic_loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerImage(
    productItem : CommodityItem,
    modifier: Modifier = Modifier
){
    val pagerState = rememberPagerState(pageCount = {productItem.images.listOfImage.size})

    Column(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
        ) { page ->
            Image(
                painter = painterResource(id = productItem.images.listOfImage[page]),
                contentDescription = stringResource(R.string.product_image),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) Color(0xFFD62F89) else  Color(0xFFDEDEDE)
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(4.dp)
                )
            }
        }
    }
}

@Composable
fun CommodityItem(
    productItem : CommodityItem,
    onHeartSignClick: (CommodityItem)->Unit,
    addToCart : ()->Unit,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(287.dp)
            .width(168.dp)
            .padding(3.dp)
            .clickable(onClick = onCardClick),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.weight(1f)) {
                Box( modifier = Modifier.fillMaxSize()) {
                    PagerImage(
                        productItem = productItem
                    )
                    IconButton(
                        onClick = {onHeartSignClick(productItem)},
                        modifier = Modifier
                            .align(Alignment.TopEnd)

                    ) {
                        Icon(
                            painter = if(productItem.isFavourite) painterResource(id = R.drawable.ic_heart_filled) else painterResource(id = R.drawable.ic_heart_outlined) ,
                            contentDescription = stringResource(R.string.add_to_favorites),
                            tint = Color(0xFFD62F89),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
            Column(modifier = Modifier.weight(1f)) {
                Box (modifier = Modifier.fillMaxSize()){
                    Column(modifier = Modifier
                        .padding(horizontal = 6.dp)
                        .fillMaxSize()
                    ) {
                        Text(
                            text = stringResource(R.string.price_with_sign,productItem.productDescription.price.price, productItem.productDescription.price.unit ),
                            style = TextStyle(
                                fontSize = 9.sp,
                                color = Color(0xFFA0A1A3),
                                textDecoration = TextDecoration.LineThrough
                            ),
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 2.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.price_with_sign, productItem.productDescription.price.priceWithDiscount, productItem.productDescription.price.unit ),
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = Color(0xFF000000),
                                ),
                                modifier = Modifier
                            )
                            Box(
                                contentAlignment = Alignment.Center ,
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .clip(MaterialTheme.shapes.extraSmall)
                                    .background(Color(0xFFD62F89))
                                    .height(16.dp)
                                    .width(34.dp)
                            ){
                                Text(
                                    text = stringResource(R.string.discount, productItem.productDescription.price.discount),
                                    style = TextStyle(
                                        fontSize = 9.sp,
                                        color = Color(0xFFFFFFFF),
                                    )
                                )
                            }
                        }
                        Text(
                            text = productItem.productDescription.title,
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = Color(0xFF000000),
                            ),
                            modifier = Modifier.padding(bottom = 2.dp)
                        )
                        Text(
                            text = productItem.productDescription.subtitle,
                            style = TextStyle(
                                fontSize = 10.sp,
                                color = Color(0xFF3E3E3E),
                            ),
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        if (productItem.productDescription.feedback!=null){
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_star),
                                    contentDescription = stringResource(R.string.rating),
                                    tint = Color(0xFFF9A249),
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = productItem.productDescription.feedback.rating.toString(),
                                    style = TextStyle(
                                        fontSize = 9.sp,
                                        color = Color(0xFFF9A249),
                                    ),
                                    modifier = Modifier.padding(horizontal = 2.dp)
                                )
                                Text(
                                    text = stringResource(R.string.feedbackCount, productItem.productDescription.feedback.count),
                                    style = TextStyle(
                                        fontSize = 9.sp,
                                        color = Color(0xFFA0A1A3),
                                    ),
                                    modifier = Modifier
                                )
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .wrapContentHeight()
                            .clickable { addToCart() }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_add_to_cart_plus_sign),
                            contentDescription = stringResource(id = R.string.add_to_cart),
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(32.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CommodityItemsGridScreen(
    productItems : List<CommodityItem>,
    onHeartSignClick: (CommodityItem)->Unit,
    addToCart : ()->Unit,
    onCardClick: (CommodityItem) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(13.dp)
    ) {
        items(items = productItems) { item ->
            CommodityItem(
                productItem  = item,
                onHeartSignClick = onHeartSignClick,
                addToCart = addToCart,
                onCardClick = {onCardClick(item)},
                modifier = modifier
//                    .aspectRatio(0.5f) //сделать прямоугольным
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Sorting(
    isExpanded: Boolean,
    sortType: String,
    expandChange: ()->Unit,
    listOfTypes : List<String>,
    onDropdownMenuItemClick: (String)->Unit,
    modifier: Modifier = Modifier
) {
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = {expandChange()},
        modifier = modifier
    ) {
        Text(
            text = sortType,
            fontSize = 14.sp,
            color = Color(0xFF3E3E3E),
            modifier = Modifier
                .menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { expandChange() }
        ) {
            listOfTypes.forEach {
                DropdownMenuItem(
                    text = { Text(text = it) },
                    onClick = {onDropdownMenuItemClick(it)}
                )
            }
        }
    }
}

@Composable
fun SortingRow(
    isExpanded: Boolean,
    sortType: String,
    expandChange: ()->Unit,
    listOfTypes : List<String>,
    onDropdownMenuItemClick: (String)->Unit,
    modifier: Modifier = Modifier
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_sort),
            contentDescription = stringResource(R.string.sort),
            modifier = Modifier
                .size(24.dp)
                .padding(end = 4.dp)
        )
        Sorting(
            isExpanded = isExpanded,
            sortType = sortType,
            expandChange = expandChange,
            listOfTypes = listOfTypes,
            onDropdownMenuItemClick = onDropdownMenuItemClick,
        )
        if (isExpanded) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = stringResource(R.string.close),
                modifier = Modifier.size(24.dp)
            )
        } else {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = stringResource(R.string.open),
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            horizontalArrangement = Arrangement.End,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_filter),
                contentDescription = stringResource(R.string.filter),
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = stringResource(R.string.filters),
                fontSize = 14.sp,
                color = Color(0xFF3E3E3E)
            )
        }
    }
}

@Composable
fun TagRow(
    listOfTags : List<String>,
    currentTag : String,
    onTagClick: (String)->Unit,
    onEraseTagClick : () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(items = listOfTags) { tag ->
            if (tag == currentTag){
                Button(
                    onClick = { onTagClick(tag) },
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF52606D),
                    )
                ) {
                    Text(
                        text = tag,
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color(0xFFFFFFFF),
                        )
                    )
                    IconButton(
                        onClick = onEraseTagClick,
                        modifier = Modifier.size(20.dp)
                    ) {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = stringResource(R.string.delete),
                        )
                    }
                }
            }else{
                Button(
                    onClick = { onTagClick(tag) },
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF8F8F8),
                    )
                ) {
                    Text(
                        text = tag,
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color(0xFFA0A1A3),
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    OnlineShopTheme {
        SortingRow(
            isExpanded = false,
            sortType = "По популярности",
            expandChange = {},
            listOfTypes = listOf("По популярности", "По уменьшению цены", "По возрастанию цены"),
            onDropdownMenuItemClick = {}
        )
    }
}