package tenfen.rodolfo.presentation.character.details.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tenfen.rodolfo.domain.character.Character
import tenfen.rodolfo.presentation.character.details.CharacterDetailsViewModel

val presentationCharacterDetailsModule = module {
    viewModel { (characterId: Character.Id) ->
        CharacterDetailsViewModel(characterId, get())
    }
}
