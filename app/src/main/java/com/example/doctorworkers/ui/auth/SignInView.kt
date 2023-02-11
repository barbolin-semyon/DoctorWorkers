package com.example.doctors.ui.views.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.doctors.ui.components.*
import com.example.doctors.util.emailIfValid
import com.example.doctorworkers.ui.components.BackgroundAuthorization
import com.example.doctorworkers.ui.navigation.AuthScreen
import com.example.doctorworkers.viewModel.AuthViewModel

@Composable
fun SignInView(navController: NavController, scaffoldState: ScaffoldState) {
    val viewModel: AuthViewModel = viewModel()

    BackgroundAuthorization(sizeBackgroundImage = 410.dp) {
        Column {
            TitleAuth(text = "Авторизация")

            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            TextFieldEmail(email = email, onValueChange = { email = it })
            TextFieldPassword(password = password, onValueChange = { password = it })

            AppButton(isEnabled = email.emailIfValid(), text = "Войти") {
                viewModel.signInWithEmail(
                    email = email,
                    password = password
                )
            }

            AppTextButton(text = "Зарегистрироваться") {
                navController.navigate(AuthScreen.Registration.route)
            }

            ObserverRequestsToFirebase(
                viewModel = viewModel,
                navController = navController,
                scaffoldState = scaffoldState
            )
        }
    }
}




