package tenfen.rodolfo.presentation.character.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tenfen.rodolfo.domain.character.Character
import tenfen.rodolfo.domain.character.CharacterRepository
import tenfen.rodolfo.presentation.character.details.CharacterDetailsUiState.Content as ContentState
import tenfen.rodolfo.presentation.character.details.CharacterDetailsUiState.Error as ErrorState
import tenfen.rodolfo.presentation.character.details.CharacterDetailsUiState.Loading as LoadingState

internal class CharacterDetailsViewModel(
    private val characterId: Character.Id,
    private val characterRepository: CharacterRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<CharacterDetailsUiState>(LoadingState)
    val uiState = _uiState.asStateFlow()

    fun load() {
        _uiState.value = LoadingState

        viewModelScope.launch {
            _uiState.value =
                characterRepository.getCharacter(characterId)?.toUiState() ?: ErrorState
        }
    }

    fun retry() = load()

    private fun Character.toUiState() =
        ContentState(
            name = name,
            imageUrl = imageUri.toURL(),
            status = status,
            species = species,
            type = type,
            gender = gender,
            origin = origin,
            currentLocation = currentLocation,
        )
}
