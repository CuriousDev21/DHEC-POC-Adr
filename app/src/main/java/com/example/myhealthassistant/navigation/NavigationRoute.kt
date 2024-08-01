package com.example.myhealthassistant.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myhealthassistant.domain.model.ConsentCategory
import com.example.myhealthassistant.presentation.consent.consent.ConsentRoute
import com.example.myhealthassistant.presentation.filemanagement.filemanagement.FileManagementRoute
import com.example.myhealthassistant.presentation.login.login.LoginRoute
import com.example.myhealthassistant.ui.components.GlobalAppBar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthAssistantNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "login"
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val canNavigateBack = backStackEntry?.destination?.route != "login"

    Scaffold(
        topBar = {
            GlobalAppBar(
                title = when (backStackEntry?.destination?.route) {
                    "login" -> "Login"
                    "consent/{userId}/{category}" -> "Consent"
                    "fileManagement/{userId}" -> "File Management"
                    else -> "Health Assistant"
                },
                canNavigateBack = canNavigateBack,
                navigateBack = { navController.popBackStack() },
                onLogout = {
                    // Implement logout logic here
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login") {
                LoginRoute(
                    onLoginSuccess = { user ->
                        navController.navigate("consent/${user.id}/${ConsentCategory.DOCUMENT_UPLOAD}")
                    }
                )
            }
            composable(
                "consent/{userId}/{category}",
                arguments = listOf(
                    navArgument("userId") { type = NavType.StringType },
                    navArgument("category") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val userId = backStackEntry.arguments?.getString("userId") ?: return@composable
                val category = backStackEntry.arguments?.getString("category")?.let {
                    ConsentCategory.valueOf(it)
                } ?: return@composable
                ConsentRoute(
                    userId = userId,
                    category = category,
                    onConsentGranted = {
                        navController.navigate("fileManagement/$userId")
                    },
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable(
                "fileManagement/{userId}",
                arguments = listOf(navArgument("userId") { type = NavType.StringType })
            ) { backStackEntry ->
                val userId = backStackEntry.arguments?.getString("userId") ?: return@composable
                FileManagementRoute(userId = userId)
            }
        }
    }
}