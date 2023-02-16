package com.example.doctorworkers

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.doctorworkers.ui.navigation.MainNavHost
import com.example.doctorworkers.ui.navigation.Screens
import com.example.doctorworkers.ui.theme.Gray200
import com.example.doctorworkers.ui.theme.MaterialThemeDoctor
import com.example.doctorworkers.viewModel.AuthViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scaffoldState = rememberScaffoldState()

            val viewModel: AuthViewModel = viewModel()
            var startDestination = Screens.Auth.route

            if (viewModel.isAuthorization()) {
                viewModel.signOut()
                startDestination = Screens.Main.route
            }

            val navController = rememberNavController()

            MaterialThemeDoctor() {
                Scaffold(
                    bottomBar = {
                        /*BottomNavigationDoctor(
                            navController = navController
                        )*/
                    },
                    scaffoldState = scaffoldState
                ) {
                    val ui = rememberSystemUiController()
                    ui.setStatusBarColor(Gray200)

                    MainNavHost(
                        navController = navController,
                        startDestination = startDestination,
                        scaffoldState = scaffoldState
                    )

                    ObserverAuthorizationState(
                        viewModel = viewModel,
                        navHostController = navController
                    )
                }
            }
        }
    }
}

@Composable
private fun ObserverAuthorizationState(
    viewModel: AuthViewModel,
    navHostController: NavHostController
) {
    val state = viewModel.typeAuthorization.observeAsState()

    val route = when (state.value) {
        AuthorizationType.AUTHORIZATION -> Screens.Main.route
        AuthorizationType.NOT_AUTHORIZATION -> Screens.Auth.route
        else -> Screens.Splash.route
    }
