package com.example.onlineshop.ui.menu

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onlineshop.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnlineShopTopAppBar(
    title: String,
    canNavigateBack: Boolean = false,
    navigateBack: () -> Unit ,
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        title = {
            if (!canNavigateBack){
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color(0xFF000000
                        ),
                    ),
                    modifier = Modifier
                        .height(21.dp)
                )
            }
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateBack) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        actions = {
            if (canNavigateBack){
                IconButton(onClick = { /* Handle icon click */ }) {
                    Icon(
                        painterResource(id = R.drawable.ic_share),
                        contentDescription = stringResource(R.string.icon_share)
                    )
                }
            }
        },
        modifier = modifier,
    )
}