package tenfen.rodolfo.data.character.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import tenfen.rodolfo.data.character.CachedRemoteCharacterRepository
import tenfen.rodolfo.data.character.datasource.memory.di.characterMemoryDataSourceModule
import tenfen.rodolfo.data.character.datasource.remote.di.characterRemoteDataSourceModule
import tenfen.rodolfo.domain.character.CharacterRepository

val dataCharacterModule = module {
    includes(characterMemoryDataSourceModule, characterRemoteDataSourceModule)

    singleOf(::CachedRemoteCharacterRepository) bind CharacterRepository::class
}
