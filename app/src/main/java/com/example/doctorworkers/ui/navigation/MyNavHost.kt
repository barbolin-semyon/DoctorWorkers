package com.example.doctorworkers.ui.navigation

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.doctors.ui.views.auth.Registration
import com.example.doctors.ui.views.auth.SignInView
import com.example.doctorworkers.ui.features.patientCard.UserInfoScreen
import com.example.doctorworkers.ui.features.placeToWrite.PlaceToWriteView
import com.example.doctorworkers.viewModel.AuthViewModel
import com.google.firebase.auth.UserInfo

@Composable
fun MainNavHost(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    authViewModel: AuthViewModel,
) {
    NavHost(navController = navController, startDestination = Screens.Splash.route) {
        auth(navController, scaffoldState, authViewModel = authViewModel)
        main(navController)

        composable(Screens.Splash.route) {

        }
    }
}

private fun NavGraphBuilder.auth(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    authViewModel: AuthViewModel
) {
    navigation(startDestination = AuthScreen.SignIn.route, route = Screens.Auth.route) {
        composable(AuthScreen.SignIn.route) {
            SignInView(
                navController = navController,
                scaffoldState = scaffoldState,
                authViewModel = authViewModel
            )
        }

        composable(AuthScreen.Registration.route) {
            Registration(
                navController = navController,
                scaffoldState = scaffoldState,
                authViewModel = authViewModel
            )
        }
    }
}

private fun NavGraphBuilder.main(navController: NavHostController) {
    navigation(startDestination = MainScreen.Profile.route, route = Screens.Main.route) {
        composable(MainScreen.Profile.route) {

        }
        timetable(navController)
    }
}

private fun NavGraphBuilder.timetable(navController: NavHostController) {
    navigation(
        startDestination = TimeTableScreen.PlacesToWrite.route,
        route = MainScreen.TimeTable.route
    ) {
        composable(TimeTableScreen.PlacesToWrite.route) {
            PlaceToWriteView(navController = navController)
        }

        composable(TimeTableScreen.DetailPlace.route) {
            val currentPatientId =
                navController.previousBackStackEntry?.arguments?.getString("patientId")

            currentPatientId?.let {
                UserInfoScreen(navController, it)
            }
        }
    }
}