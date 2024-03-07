package com.example.onlineshop.ui.screens.catalogAndProductScreen


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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.onlineshop.R
import com.example.onlineshop.model.CommodityItem
import com.example.onlineshop.ui.screens.NavigationBottomAppBar
import com.example.onlineshop.ui.screens.TopAppBarBackAndShare

@Composable
fun ProductScreen(
    navController: NavHostController,
    onClickNavigateBack: ()->Unit,
    onCLickShare: ()->Unit,
    modifier: Modifier = Modifier,
    previousRoute: String,
    catalogProductScreenViewModel: CatalogProductScreenViewModel
) {
    val productScreenUiState = catalogProductScreenViewModel.productScreenUiState.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBarBackAndShare(
                onClickNavigateBack = onClickNavigateBack,
                onCLickShare = onCLickShare
            )
        },
        bottomBar = {
            NavigationBottomAppBar(
                navController = navController,
                previousRoute = previousRoute
            )
        }
    ) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            ProductScreenBody(
                productItem = productScreenUiState.commodityItem,
                onHeartSignClick = { catalogProductScreenViewModel.saveOrDeleteFromFavorites(it) },
                onQuestionSignClick = {},
                onBrandButtonClick = {},
                isShowInfo = productScreenUiState.isShowInfo,
                hideShowInfo = { catalogProductScreenViewModel.hideShowInfo() },
                onCopyButtonClick = {},
                isShowCompound = productScreenUiState.isShowCompound,
                hideCompoundInfo = { catalogProductScreenViewModel.hideShowCompoundInfo() },
                addToCartButton = {},
            )
        }
    }
}

