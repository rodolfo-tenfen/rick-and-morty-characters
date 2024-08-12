package tenfen.rodolfo.data.character.datasource.memory.di

import org.koin.dsl.module
import tenfen.rodolfo.data.character.datasource.memory.CharacterMapMemoryDataSource
import tenfen.rodolfo.data.character.datasource.memory.CharacterMemoryDataSource

val characterMemoryDataSourceModule = module {
    single<CharacterMemoryDataSource> {
        CharacterMapMemoryDataSource
    }
}
