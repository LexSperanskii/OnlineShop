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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
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
            .padding(
                top = dimensionResource(R.dimen.size_16),
                start = dimensionResource(R.dimen.size_16),
                end = dimensionResource(R.dimen.size_16),
                bottom = dimensionResource(R.dimen.size_8)
            )
            .verticalScroll(enabled = true, state = rememberScrollState())
    ) {
        PagerBox(
            productItem = productItem,
            onHeartSignClick = onHeartSignClick,
            onQuestionSignClick = onQuestionSignClick,
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.size_16))
        )
        NamePart(
            productItem = productItem,
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.size_10))
        )
        HorizontalDivider(
            color = colorResource(id = R.color.white_grey),
            thickness = dimensionResource(R.dimen.size_1),
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.size_10))
        )
        RatingRow(
            productItem = productItem,
            modifier = modifier.padding(bottom = dimensionResource(R.dimen.size_16))
        )
        PriceRow(
            productItem = productItem,
            modifier = Modifier.padding(start = dimensionResource(R.dimen.size_5), bottom = dimensionResource(R.dimen.size_24))
        )
        DescriptionPart(
            productItem = productItem,
            onBrandButtonClick = onBrandButtonClick,
            isShowInfo = isShowInfo,
            hideShowInfo = hideShowInfo,
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.size_34))
        )
        CharacteristicsPart(
            productItem = productItem,
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.size_34))
        )
        Compound(
            productItem = productItem,
            onCopyButtonClick = onCopyButtonClick,
            isShowCompound = isShowCompound,
            hideCompoundInfo = hideCompoundInfo,
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.size_32))
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
            modifier = Modifier.height(dimensionResource(R.dimen.size_294))
        )
        IconButton(
            onClick = {onHeartSignClick(productItem)},
            modifier = Modifier
                .align(Alignment.TopEnd)

        ) {
            Icon(
                painter = if(productItem.isFavourite) painterResource(id = R.drawable.ic_heart_filled) else painterResource(id = R.drawable.ic_heart_outlined) ,
                contentDescription = stringResource(id = R.string.add_to_favorites),
                tint = colorResource(id = R.color.pink),
                modifier = Modifier.size(dimensionResource(R.dimen.size_24))
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
                tint = colorResource(id = R.color.light_light_gary),
                modifier = Modifier.size(dimensionResource(R.dimen.size_24))
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
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                color = colorResource(id = R.color.light_grey),
            ),
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.size_8))
        )
        Text(
            text = productItem.productDescription.subtitle,
            style = TextStyle(
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                color = colorResource(id = R.color.black),
            ),
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.size_10))
        )
        Text(
            text =  LocalContext.current.resources.getQuantityString  (
                R.plurals.available_products,
                productItem.productDescription.available,
                productItem.productDescription.available
            ),
            style = TextStyle(
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                color = colorResource(id = R.color.light_grey),
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
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    color = colorResource(id = R.color.light_grey),
                ),
                modifier = Modifier.padding(start = dimensionResource(R.dimen.size_8), end = dimensionResource(R.dimen.size_6))
            )
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(colorResource(id = R.color.light_grey))
                    .size(dimensionResource(R.dimen.size_2))
            )
            Text(
                text =  LocalContext.current.resources.getQuantityString (
                    R.plurals.feedbacks,
                    productItem.productDescription.feedback.count,
                    productItem.productDescription.feedback.count
                ),
                style = TextStyle(
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    color = colorResource(id = R.color.light_grey),
                ),
                modifier = Modifier.padding(start = dimensionResource(R.dimen.size_6))
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
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                color = colorResource(id = R.color.black),
            ),
            modifier = Modifier
        )
        Text(
            text = stringResource(R.string.price_with_sign,productItem.productDescription.price.price, productItem.productDescription.price.unit ),
            style = TextStyle(
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                color = colorResource(id = R.color.light_grey),
                textDecoration = TextDecoration.LineThrough
            ),
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.size_11))
        )
        Box(
            contentAlignment = Alignment.Center ,
            modifier = Modifier
                .clip(MaterialTheme.shapes.extraSmall)
                .background(colorResource(id = R.color.pink))
                .height(dimensionResource(R.dimen.size_16))
                .width(dimensionResource(R.dimen.size_34))
        ){
            Text(
                text = stringResource(R.string.discount, productItem.productDescription.price.discount),
                style = TextStyle(
                    fontSize = MaterialTheme.typography.labelMedium.fontSize,
                    color = colorResource(id = R.color.white),
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
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                color = colorResource(id = R.color.black),
            ),
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.size_16))
        )
        if (isShowInfo){
            Box(
                contentAlignment = Alignment.Center ,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraSmall)
                    .wrapContentSize()
                    .background(colorResource(id = R.color.white_grey))
                    .clickable { onBrandButtonClick() }
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(R.dimen.size_48))
                ) {
                    Text(
                        text = productItem.productDescription.title,
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            color = colorResource(id = R.color.black),
                        ),
                        modifier = Modifier.padding(start = dimensionResource(R.dimen.size_9))
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = stringResource(R.string.arrow_forward),
                        tint = colorResource(id = R.color.black),
                        modifier = Modifier
                            .size(dimensionResource(R.dimen.size_24))
                            .padding(end = dimensionResource(R.dimen.size_12)),
                    )
                }
            }
            Text(
                text = productItem.productDescription.description,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    color = colorResource(id = R.color.grey),
                ),
                modifier = Modifier.padding(top = dimensionResource(R.dimen.size_8), bottom = dimensionResource(R.dimen.size_10))
            )
        }
        Text(
            text = if(isShowInfo)stringResource(R.string.hide) else stringResource(R.string.show),
            style = TextStyle(
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                color = colorResource(id = R.color.light_grey),
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
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                color = colorResource(id = R.color.black),
            ),
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.size_16))
        )
        productItem.productDescription.info.forEach { item ->
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.size_31))
            ) {
                Text(
                    text = item.title,
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        color = colorResource(id = R.color.grey),
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = item.value,
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        color = colorResource(id = R.color.grey),
                    )
                )
            }
            HorizontalDivider(
                color = colorResource(id = R.color.light_light_gary),
                thickness = dimensionResource(R.dimen.size_1),
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
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.size_16))
        ){
            Text(
                text = stringResource(R.string.compound),
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    color = colorResource(id = R.color.black),
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
                    tint = colorResource(id = R.color.light_grey),
                    modifier = Modifier.size(dimensionResource(R.dimen.size_24))
                )
            }
        }
        Text(
            text = productItem.productDescription.ingredients,
            maxLines = if (isShowCompound) Int.MAX_VALUE else 2,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                color = colorResource(id = R.color.grey),
            ),
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.size_10))
        )
        Text(
            text = if(isShowCompound)stringResource(R.string.hide) else stringResource(R.string.show),
            style = TextStyle(
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                color = colorResource(id = R.color.light_grey),
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
                .background(colorResource(id = R.color.pink))
                .height(dimensionResource(R.dimen.size_51))
                .fillMaxWidth()
                .clickable { addToCartButton() }
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.size_16))
            ) {
                Text(
                    text = stringResource(R.string.price_with_sign, productItem.productDescription.price.priceWithDiscount, productItem.productDescription.price.unit ),
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        color = colorResource(id = R.color.white),
                    ),
                    modifier = Modifier
                )
                Text(
                    text = stringResource(R.string.price_with_sign,productItem.productDescription.price.price, productItem.productDescription.price.unit ),
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.labelLarge.fontSize,
                        color = colorResource(id = R.color.white),
                        textDecoration = TextDecoration.LineThrough
                    ),
                    modifier = Modifier.padding(start = dimensionResource(R.dimen.size_9))
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(R.string.add_to_cart),
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        color = colorResource(id = R.color.white),
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
    starsColor: Color = colorResource(id = R.color.yellow)
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
