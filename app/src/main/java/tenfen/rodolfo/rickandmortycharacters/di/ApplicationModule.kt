package tenfen.rodolfo.rickandmortycharacters.di

import org.koin.dsl.module
import tenfen.rodolfo.data.character.di.dataCharacterModule
import tenfen.rodolfo.presentation.home.di.presentationHomeModule

val applicationModule = module {
    includes(dataCharacterModule, presentationHomeModule)
}
