package tenfen.rodolfo.presentation.home

import java.net.URL
import tenfen.rodolfo.domain.character.Character

sealed interface HomeUiState {

    data object Loading : HomeUiState

    data class Content(
        val characters: List<CharacterItemUiState>,
        val detailedCharacterId: Character.Id?,
    ) : HomeUiState

    data class Filter(val name: String, val selectedStatusIndex: Int?) : HomeUiState

    data object Error : HomeUiState
}

data class CharacterItemUiState(val id: Character.Id, val name: String, val imageUrl: URL)
