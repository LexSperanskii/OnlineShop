package com.example.onlineshop.ui.menu.catalog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.onlineshop.R
import com.example.onlineshop.model.CommodityItem
import com.example.onlineshop.ui.AppViewModelProvider
import com.example.onlineshop.ui.menu.NavigationBottomAppBar
import com.example.onlineshop.ui.menu.OnlineShopTopAppBar

@Composable
fun ProductScreen(
    title :Int,
    navController: NavHostController,
    navigateBack: ()->Unit,
    modifier: Modifier = Modifier,
    previousRoute: String,
    catalogScreenVIewModel: CatalogScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val catalogScreenUiState = catalogScreenVIewModel.catalogScreenUiState.collectAsState().value

    Scaffold(
        topBar = {
            OnlineShopTopAppBar(
                title = stringResource(id = title),
                navigateBack = navigateBack,
                canNavigateBack = true
            )
        },
        bottomBar = {
            NavigationBottomAppBar(navController, previousRoute = previousRoute)
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductScreenBody(
    productItem : CommodityItem,
    onHeartSignClick: (CommodityItem)->Unit,
    onQuestionSignClick: ()->Unit,
    onBrandButtonClick: ()->Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Box {
            PagerImage(
                productItem = productItem
            )
            IconButton(
                onClick = {onHeartSignClick(productItem)},
                modifier = Modifier
                    .align(Alignment.TopEnd)

            ) {
                Image(
                    painter = if(productItem.isFavourite) painterResource(id = R.drawable.heart_filled) else painterResource(id = R.drawable.heart_outlined) ,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(24.dp)
                )
            }
            IconButton(
                onClick = onQuestionSignClick,
                modifier = Modifier
                    .align(Alignment.BottomStart)

            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_question),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Text(
            text = productItem.productDescription.title,
            style = TextStyle(
                fontSize = 16.sp,
                color = Color(0xFFA0A1A3),
            ),
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
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
            text = pluralStringResource(
                id = R.plurals.available_products,
                count = productItem.productDescription.available,
                productItem.productDescription.available
            ),
            style = TextStyle(
                fontSize = 12.sp,
                color = Color(0xFFA0A1A3),
            ),
            modifier = Modifier.padding(bottom = 10.dp)
        )
        HorizontalDivider(
            color = Color(0xFFF8F8F8),
            thickness = 1.dp,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        if (productItem.productDescription.feedback!=null){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                RatingBar(
                    rating = productItem.productDescription.feedback.rating.toFloat(),
                    spaceBetween = 3.dp
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
                    text = pluralStringResource(
                        id = R.plurals.feedbacks,
                        count = productItem.productDescription.feedback.count,
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 5.dp, bottom = 24.dp)
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
            text = stringResource(R.string.description),
            style = TextStyle(
                fontSize = 16.sp,
                color = Color(0x000000),
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(Color(0xFFF8F8F8)),
            onClick = onBrandButtonClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(bottom = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(
                    text = productItem.productDescription.title,
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color(0xFF000000),
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight, //painter =painterResource(id = R.drawable.ic_arrow_forward)
                    contentDescription = "Delete",
                    modifier = modifier.size(24.dp)
                )
            }
        }
        Text(
            text = productItem.productDescription.description,
            style = TextStyle(
                fontSize = 12.sp,
                color = Color(0xFF3E3E3E),
            ),
            modifier = Modifier.padding(bottom = 10.dp)
        )
    }
}


//https://stackoverflow.com/questions/73948960/jetpack-compose-how-to-create-a-rating-bar - тут брал код
@Composable
private fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    spaceBetween: Dp = 0.dp
) {

    val image = ImageBitmap.imageResource(id = R.drawable.ic_star_outlined)
    val imageFull = ImageBitmap.imageResource(id = R.drawable.ic_star_filled)

    val totalCount = 5

    val height = LocalDensity.current.run { image.height.toDp() }
    val width = LocalDensity.current.run { image.width.toDp() }
    val space = LocalDensity.current.run { spaceBetween.toPx() }
    val totalWidth = width * totalCount + spaceBetween * (totalCount - 1)


    Box(
        modifier
            .width(totalWidth)
            .height(height)
            .drawBehind {
                drawRating(rating, image, imageFull, space)
            })
}

private fun DrawScope.drawRating(
    rating: Float,
    image: ImageBitmap,
    imageFull: ImageBitmap,
    space: Float
) {

    val totalCount = 5

    val imageWidth = image.width.toFloat()
    val imageHeight = size.height

    val reminder = rating - rating.toInt()
    val ratingInt = (rating - reminder).toInt()

    for (i in 0 until totalCount) {

        val start = imageWidth * i + space * i

        drawImage(
            image = image,
            topLeft = Offset(start, 0f)
        )
    }

    drawWithLayer {
        for (i in 0 until totalCount) {
            val start = imageWidth * i + space * i
            // Destination
            drawImage(
                image = imageFull,
                topLeft = Offset(start, 0f)
            )
        }

        val end = imageWidth * totalCount + space * (totalCount - 1)
        val start = rating * imageWidth + ratingInt * space
        val size = end - start

        // Source
        drawRect(
            Color.Transparent,
            topLeft = Offset(start, 0f),
            size = Size(size, height = imageHeight),
            blendMode = BlendMode.SrcIn
        )
    }
}
private fun DrawScope.drawWithLayer(block: DrawScope.() -> Unit) {
    with(drawContext.canvas.nativeCanvas) {
        val checkPoint = saveLayer(null, null)
        block()
        restoreToCount(checkPoint)
    }
}