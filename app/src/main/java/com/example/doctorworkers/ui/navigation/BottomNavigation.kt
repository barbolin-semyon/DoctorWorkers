package com.example.doctorworkers.ui.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationDoctorWorker(navController: NavController) {
    val backStackEntryState = navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntryState.value?.destination

    val screens = listOf(MainScreen.Profile, MainScreen.TimeTable)

    currentDestination?.let { destination ->
        if (destination.hierarchy.any { it.route != Screens.Auth.route }) {
            BottomNavigation {
                screens.forEach { currentScreen ->
                    val labelString = currentScreen.description

                    BottomNavigationItem(
                        selected = destination.hierarchy.any { it.route == currentScreen.route },
                        onClick = {
                            navController.navigate(currentScreen.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = { Text(labelString) },
                        icon = {
                            Icon(
                                painter = painterResource(id = currentScreen.icon),
                                contentDescription = currentScreen.description
                            )
                        }
                    )
                }
            }
        }
    }
}