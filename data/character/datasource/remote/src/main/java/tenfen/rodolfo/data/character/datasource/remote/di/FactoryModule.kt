package tenfen.rodolfo.data.character.datasource.remote.di

import org.koin.dsl.module
import tenfen.rodolfo.data.character.datasource.remote.factory.CharacterFactory
import tenfen.rodolfo.data.character.datasource.remote.factory.LocationFactory
import tenfen.rodolfo.data.character.datasource.remote.factory.characterFactory
import tenfen.rodolfo.data.character.datasource.remote.factory.locationFactory

internal val factoryModule = module {
    single<LocationFactory> {
        locationFactory
    }

    single<CharacterFactory> {
        characterFactory
    }
}
