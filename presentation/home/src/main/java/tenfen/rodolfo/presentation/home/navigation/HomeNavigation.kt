package tenfen.rodolfo.presentation.home.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import tenfen.rodolfo.presentation.character.details.navigation.characterDetailsRoute
import tenfen.rodolfo.presentation.character.details.navigation.navigateToCharacterDetails
import tenfen.rodolfo.presentation.home.HomeScreen

@Serializable
object HomeRoute

fun NavGraphBuilder.homeRoute() {
    composable<HomeRoute> {
        HomeRoute()
    }
}

internal fun NavGraphBuilder.homeContentRoute(navController: NavHostController) {
    composable<HomeRoute> {
        HomeScreen(onShowCharacterDetails = navController::navigateToCharacterDetails)
    }
}

@Composable
internal fun HomeRoute() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = HomeRoute) {
        homeContentRoute(navController)

        characterDetailsRoute(navController)
    }
}
