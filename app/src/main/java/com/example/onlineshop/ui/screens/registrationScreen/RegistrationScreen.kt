package com.example.onlineshop.ui.screens.registrationScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.onlineshop.R
import com.example.onlineshop.ui.AppViewModelProvider
import com.example.onlineshop.ui.navigation.RegistrationDestination
import com.example.onlineshop.ui.screens.TopAppBarNameOnly
import com.example.onlineshop.ui.theme.OnlineShopTheme

@Composable
fun RegistrationScreen(
    title: Int,
    navigateToGeneral : () -> Unit,
    navigateToCatalog : () -> Unit,
    registrationScreenVIewModel: RegistrationScreenVIewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val registrationUiState = registrationScreenVIewModel.uiState.collectAsState().value

    //Для Форматирования строки вывода Номера телефона и правильного расположения курсора
    val phoneNumber: String = registrationUiState.number
    val textFieldValueState = when (phoneNumber.length){
        2 -> {
            TextFieldValue(
                text = "${phoneNumber[0]}${phoneNumber[1]} xxx xxx-xx-xx",
                selection = TextRange(2)
            )
        }

        3 -> {
            TextFieldValue(
                text = "${phoneNumber[0]}${phoneNumber[1]} ${phoneNumber[2]}xx xxx-xx-xx",
                selection = TextRange(4)
            )
        }

        4 -> {
            TextFieldValue(
                text = "${phoneNumber[0]}${phoneNumber[1]} ${phoneNumber[2]}${phoneNumber[3]}x xxx-xx-xx",
                selection = TextRange(5)
            )
        }

        5 -> {
            TextFieldValue(
                text = "${phoneNumber[0]}${phoneNumber[1]} ${phoneNumber[2]}${phoneNumber[3]}${phoneNumber[4]} xxx-xx-xx",
                selection = TextRange(6)
            )
        }

        6 -> {
            TextFieldValue(
                text = "${phoneNumber[0]}${phoneNumber[1]} ${phoneNumber[2]}${phoneNumber[3]}${phoneNumber[4]} ${phoneNumber[5]}xx-xx-xx",
                selection = TextRange(8)
            )
        }

        7 -> {
            TextFieldValue(
                text =  "${phoneNumber[0]}${phoneNumber[1]} ${phoneNumber[2]}${phoneNumber[3]}${phoneNumber[4]} ${phoneNumber[5]}${phoneNumber[6]}x-xx-xx",
                selection = TextRange(9)
            )
        }

        8 -> {
            TextFieldValue(
                text =  "${phoneNumber[0]}${phoneNumber[1]} ${phoneNumber[2]}${phoneNumber[3]}${phoneNumber[4]} ${phoneNumber[5]}${phoneNumber[6]}${phoneNumber[7]}-xx-xx",
                selection = TextRange(10)
            )
        }

        9 -> {
            TextFieldValue(
                text = "${phoneNumber[0]}${phoneNumber[1]} ${phoneNumber[2]}${phoneNumber[3]}${phoneNumber[4]} ${phoneNumber[5]}${phoneNumber[6]}${phoneNumber[7]}-${phoneNumber[8]}x-xx",
                selection = TextRange(12)
            )
        }

        10 -> {
            TextFieldValue(
                text = "${phoneNumber[0]}${phoneNumber[1]} ${phoneNumber[2]}${phoneNumber[3]}${phoneNumber[4]} ${phoneNumber[5]}${phoneNumber[6]}${phoneNumber[7]}-${phoneNumber[8]}${phoneNumber[9]}-xx",
                selection = TextRange(13)
            )
        }

        11 -> {
            TextFieldValue(
                text = "${phoneNumber[0]}${phoneNumber[1]} ${phoneNumber[2]}${phoneNumber[3]}${phoneNumber[4]} ${phoneNumber[5]}${phoneNumber[6]}${phoneNumber[7]}-${phoneNumber[8]}${phoneNumber[9]}-${phoneNumber[10]}x",
                selection = TextRange(15)
            )
        }

        12 -> {
            TextFieldValue(
                text = "${phoneNumber[0]}${phoneNumber[1]} ${phoneNumber[2]}${phoneNumber[3]}${phoneNumber[4]} ${phoneNumber[5]}${phoneNumber[6]}${phoneNumber[7]}-${phoneNumber[8]}${phoneNumber[9]}-${phoneNumber[10]}${phoneNumber[11]}",
                selection = TextRange(16)
            )
        }
        else -> {
            TextFieldValue(
                text = phoneNumber,
                selection = TextRange(0)
            )
        }
    }

    //для того чтобы сразу переходить на экран Каталог если в БД была запись аккаунта
    LaunchedEffect(registrationUiState.id) {
        if (registrationUiState.name.isNotBlank() && registrationUiState.lastName.isNotBlank() && registrationUiState.number.isNotBlank()) {
            navigateToCatalog()
        }
    }
    Scaffold(
        topBar = {
            TopAppBarNameOnly(
                currentDestinationTitle = stringResource(title),
            )
        }
    ) { innerPadding ->
        RegistrationBody(
            nameField = registrationUiState.name,
            isErrorName = registrationUiState.nameIsError,
            onNameFieldValueChange = {
                registrationScreenVIewModel.updateNameField(it)
            },
            onEraseNameClick = {
                registrationScreenVIewModel.onEraseNameClick()
            },
            lastNameField = registrationUiState.lastName,
            isErrorLastName = registrationUiState.lastNameIsError,
            onLastNameFieldValueChange = {
                registrationScreenVIewModel.updateLastNameField(it)
            },
            onEraseLastNameClick = {
                registrationScreenVIewModel.onEraseLastNameClick()
            },
            number = textFieldValueState,
            isErrorNumber = registrationUiState.numberIsError,
            onNumberValueChange = {
                registrationScreenVIewModel.updateNumberField(it)
            },
            onEraseNumberClick = {
                registrationScreenVIewModel.onEraseNumberClick()
            },
            onNumberFieldClick = {}, //На тот случай, если действительно по наведению на поле отображать маску
            onButtonClick = {
                registrationScreenVIewModel.saveUser()
                navigateToGeneral()
            },
            enabled = !registrationUiState.nameIsError &&
                    !registrationUiState.lastNameIsError &&
                    !registrationUiState.numberIsError &&
                    registrationUiState.name.isNotEmpty() &&
                    registrationUiState.lastName.isNotEmpty() &&
                    registrationUiState.number.isNotEmpty(),
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
    number : TextFieldValue,
    onNameFieldValueChange: (String)->Unit,
    onLastNameFieldValueChange: (String)->Unit,
    onNumberValueChange: (String)->Unit,
    onButtonClick: () -> Unit,
    enabled: Boolean,
    isErrorNumber: Boolean,
    isErrorName: Boolean,
    isErrorLastName: Boolean,
    onNumberFieldClick : ()->Unit ,
    onEraseNameClick : () -> Unit,
    onEraseLastNameClick : () -> Unit,
    onEraseNumberClick : () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(
                top = dimensionResource(R.dimen.size_113),
                bottom = dimensionResource(R.dimen.size_11)
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
                onNumberValueChange = onNumberValueChange,
                onNumberFieldClick = onNumberFieldClick,
                isErrorNumber = isErrorNumber,
                isErrorName = isErrorName,
                isErrorLastName = isErrorLastName,
                onEraseNameClick = onEraseNameClick,
                onEraseLastNameClick = onEraseLastNameClick,
                onEraseNumberClick = onEraseNumberClick,
            )
            Column(
                modifier = Modifier
                    .padding(
                        top = dimensionResource(id = R.dimen.size_32),
                        start = dimensionResource(id = R.dimen.size_16),
                        end = dimensionResource(id = R.dimen.size_16)
                    )
            ) {
                Button(
                    onClick = onButtonClick,
                    enabled = enabled,
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.pink),
                        disabledContainerColor = colorResource(id = R.color.light_pink)
                    ), // Задаем цвет фона кнопки
                    modifier = Modifier
                        .height(dimensionResource(R.dimen.size_51))
                        .width(dimensionResource(R.dimen.size_343))

                ) {
                    Text(
                        text = stringResource(R.string.enter),
                        style = TextStyle(
                            fontSize = dimensionResource(id = R.dimen.text_size_14).value.sp,
                            color = colorResource(id = R.color.white),
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
                text = stringResource(R.string.commercial_conditions_one),
                style = TextStyle(
                    fontSize = dimensionResource(id = R.dimen.text_size_10).value.sp,
                    lineHeight = dimensionResource(R.dimen.size_11).value.sp,
                    color = colorResource(id = R.color.light_grey),
                ),
                modifier = Modifier
                    .height(dimensionResource(R.dimen.size_13))
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_2)))
            Text(
                text = stringResource(R.string.commercial_conditions_two),
                style = TextStyle(
                    fontSize = dimensionResource(id = R.dimen.text_size_10).value.sp,
                    lineHeight = dimensionResource(R.dimen.size_11).value.sp,
                    color = colorResource(id = R.color.light_grey),
                ),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .height(dimensionResource(R.dimen.size_13))
            )
        }
    }

}
@Composable
fun RegistrationField(
    text : String,
    placeholder: String,
    onValueChange: (String)->Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onNumberFieldClick : ()->Unit = {},
    isError: Boolean,
    onEraseItemClick : () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = dimensionResource(id = R.dimen.size_16),
                    start = dimensionResource(id = R.dimen.size_16),
                    end = dimensionResource(id = R.dimen.size_16)
                )
        ) {
            TextField(
                value = text,
                textStyle = TextStyle(
                    fontSize = dimensionResource(id = R.dimen.text_size_16).value.sp,
                    color = colorResource(id = R.color.black),
                ),
                onValueChange = { onValueChange(it) },
                singleLine = true,
                trailingIcon = {
                    if (text.isNotEmpty()) {
                        IconButton(
                            onClick = onEraseItemClick,
                        ) {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = stringResource(id = R.string.delete),
                                modifier = modifier
                                    .height(dimensionResource(R.dimen.size_13))
                                    .width(dimensionResource(R.dimen.size_13))
                            )
                        }
                    }
                },
                placeholder = {
                    Text(
                        text = placeholder,
                        style = TextStyle(
                            fontSize = dimensionResource(id = R.dimen.text_size_16).value.sp,
                            color = colorResource(id = R.color.light_grey),
                        )
                    )
                },
                shape = MaterialTheme.shapes.small,
                colors = TextFieldDefaults.colors(
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                keyboardOptions = keyboardOptions,
                interactionSource = remember { MutableInteractionSource() }
                    .also { interactionSource ->
                        LaunchedEffect(interactionSource) {
                            interactionSource.interactions.collect {
                                if (it is PressInteraction.Release) {
                                    onNumberFieldClick()
                                }
                            }
                        }
                    },
                modifier = Modifier
                    .height(dimensionResource(R.dimen.size_50))
                    .width(dimensionResource(R.dimen.size_343))
                    .border(
                        width = if (isError) dimensionResource(R.dimen.size_2) else dimensionResource(
                            R.dimen.size_0
                        ),
                        color = if (isError) Color.Red else Color.Transparent,
                        shape = MaterialTheme.shapes.small
                    )
            )
        }
    }
}
@Composable
fun NumberRegistrationField(
    text: TextFieldValue,
    placeholder: String,
    onValueChange: (String)->Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onNumberFieldClick : ()->Unit = {},
    isError: Boolean,
    onEraseItemClick : () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = dimensionResource(id = R.dimen.size_16),
                    start = dimensionResource(id = R.dimen.size_16),
                    end = dimensionResource(id = R.dimen.size_16)
                )
        ) {
            TextField(
                value = text,
                textStyle = TextStyle(
                    fontSize = dimensionResource(id = R.dimen.text_size_16).value.sp,
                    color = colorResource(id = R.color.black),
                ),
                onValueChange = { onValueChange(it.text) },
                singleLine = true,
                trailingIcon = {
                    if (text.text.isNotEmpty()) {
                        IconButton(
                            onClick = onEraseItemClick,
                        ) {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = stringResource(id = R.string.delete),
                                modifier = modifier
                                    .height(dimensionResource(R.dimen.size_13))
                                    .width(dimensionResource(R.dimen.size_13))
                            )
                        }
                    }
                },
                placeholder = {
                    Text(
                        text = placeholder,
                        style = TextStyle(
                            fontSize = dimensionResource(id = R.dimen.text_size_16).value.sp,
                            color = colorResource(id = R.color.light_grey),
                        )
                    )
                },
                shape = MaterialTheme.shapes.small,
                colors = TextFieldDefaults.colors(
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                keyboardOptions = keyboardOptions,
                interactionSource = remember { MutableInteractionSource() }
                    .also { interactionSource ->
                        LaunchedEffect(interactionSource) {
                            interactionSource.interactions.collect {
                                if (it is PressInteraction.Release) {
                                    onNumberFieldClick()
                                }
                            }
                        }
                    },
                modifier = Modifier
                    .height(dimensionResource(R.dimen.size_50))
                    .width(dimensionResource(R.dimen.size_343))
                    .border(
                        width = if (isError) dimensionResource(R.dimen.size_2) else dimensionResource(R.dimen.size_0),
                        color = if (isError) Color.Red else Color.Transparent,
                        shape = MaterialTheme.shapes.small
                    )
            )
        }
    }
}

