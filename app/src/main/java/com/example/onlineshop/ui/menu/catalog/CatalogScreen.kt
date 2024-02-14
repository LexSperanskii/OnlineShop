package com.example.onlineshop.ui.menu.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.onlineshop.R
import com.example.onlineshop.ui.AppViewModelProvider
import com.example.onlineshop.ui.OnlineShopTopAppBar
import com.example.onlineshop.ui.menu.NavigationBottomAppBar
import com.example.onlineshop.ui.theme.OnlineShopTheme

@Composable
fun CatalogScreen(
    navController: NavHostController,
    title :String,
    modifier: Modifier = Modifier,
    catalogScreenVIewModel: CatalogScreenVIewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val sortingRowUiState = catalogScreenVIewModel.sortingRowUiState.collectAsState().value
    Scaffold(
        topBar = {
            OnlineShopTopAppBar(
                title = title,
            )
        },
        bottomBar = {
            NavigationBottomAppBar(navController)
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SortingRow(
                isExpanded = sortingRowUiState.isExpanded,
                sortType = sortingRowUiState.sortType,
                expandChange = {catalogScreenVIewModel.expandChange()},
                listOfTypes = sortingRowUiState.listOfTypes,
                onDropdownMenuItemClick = {catalogScreenVIewModel.onDropdownMenuItemClick(it)}
            )
        }
    }
}

//@Composable
//fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
//    Column(
//        modifier = modifier,
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
//        )
//        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
//        Button(onClick = retryAction) {
//            Text(stringResource(R.string.retry))
//        }
//    }
//}
//
//@Composable
//fun LoadingScreen(modifier: Modifier = Modifier) {
//    Image(
//        modifier = modifier.size(200.dp),
//        painter = painterResource(R.drawable.loading_img),
//        contentDescription = stringResource(R.string.loading)
//    )
//}
//
//@Composable
//fun CommodityItem(info: Amphibians, modifier: Modifier = Modifier) {
//    Card(
//        modifier = modifier,
//        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
//    ) {
//        Column {
//            Text(
//                text = stringResource(R.string.name_and_type,info.name,info.type),
//                style = MaterialTheme.typography.bodyLarge,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(16.dp)
//            )
//            AsyncImage(
//                model = ImageRequest.Builder(context = LocalContext.current)
//                    .data(info.imgSrc)
//                    .crossfade(true)//плавное затухание
//                    .build(),
//                contentScale = ContentScale.FillWidth ,
//                error = painterResource(R.drawable.ic_broken_image),
//                placeholder = painterResource(R.drawable.loading_img),
//                contentDescription = stringResource(R.string.amphibians),
//                modifier = Modifier
//                    .fillMaxWidth()
//            )
//            Text(
//                text = info.description,
//                style = MaterialTheme.typography.bodyMedium,
//                modifier = Modifier.padding(16.dp)
//            )
//        }
//    }
//}
//
//@Composable
//fun BookshelfGridScreen(covers: List<String>, modifier: Modifier = Modifier) {
//    LazyVerticalGrid(
//        columns = GridCells.Adaptive(150.dp),
//        modifier = modifier.fillMaxWidth(),
//        contentPadding = PaddingValues(4.dp)
//    ) {
//        items(items = covers) { cover ->
//            BookshelfCovers(
//                cover,
//                modifier = modifier
//                    .padding(4.dp)
//                    .fillMaxWidth()
//                    .aspectRatio(0.5f)
//            )
//        }
//    }
//}

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
//        TextField(
//            value = sortType ,
//            onValueChange = {},
//            readOnly = true,
//            singleLine = true,
//            textStyle = TextStyle(
//                fontSize = 14.sp,
//                color = Color(0xFF3E3E3E),
//            ),
//            leadingIcon = {
//                Icon(
//                    painter = painterResource(R.drawable.icon_sort),
//                    contentDescription = "Сортировка",
//                    modifier = Modifier
//                        .size(24.dp)
//                )
//            },
//            trailingIcon = {
//                Row (modifier = Modifier.offset(x = 0.dp)){
//                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
//                }
//
//            },
//            colors = TextFieldDefaults.colors(
//                focusedContainerColor = Color.Transparent,
//                unfocusedContainerColor = Color.Transparent,
//                disabledTextColor = Color.Transparent,
//                focusedIndicatorColor = Color.Transparent,
//                unfocusedIndicatorColor = Color.Transparent,
//                disabledIndicatorColor = Color.Transparent
//            ),
//            modifier = Modifier
//                .menuAnchor()
//                .width(250.dp)
//        )
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
            .padding(
                start = 16.dp,
                end = 16.dp
            )
            .fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(R.drawable.icon_sort),
            contentDescription = "Сортировка",
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
                contentDescription = "Фильтр",
                modifier = Modifier.size(24.dp)
            )
        } else {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Фильтр",
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            horizontalArrangement = Arrangement.End,
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_filter),
                contentDescription = "Фильтр",
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp)
            )
            Text(
                text = "Фильтры",
                fontSize = 14.sp,
                color = Color(0xFF3E3E3E)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    OnlineShopTheme {
    }
}