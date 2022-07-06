package com.example.doctorworkers.ui.navigation

import androidx.annotation.DrawableRes
import com.example.doctorworkers.R

sealed class Screens(val route: String) {
    object SignIn: Screens("signIn")
    object Registration: Screens("Registration")
    object InputInformation: Screens("Registration")
    object Main: Screens("main")
}

sealed class MainScreen(val route: String, val description: String, @DrawableRes val icon: Int) {
    object Profile: MainScreen("profile", "Описание", R.drawable.ic_home)
    object TimeTable: MainScreen("timetable","Расписание", R.drawable.ic_calendar)
}

sealed class TimeTableScreen(val route: String) {
    object PlacesToWrite: TimeTableScreen("places")
    object DetailPlace: TimeTableScreen("detailPlace")
}
