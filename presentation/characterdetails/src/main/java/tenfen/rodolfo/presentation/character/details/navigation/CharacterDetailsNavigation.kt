package tenfen.rodolfo.presentation.character.details.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import tenfen.rodolfo.domain.character.Character
import tenfen.rodolfo.presentation.character.details.CharacterDetailsScreen

@Serializable
data class CharacterDetailsRoute(val characterId: Int)

fun NavGraphBuilder.characterDetailsRoute(navController: NavHostController) {
    composable<CharacterDetailsRoute> { entry ->
        CharacterDetailsRoute(
            characterId = entry.toRoute<CharacterDetailsRoute>().characterId,
            onNavigateUp = navController::navigateUp,
        )
    }
}

fun NavHostController.navigateToCharacterDetails(id: Character.Id) {
    navigate(CharacterDetailsRoute(id.value))
}

@Composable
internal fun CharacterDetailsRoute(characterId: Int, onNavigateUp: () -> Unit) {
    CharacterDetailsScreen(Character.Id(characterId), onNavigateUp)
}
