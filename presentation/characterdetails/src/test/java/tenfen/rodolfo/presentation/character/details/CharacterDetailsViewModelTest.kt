package tenfen.rodolfo.presentation.character.details

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.net.URI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import tenfen.rodolfo.domain.character.Character
import tenfen.rodolfo.domain.character.CharacterRepository

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterDetailsViewModelTest {

    private var characterId = Character.Id(1)

    private var character =
        Character(
            id = characterId,
            name = "Name",
            status = Character.Status.ALIVE,
            species = Character.Species("Species"),
            type = "Type",
            gender = Character.Gender.MALE,
            origin = Character.Location("Earth"),
            currentLocation = Character.Location("Mars"),
            imageUri = URI("https://example.com/image.jpg"),
        )

    private var characterRepositoryMock = mockk<CharacterRepository> {
        coEvery { getCharacter(any()) } returns character
    }

    private val viewModel by lazy {
        CharacterDetailsViewModel(characterId, characterRepositoryMock)
    }

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when the view model is created, then it is in the loading state`() = runTest {
        viewModel

        assert(viewModel.uiState.value is CharacterDetailsUiState.Loading)
    }

    @Test
    fun `when the view model loads, then it loads the character with the provided id`() = runTest {
        viewModel.load()

        coVerify(exactly = 1) { characterRepositoryMock.getCharacter(characterId) }
    }

    @Test
    fun `given the repository returns null for the id provided, when the view model loads, then it goes to the error state`() =
        runTest {
            coEvery { characterRepositoryMock.getCharacter(any()) } returns null

            viewModel.load()

            assert(viewModel.uiState.value is CharacterDetailsUiState.Error)
        }
}
