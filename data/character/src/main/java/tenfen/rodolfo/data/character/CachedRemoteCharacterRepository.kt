package tenfen.rodolfo.data.character

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tenfen.rodolfo.data.character.datasource.memory.CharacterMemoryDataSource
import tenfen.rodolfo.data.character.datasource.remote.CharacterRemoteDataSource
import tenfen.rodolfo.domain.character.Character
import tenfen.rodolfo.domain.character.CharacterRepository

internal class CachedRemoteCharacterRepository(
    private val remoteCharacterDataSource: CharacterRemoteDataSource,
    private val memoryCharacterDataSource: CharacterMemoryDataSource,
) : CharacterRepository {

    override suspend fun getCharacter(id: Character.Id) =
        memoryCharacterDataSource[id]
            ?: remoteCharacterDataSource.getCharacter(id).also { save(it) }

    override suspend fun getCharacters(startingIndex: Int, count: Int) =
        memoryCharacterDataSource
            .characters
            .takeIf(List<*>::isNotEmpty)
            ?.subList(fromIndex = startingIndex, toIndex = startingIndex + count)
            ?: remoteCharacterDataSource.getCharacters(startingIndex, count).also { save(it) }

    override suspend fun getCharacters(name: String, status: Character.Status?) =
        remoteCharacterDataSource.getCharacters(name, status).also { save(it) }

    override suspend fun clear() = save(emptyList())

    private suspend fun save(character: Character) = withContext(Dispatchers.Default) {
        memoryCharacterDataSource[character.id] = character
    }

    private suspend fun save(characters: List<Character>) = withContext(Dispatchers.Default) {
        memoryCharacterDataSource.characters = characters
    }
}