@Composable
fun ProductScreenBody(
    productItem : CommodityItem,
    onHeartSignClick: (CommodityItem)->Unit,
    onQuestionSignClick: ()->Unit,
    onBrandButtonClick: ()->Unit,
    isShowInfo:Boolean,
    hideShowInfo: ()->Unit,
    onCopyButtonClick: () -> Unit,
    isShowCompound: Boolean,
    hideCompoundInfo: () -> Unit,
    addToCartButton: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
            .verticalScroll(enabled = true, state = rememberScrollState())
    ) {
        PagerBox(
            productItem = productItem,
            onHeartSignClick = onHeartSignClick,
            onQuestionSignClick = onQuestionSignClick,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        NamePart(
            productItem = productItem,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        HorizontalDivider(
            color = Color(0xFFF8F8F8),
            thickness = 1.dp,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        RatingRow(
            productItem = productItem,
            modifier = modifier.padding(bottom = 16.dp)
        )
        PriceRow(
            productItem = productItem,
            modifier = Modifier.padding(start = 5.dp, bottom = 24.dp)
        )
        DescriptionPart(
            productItem = productItem,
            onBrandButtonClick = onBrandButtonClick,
            isShowInfo = isShowInfo,
            hideShowInfo = hideShowInfo,
            modifier = Modifier.padding(bottom = 34.dp)
        )
        CharacteristicsPart(
            productItem = productItem,
            modifier = Modifier.padding(bottom = 34.dp)
        )
        Compound(
            productItem = productItem,
            onCopyButtonClick = onCopyButtonClick,
            isShowCompound = isShowCompound,
            hideCompoundInfo = hideCompoundInfo,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        AddToCartButton(
            productItem = productItem,
            addToCartButton = addToCartButton
        )
    }
}

@Composable
fun PagerBox(
    productItem : CommodityItem,
    onHeartSignClick: (CommodityItem) -> Unit,
    onQuestionSignClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier.background(Color.White)
    ) {
        PagerImage(
            productItem = productItem,
            modifier = Modifier.height(294.dp)
        )
        IconButton(
            onClick = {onHeartSignClick(productItem)},
            modifier = Modifier
                .align(Alignment.TopEnd)

        ) {
            Icon(
                painter = if(productItem.isFavourite) painterResource(id = R.drawable.ic_heart_filled) else painterResource(id = R.drawable.ic_heart_outlined) ,
                contentDescription = stringResource(id = R.string.add_to_favorites),
                tint = Color(0xFFD62F89),
                modifier = Modifier.size(24.dp)
            )
        }
        IconButton(
            onClick = onQuestionSignClick,
            modifier = Modifier
                .align(Alignment.BottomStart)

        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_question),
                contentDescription = stringResource(R.string.info),
                tint = Color(0xFFDEDEDE),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
@Composable
fun NamePart(
    productItem : CommodityItem,
    modifier: Modifier = Modifier
){
    Column( modifier = modifier ) {
        Text(
            text = productItem.productDescription.title,
            style = TextStyle(
                fontSize = 16.sp,
                color = Color(0xFFA0A1A3),
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = productItem.productDescription.subtitle,
            style = TextStyle(
                fontSize = 20.sp,
                color = Color(0xFF000000),
            ),
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Text(
            text =  LocalContext.current.resources.getQuantityString  (
                R.plurals.available_products,
                productItem.productDescription.available,
                productItem.productDescription.available
            ),
            style = TextStyle(
                fontSize = 12.sp,
                color = Color(0xFFA0A1A3),
            )
        )
    }
}
@Composable
fun RatingRow(
    productItem : CommodityItem,
    modifier: Modifier = Modifier
){
    if (productItem.productDescription.feedback!=null){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            RatingBar(
                rating = productItem.productDescription.feedback.rating
            )
            Text(
                text = productItem.productDescription.feedback.rating.toString(),
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color(0xFFA0A1A3),
                ),
                modifier = Modifier.padding(start = 8.dp, end = 6.dp)
            )
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color(0xFFA0A1A3))
                    .size(2.dp)
            )
            Text(
                text =  LocalContext.current.resources.getQuantityString (
                    R.plurals.feedbacks,
                    productItem.productDescription.feedback.count,
                    productItem.productDescription.feedback.count
                ),
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color(0xFFA0A1A3),
                ),
                modifier = Modifier.padding(start = 6.dp)
            )
        }
    }
}
@Composable
fun PriceRow(
    productItem : CommodityItem,
    modifier: Modifier = Modifier
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.price_with_sign, productItem.productDescription.price.priceWithDiscount, productItem.productDescription.price.unit ),
            style = TextStyle(
                fontSize = 24.sp,
                color = Color(0xFF000000),
            ),
            modifier = Modifier
        )
        Text(
            text = stringResource(R.string.price_with_sign,productItem.productDescription.price.price, productItem.productDescription.price.unit ),
            style = TextStyle(
                fontSize = 12.sp,
                color = Color(0xFFA0A1A3),
                textDecoration = TextDecoration.LineThrough
            ),
            modifier = Modifier.padding(horizontal = 11.dp)
        )
        Box(
            contentAlignment = Alignment.Center ,
            modifier = Modifier
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
}
@Composable
fun DescriptionPart(
    productItem : CommodityItem,
    onBrandButtonClick: ()->Unit,
    isShowInfo:Boolean,
    hideShowInfo: ()->Unit,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.description),
            style = TextStyle(
                fontSize = 16.sp,
                color = Color(0xFF000000),
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        if (isShowInfo){
            Box(
                contentAlignment = Alignment.Center ,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraSmall)
                    .wrapContentSize()
                    .background(Color(0xFFF8F8F8))
                    .clickable { onBrandButtonClick() }
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = productItem.productDescription.title,
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color(0xFF000000),
                        ),
                        modifier = Modifier.padding(start = 9.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = stringResource(R.string.arrow_forward),
                        tint = Color(0xFF000000),
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 12.dp),
                    )
                }
            }
            Text(
                text = productItem.productDescription.description,
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color(0xFF3E3E3E),
                ),
                modifier = Modifier.padding(top = 8.dp, bottom = 10.dp)
            )
        }
        Text(
            text = if(isShowInfo)stringResource(R.string.hide) else stringResource(R.string.show),
            style = TextStyle(
                fontSize = 12.sp,
                color = Color(0xFFA0A1A3),
            ),
            modifier = Modifier
                .clickable { hideShowInfo() }
        )
    }
}
@Composable
fun CharacteristicsPart(
    productItem : CommodityItem,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.characteristics),
            style = TextStyle(
                fontSize = 16.sp,
                color = Color(0xFF000000),
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        productItem.productDescription.info.forEach { item ->
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(31.dp)
            ) {
                Text(
                    text = item.title,
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color(0xFF3E3E3E),
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = item.value,
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color(0xFF3E3E3E),
                    )
                )
            }
            HorizontalDivider(
                color = Color(0xFFDEDEDE),
                thickness = 1.dp,
                modifier = Modifier
            )
        }
    }
}
@Composable
fun Compound(
    productItem: CommodityItem,
    onCopyButtonClick: () -> Unit,
    isShowCompound: Boolean,
    hideCompoundInfo: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ){
            Text(
                text = stringResource(R.string.compound),
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color(0xFF000000),
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = onCopyButtonClick,
                modifier = Modifier

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_copy),
                    contentDescription = stringResource(R.string.copy),
                    tint = Color(0xFFA0A1A3),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Text(
            text = productItem.productDescription.ingredients,
            maxLines = if (isShowCompound) Int.MAX_VALUE else 2,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                fontSize = 12.sp,
                color = Color(0xFF3E3E3E),
            ),
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Text(
            text = if(isShowCompound)stringResource(R.string.hide) else stringResource(R.string.show),
            style = TextStyle(
                fontSize = 12.sp,
                color = Color(0xFFA0A1A3),
            ),
            modifier = Modifier
                .clickable { hideCompoundInfo() }
        )
    }
}
@Composable
fun AddToCartButton(
    productItem: CommodityItem,
    addToCartButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Box(
            contentAlignment = Alignment.Center ,
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .background(Color(0xFFD62F89))
                .height(51.dp)
                .fillMaxWidth()
                .clickable { addToCartButton() }
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.price_with_sign, productItem.productDescription.price.priceWithDiscount, productItem.productDescription.price.unit ),
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color(0xFFFFFFFF),
                    ),
                    modifier = Modifier
                )
                Text(
                    text = stringResource(R.string.price_with_sign,productItem.productDescription.price.price, productItem.productDescription.price.unit ),
                    style = TextStyle(
                        fontSize = 10.sp,
                        color = Color(0xFFFFFFFF),
                        textDecoration = TextDecoration.LineThrough
                    ),
                    modifier = Modifier.padding(start = 9.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(R.string.add_to_cart),
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color(0xFFFFFFFF),
                    )
                )
            }
        }
    }
}
@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = Color(0xFFF9A249)
) {
    val filledStars = kotlin.math.floor(rating).toInt()
    val unfilledStars = (stars - kotlin.math.ceil(rating)).toInt()
    val halfStar = !(rating.rem(1).equals(0.0))
    Row(modifier = modifier) {
        repeat(filledStars) {
            Icon(
                painter = painterResource(R.drawable.ic_star_filled),
                contentDescription = stringResource(id = R.string.rating),
                tint = starsColor
            )
        }
        if (halfStar) {
            Icon(
                painter = painterResource(R.drawable.ic_star_half),
                contentDescription = stringResource(id = R.string.rating),
                tint = starsColor
            )
        }
        repeat(unfilledStars) {
            Icon(
                painter = painterResource(R.drawable.ic_star_outlined),
                contentDescription = stringResource(id = R.string.rating),
                tint = starsColor
            )
        }
    }
}
