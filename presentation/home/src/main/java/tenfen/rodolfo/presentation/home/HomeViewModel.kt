package tenfen.rodolfo.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tenfen.rodolfo.domain.character.Character
import tenfen.rodolfo.domain.character.CharacterRepository
import tenfen.rodolfo.presentation.home.HomeUiState.Content as ContentState
import tenfen.rodolfo.presentation.home.HomeUiState.Error as ErrorState
import tenfen.rodolfo.presentation.home.HomeUiState.Filter as FilterState
import tenfen.rodolfo.presentation.home.HomeUiState.Loading as LoadingState

class HomeViewModel(private val characterRepository: CharacterRepository) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        Log.d("[HomeViewModel]", "error: $e")

        _uiState.value = ErrorState
    }

    private val _uiState = MutableStateFlow<HomeUiState>(LoadingState)
    val uiState = _uiState.asStateFlow()

    private var filterState: FilterState? = null

    fun load(startingIndex: Int, count: Int) {
        _uiState.value = LoadingState

        viewModelScope.launch(exceptionHandler) {
            _uiState.value =
                characterRepository
                    .getCharacters(startingIndex, count)
                    .takeIf { it.isNotEmpty() }
                    ?.toUiState()
                    ?: ErrorState
        }
    }

    fun showFilter() {
        _uiState.value = filterState ?: FilterState("", null)
    }

    fun applyFilter() {
        _uiState.value
            .let { it as? FilterState ?: return }
            .let { state ->
                filterState = state

                _uiState.value = LoadingState

                viewModelScope.launch(exceptionHandler) {
                    characterRepository.clear()

                    characterRepository
                        .getCharacters(
                            name = state.name,
                            status = state.selectedStatusIndex
                                ?.let { Character.Status.entries[it] },
                        )
                        .toUiState()
                        .let { _uiState.value = it }
                }
            }
    }

    fun clearFilter() {
        filterState = null
        viewModelScope.launch(exceptionHandler) {
            characterRepository.clear()

            load(startingIndex = 0, count = 20)
        }
    }

    fun hideFilter() = load(startingIndex = 0, count = 20)

    fun onNameFilterChanged(name: String) = _uiState.update {
        if (it is FilterState) it.copy(name = name) else it
    }

    fun onStatusFilterSelected(index: Int) = _uiState.update {
        if (it is FilterState) it.copy(selectedStatusIndex = index) else it
    }

    fun onItemClick(item: CharacterItemUiState) = _uiState.update {
        if (it is ContentState) it.copy(detailedCharacterId = item.id) else it
    }

    fun retry() = load(startingIndex = 0, count = 20)

    fun onCharacterDetailsShown() = _uiState.update {
        if (it is ContentState) it.copy(detailedCharacterId = null) else it
    }

    private fun List<Character>.toUiState() =
        ContentState(
            characters = map { CharacterItemUiState(it.id, it.name, it.imageUri.toURL()) },
            detailedCharacterId = null,
        )
}
