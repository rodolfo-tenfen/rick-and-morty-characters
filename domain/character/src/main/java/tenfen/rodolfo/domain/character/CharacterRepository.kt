package tenfen.rodolfo.domain.character

interface CharacterRepository {

    suspend fun getCharacter(id: Character.Id): Character?

    suspend fun getCharacters(startingIndex: Int, count: Int): List<Character>

    suspend fun getCharacters(name: String, status: Character.Status?): List<Character>

    suspend fun clear()
}
