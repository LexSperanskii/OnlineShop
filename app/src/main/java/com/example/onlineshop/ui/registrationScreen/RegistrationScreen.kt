package com.example.onlineshop.ui.registrationScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onlineshop.R
import com.example.onlineshop.ui.theme.OnlineShopTheme
import kotlinx.coroutines.launch

//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun RegistrationScreen(
//    navigateBack: () -> Unit,
//    onNavigateUp: () -> Unit,
//    canNavigateBack: Boolean = true,
//    viewModel: ItemEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
//) {
//    val coroutineScope = rememberCoroutineScope()
//    Scaffold(
//        topBar = {
//            InventoryTopAppBar(
//                title = stringResource(ItemEntryDestination.titleRes),
//                canNavigateBack = canNavigateBack,
//                navigateUp = onNavigateUp
//            )
//        }
//    ) { innerPadding ->
//        ItemEntryBody(
//            itemUiState = viewModel.itemUiState,
//            onItemValueChange = viewModel::updateUiState,
//            onSaveClick = {
//                coroutineScope.launch {
//                    viewModel.saveItem()
//                    navigateBack()
//                }
//            },
//            modifier = Modifier
//                .padding(innerPadding)
//                .verticalScroll(rememberScrollState())
//                .fillMaxWidth()
//        )
//    }
//}

@Composable
fun RegistrationBody(
    modifier: Modifier = Modifier,
    onButtonClick : ()->Unit,
    enabled: Boolean
) {
    Column(
        modifier = modifier
    ) {
        RegistrationForm(
            nameField =  "",
            lastNameField = "",
            number = "",
            onValueChange = {}
        )
        Button(
            onClick = onButtonClick,
            enabled = enabled,
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD62F89),
                disabledContainerColor = Color(0xFFFF8AC9)
            ), // Задаем цвет фона кнопки
            modifier = Modifier
//                .fillMaxWidth()
                .height(51.dp)
                .width(343.dp)
                .padding(
                    top = dimensionResource(id = R.dimen.padding_extra_large),
                    start = dimensionResource(id = R.dimen.padding_medium),
                    end = dimensionResource(id = R.dimen.padding_medium)
                )
        ) {
            Text(text = "Войти")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationField(
    text : String,
    placeholder: String,
    onValueChange: (String)->Unit,
    modifier: Modifier = Modifier
){
    TextField(
        value = text,
        textStyle = TextStyle(
            fontSize = 16.sp,
            color = Color(0xFF000000),
        ),
        onValueChange = { onValueChange(it) },
        singleLine = true,
        trailingIcon = {
            Icon(
                Icons.Filled.Close,
                contentDescription = "Delete",
                modifier = modifier
                    .height(13.dp)
                    .width(13.dp)
            )},
        placeholder = {
            Text(
                text = placeholder,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color(0xFFA0A1A3),
                )
            )
        },
        shape = MaterialTheme.shapes.small,
        colors = TextFieldDefaults.textFieldColors(
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        modifier = modifier
            .width(343.dp)
            .height(50.dp)
            .padding(
                top = dimensionResource(id = R.dimen.padding_medium),
                start = dimensionResource(id = R.dimen.padding_medium),
                end = dimensionResource(id = R.dimen.padding_medium)
            )
    )
}

@Composable
fun RegistrationForm(
    nameField : String,
    lastNameField : String,
    number : String,
    onValueChange: (String)->Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        RegistrationField(
            text = nameField,
            placeholder = "Имя",
            onValueChange = onValueChange
        )
        RegistrationField(
            text = lastNameField,
            placeholder = "Фамилия",
            onValueChange = onValueChange
        )
        RegistrationField(
            text = number,
            placeholder = "Номер телефона",
            onValueChange = onValueChange
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationFieldPreview() {
    OnlineShopTheme {
        RegistrationField(
            text = "",
            placeholder = "Имя",
            onValueChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationFormPreview() {
    OnlineShopTheme {
        RegistrationForm(
            nameField = "",
            lastNameField = "",
            number = "",
            onValueChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationBodyPreview() {
    OnlineShopTheme {
        RegistrationBody(
            onButtonClick = {},
            enabled = true
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun RegistrationScreenPreview() {
//    OnlineShopTheme {
//        RegistrationScreen()
//    }
//}