@Composable
fun RegistrationForm(
    nameField : String,
    lastNameField : String,
    number : TextFieldValue,
    onNameFieldValueChange: (String)->Unit,
    onLastNameFieldValueChange: (String)->Unit,
    onNumberValueChange: (String)->Unit,
    onNumberFieldClick : ()->Unit,
    isErrorNumber: Boolean,
    isErrorName: Boolean,
    isErrorLastName: Boolean,
    onEraseNameClick : () -> Unit,
    onEraseLastNameClick : () -> Unit,
    onEraseNumberClick : () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        RegistrationField(
            text = nameField,
            placeholder = stringResource(R.string.name),
            onValueChange = onNameFieldValueChange,
            isError = isErrorName,
            onEraseItemClick = onEraseNameClick
        )
        RegistrationField(
            text = lastNameField,
            placeholder = stringResource(R.string.surname),
            onValueChange = onLastNameFieldValueChange,
            isError = isErrorLastName,
            onEraseItemClick = onEraseLastNameClick
        )
        NumberRegistrationField(
            text = number,
            placeholder = stringResource(R.string.phone_number),
            onValueChange = onNumberValueChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onNumberFieldClick = onNumberFieldClick,
            isError = isErrorNumber,
            onEraseItemClick = onEraseNumberClick
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
            onValueChange = {},
            isError = false,
            onEraseItemClick = {}
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
            number = TextFieldValue(),
            onNameFieldValueChange = {},
            onLastNameFieldValueChange = {},
            onNumberValueChange = {},
            onNumberFieldClick = {},
            isErrorNumber = false,
            isErrorName = false,
            isErrorLastName = false,
            onEraseNameClick = {},
            onEraseLastNameClick = {},
            onEraseNumberClick = {},
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
            number = TextFieldValue(),
            onNameFieldValueChange = {},
            onLastNameFieldValueChange = {},
            onNumberValueChange = {},
            onButtonClick = {},
            enabled = true,
            onNumberFieldClick = {},
            isErrorNumber = false,
            isErrorName = false,
            isErrorLastName = false,
            onEraseNameClick = {},
            onEraseLastNameClick = {},
            onEraseNumberClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    OnlineShopTheme {
        RegistrationScreen(
            title = RegistrationDestination.title,
            navigateToGeneral = {},
            navigateToCatalog = {},
        )
    }
}
