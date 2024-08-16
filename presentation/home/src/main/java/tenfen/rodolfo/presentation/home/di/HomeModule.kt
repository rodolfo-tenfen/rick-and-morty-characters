package tenfen.rodolfo.presentation.home.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import tenfen.rodolfo.presentation.character.details.di.presentationCharacterDetailsModule
import tenfen.rodolfo.presentation.home.HomeViewModel

val presentationHomeModule = module {
    includes(presentationCharacterDetailsModule)

    viewModelOf(::HomeViewModel)
}
