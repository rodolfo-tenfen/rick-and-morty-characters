package tenfen.rodolfo.data.character.datasource.remote.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import tenfen.rodolfo.data.character.datasource.remote.CharacterRemoteDataSource
import tenfen.rodolfo.data.character.datasource.remote.CharacterRetrofitDataSource

val characterRemoteDataSourceModule = module {
    includes(serviceModule, factoryModule)

    singleOf(::CharacterRetrofitDataSource) bind CharacterRemoteDataSource::class
}
