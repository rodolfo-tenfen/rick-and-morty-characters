package tenfen.rodolfo.data.character.datasource.remote.di

import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import tenfen.rodolfo.data.character.datasource.remote.service.CharacterService
import tenfen.rodolfo.data.character.datasource.remote.service.CharacterService.Companion.BASE_URL

internal val serviceModule = module {
    single<MediaType> {
        "application/json; charset=UTF8".toMediaType()
    }

    single<Json> {
        Json {
            ignoreUnknownKeys = true
        }
    }

    factory<Converter.Factory> {
        get<Json>().asConverterFactory(get<MediaType>())
    }

    factory<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(get<Converter.Factory>())
            .build()
    }

    single<CharacterService> {
        get<Retrofit>().create(CharacterService::class.java)
    }
}
