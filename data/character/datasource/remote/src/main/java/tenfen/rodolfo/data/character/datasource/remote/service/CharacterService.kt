package tenfen.rodolfo.data.character.datasource.remote.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import tenfen.rodolfo.data.character.datasource.remote.dto.CharacterBody
import tenfen.rodolfo.data.character.datasource.remote.dto.ResultBody

internal interface CharacterService {

    @GET("character/{characterId}")
    suspend fun getCharacter(@Path("characterId") characterId: Int): CharacterBody

    @GET("character")
    suspend fun getCharacters(): ResultBody

    @GET("character")
    suspend fun getCharacters(
        @Query("name") name: String,
        @Query("status") status: String,
    ): ResultBody

    companion object {

        const val BASE_URL = "https://rickandmortyapi.com/api/"
    }
}
