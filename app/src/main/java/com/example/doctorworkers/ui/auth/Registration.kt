package com.example.doctors.ui.views.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.doctors.ui.components.*
import com.example.doctors.util.emailIfValid
import com.example.doctorworkers.ui.components.BackgroundAuthorization
import com.example.doctorworkers.viewModel.AuthViewModel

@Composable
fun Registration(navController: NavController, scaffoldState: ScaffoldState) {
    BackgroundAuthorization(sizeBackgroundImage = 0.dp) {
        Column {
            TitleAuth("Регистрация")

            val viewModel: AuthViewModel = viewModel()

            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var repeatPassword by remember { mutableStateOf("") }
            var name by remember { mutableStateOf("") }
            var code by remember { mutableStateOf("") }
            var avaragePrice by remember { mutableStateOf(0) }
            var avaragePriceIsInt by remember { mutableStateOf(true) }

            TextFieldEmail(email = email, onValueChange = { email = it })

            TextFieldsWithLabelError(
                value = name,
                onValueChange = { name = it },
                labelText = "Введите имя и фамилию",
                errorText = "Должно быть 2 слова",
                isError = name.split(" ").size != 2 && name.isNotEmpty()
            )

            TextFieldPassword(password = password, onValueChange = { password = it })

            TextFieldsWithLabelError(
                value = repeatPassword,
                onValueChange = { text -> repeatPassword = text },
                labelText = "Повторите пароль",
                visualTransformation = PasswordVisualTransformation(),
                isError = password != repeatPassword,
                errorText = "Пароли не совпадают"
            )

            TextFieldsWithLabelError(
                value = avaragePrice.toString(),
                isError = avaragePriceIsInt.not(),
                errorText = "Значение не является числом",
                labelText = "Введите ваш средний ценник",
                onValueChange = {
                    avaragePriceIsInt = try {
                        avaragePrice = it.toInt()
                        true
                    } catch (e: NumberFormatException) {
                        false
                    }
                },
            )

            TextFieldsWithLabelError(
                value = code,
                labelText = "Введите код",
                onValueChange = { code = it }
            )

            AppButton(
                isEnabled = password == repeatPassword
                        && email.emailIfValid()
                        && name.split(" ").size == 2
                        && avaragePriceIsInt,
                text = "Зарегистрироваться"
            ) {
                viewModel.registration(
                    email = email,
                    password = password,
                    name = name,
                    avaragePrice = avaragePrice,
                    enteredPasswordWork = code
                )
            }

            ObserverRequestsToFirebase(
                viewModel = viewModel,
                navController = navController,
                scaffoldState = scaffoldState
            )
        }
    }
}

