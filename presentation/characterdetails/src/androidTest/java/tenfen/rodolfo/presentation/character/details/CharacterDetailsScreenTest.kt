package tenfen.rodolfo.presentation.character.details

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.rule.GrantPermissionRule
import java.net.URL
import org.junit.Rule
import org.junit.Test
import tenfen.rodolfo.domain.character.Character

class CharacterDetailsScreenTest {

    @get:Rule
    val permissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.ACCESS_NETWORK_STATE,
        )

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenTheValueOfAnEntryIsPresent_whenItIsShown_thenThatEntryIsShown() {
        val state =
            CharacterDetailsUiState.Content(
                "Name",
                URL("https://example.com"),
                Character.Status.ALIVE,
                Character.Species("Species"),
                "",
                Character.Gender.MALE,
                Character.Location("Origin"),
                Character.Location("Current Location"),
            )

        composeTestRule.setContent {
            CharacterDetailsContent(state)
        }

        composeTestRule.onNodeWithText("Species").assertExists()
    }

    @Test
    fun givenTheValueOfAnEntryIsEmpty_whenItIsShown_thenThatEntryIsNotShown() {
        val state =
            CharacterDetailsUiState.Content(
                "Name",
                URL("https://example.com"),
                Character.Status.ALIVE,
                Character.Species("Species"),
                "",
                Character.Gender.MALE,
                Character.Location("Origin"),
                Character.Location("Current Location"),
            )

        composeTestRule.setContent {
            CharacterDetailsContent(state)
        }

        composeTestRule.onNodeWithText("Type").assertDoesNotExist()
    }
}
