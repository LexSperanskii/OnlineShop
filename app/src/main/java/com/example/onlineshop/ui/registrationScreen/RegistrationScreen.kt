package com.example.onlineshop.ui.registrationScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.onlineshop.R
import com.example.onlineshop.ui.OnlineShopTopAppBar
import com.example.onlineshop.ui.theme.OnlineShopTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    registrationScreenVIewModel: RegistrationScreenVIewModel = viewModel()
) {
    Scaffold(
        topBar = {
            OnlineShopTopAppBar(
                title = "Вход",
            )
        }
    ) { innerPadding ->
        RegistrationBody(
            nameField = "",
            lastNameField = "",
            number = "",
            onNameFieldValueChange = {},
            onLastNameFieldValueChange = {},
            onNumberValueChange = {},
            onButtonClick = {},
            enabled = false,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
fun RegistrationBody(
    nameField : String,
    lastNameField : String,
    number : String,
    onNameFieldValueChange: (String)->Unit,
    onLastNameFieldValueChange: (String)->Unit,
    onNumberValueChange: (String)->Unit,
    onButtonClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(
                top = 113.dp,
                bottom = 11.dp
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ){
            RegistrationForm(
                nameField = nameField,
                lastNameField = lastNameField,
                number = number,
                onNameFieldValueChange = onNameFieldValueChange,
                onLastNameFieldValueChange = onLastNameFieldValueChange,
                onNumberValueChange = onNumberValueChange
            )
            Column(
                modifier = Modifier
                    .padding(
                        top = dimensionResource(id = R.dimen.padding_extra_large),
                        start = dimensionResource(id = R.dimen.padding_medium),
                        end = dimensionResource(id = R.dimen.padding_medium)
                    )
            ) {
                Button(
                    onClick = onButtonClick,
                    enabled = enabled,
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD62F89),
                        disabledContainerColor = Color(0xFFFF8AC9)
                    ), // Задаем цвет фона кнопки
                    modifier = Modifier
                        .height(51.dp)
                        .width(343.dp)

                ) {
                    Text(
                        text = "Войти",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color(0xFFFFFFFF),
                        )
                    )
                }
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = "Нажимая кнопку “Войти”, Вы принимаете",
                style = TextStyle(
                    fontSize = 10.sp,
                    lineHeight = 11.sp,
                    color = Color(0xFFA0A1A3),
                ),
                modifier = Modifier
                    .height(13.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "условия программы лояльности",
                style = TextStyle(
                    fontSize = 10.sp,
                    lineHeight = 11.sp,
                    color = Color(0xFFA0A1A3),
                ),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .height(13.dp)
            )
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
    Column(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = dimensionResource(id = R.dimen.padding_medium),
                    start = dimensionResource(id = R.dimen.padding_medium),
                    end = dimensionResource(id = R.dimen.padding_medium)
                )
        ) {
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
                modifier = Modifier
                    .height(50.dp)
                    .width(343.dp)
            )
        }
    }
}

@Composable
fun RegistrationForm(
    nameField : String,
    lastNameField : String,
    number : String,
    onNameFieldValueChange: (String)->Unit,
    onLastNameFieldValueChange: (String)->Unit,
    onNumberValueChange: (String)->Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        RegistrationField(
            text = nameField,
            placeholder = "Имя",
            onValueChange = onNameFieldValueChange
        )
        RegistrationField(
            text = lastNameField,
            placeholder = "Фамилия",
            onValueChange = onLastNameFieldValueChange
        )
        RegistrationField(
            text = number,
            placeholder = "Номер телефона",
            onValueChange = onNumberValueChange
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
            onNameFieldValueChange = {},
            onLastNameFieldValueChange = {},
            onNumberValueChange = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationBodyPreview() {
    OnlineShopTheme {
        RegistrationBody(
            nameField = "",
            lastNameField = "",
            number = "",
            onNameFieldValueChange = {},
            onLastNameFieldValueChange = {},
            onNumberValueChange = {},
            onButtonClick = {},
            enabled = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    OnlineShopTheme {
        RegistrationScreen()
    }
}
