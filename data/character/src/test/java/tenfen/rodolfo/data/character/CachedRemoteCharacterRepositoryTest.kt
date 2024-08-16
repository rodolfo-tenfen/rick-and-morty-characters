package tenfen.rodolfo.data.character

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import tenfen.rodolfo.data.character.datasource.memory.CharacterMemoryDataSource
import tenfen.rodolfo.data.character.datasource.remote.CharacterRemoteDataSource
import tenfen.rodolfo.domain.character.Character

class CachedRemoteCharacterRepositoryTest {

    private var characterMock: Character = mockk()
    private var remoteDataSourceMock: CharacterRemoteDataSource = mockk {
        coEvery { getCharacters(any<Int>(), any<Int>()) } returns listOf(characterMock)
    }
    private var memoryDataSourceMockk: CharacterMemoryDataSource = mockk {
        coEvery { characters } returns emptyList()
        coEvery { characters = any() } returns Unit
    }

    private val repository by lazy {
        CachedRemoteCharacterRepository(remoteDataSourceMock, memoryDataSourceMockk)
    }

    @Test
    fun `when the repository loads characters, then it saves the result to the memory data source`() =
        runTest {
            repository.getCharacters(startingIndex = 0, count = 20)

            coVerify(exactly = 1) { memoryDataSourceMockk.characters = listOf(characterMock) }
        }

    @Test
    fun `when the repository is cleared, then it saves an empty list to the memory data source`() =
        runTest {
            repository.clear()

            coVerify(exactly = 1) { memoryDataSourceMockk.characters = emptyList() }
        }
}
