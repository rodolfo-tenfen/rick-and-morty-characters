package tenfen.rodolfo.presentation.character.details

import java.net.URL
import tenfen.rodolfo.domain.character.Character

internal sealed class CharacterDetailsUiState {

    data object Loading : CharacterDetailsUiState()

    data class Content(
        val name: String,
        val imageUrl: URL,
        val status: Character.Status,
        val species: Character.Species,
        val type: String,
        val gender: Character.Gender,
        val origin: Character.Location,
        val currentLocation: Character.Location,
    ) : CharacterDetailsUiState()

    data object Error : CharacterDetailsUiState()
}
