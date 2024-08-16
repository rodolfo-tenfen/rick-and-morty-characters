package tenfen.rodolfo.data.character.datasource.remote

import tenfen.rodolfo.domain.character.Character

interface CharacterRemoteDataSource {

    suspend fun getCharacter(id: Character.Id): Character

    suspend fun getCharacters(startingIndex: Int, count: Int): List<Character>

    suspend fun getCharacters(name: String, status: Character.Status?): List<Character>
}
