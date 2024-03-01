package com.example.onlineshop.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
fun TopAppBarNameOnly(
    currentDestinationTitle: String,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = currentDestinationTitle,
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color(0xFF000000),
                    ),
                    modifier = Modifier
                )
            }
        },
        modifier = modifier
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarBackAndShare(
    onClickNavigateBack: () -> Unit,
    onCLickShare: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = onClickNavigateBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back_button)
                )
            }
        },
        actions = {
            IconButton(onClick = onCLickShare) {
                Icon(
                    painterResource(id = R.drawable.ic_share),
                    contentDescription = stringResource(R.string.icon_share)
                )
            }
        },
        modifier = modifier
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarBackAndName(
    currentDestinationTitle: String,
    onClickNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Row(horizontalArrangement = Arrangement.Center) {
                Text(
                    text = currentDestinationTitle,
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color(0xFF000000),
                    ),
                    modifier = Modifier.padding(start = 28.dp)
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onClickNavigateBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back_button)
                )
            }
        },
        modifier = modifier
    )
}