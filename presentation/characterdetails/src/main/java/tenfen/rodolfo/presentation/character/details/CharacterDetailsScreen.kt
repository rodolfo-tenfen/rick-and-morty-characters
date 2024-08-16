package tenfen.rodolfo.presentation.character.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import java.net.URL
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import tenfen.rodolfo.domain.character.Character
import tenfen.rodolfo.domain.character.Character.Gender
import tenfen.rodolfo.domain.character.Character.Location
import tenfen.rodolfo.domain.character.Character.Species
import tenfen.rodolfo.domain.character.Character.Status
import tenfen.rodolfo.presentation.character.details.CharacterDetailsUiState.Content as ContentState
import tenfen.rodolfo.presentation.character.details.CharacterDetailsUiState.Error
import tenfen.rodolfo.presentation.character.details.CharacterDetailsUiState.Loading
import tenfen.rodolfo.presentation.table.Table
import tenfen.rodolfo.presentation.table.TableItem
import tenfen.rodolfo.presentation.table.TableItem.Label as TableLabel
import tenfen.rodolfo.presentation.table.TableItem.Value as TableValue
import tenfen.rodolfo.presentation.theme.RickAndMortyCharactersTheme

@Composable
internal fun CharacterDetailsScreen(
    characterId: Character.Id,
    onNavigateUp: () -> Unit,
    viewModel: CharacterDetailsViewModel = koinViewModel { parametersOf(characterId) },
) {
    LaunchedEffect(Unit) {
        viewModel.load()
    }

    val uiState by viewModel.uiState.collectAsState()

    CharacterDetails(uiState, onNavigateUp, viewModel::retry)
}

@Composable
internal fun CharacterDetails(
    uiState: CharacterDetailsUiState,
    onNavigateUp: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Box(Modifier.fillMaxSize().padding(innerPadding)) {
            when (uiState) {
                Loading -> CharacterDetailsLoading()
                is ContentState -> CharacterDetailsContent(uiState)
                Error -> CharacterDetailsError(onRetry)
            }

            NavigateUpButton(onNavigateUp, modifier = Modifier.align(Alignment.TopStart))
        }
    }
}

@Composable
internal fun NavigateUpButton(onNavigateUp: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick = onNavigateUp, modifier = modifier) {
        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Navigate up")
    }
}

@Composable
internal fun CharacterDetailsContent(state: ContentState, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        item {
            CharacterImage(url = state.imageUrl, modifier = Modifier.height(300.dp).fillMaxWidth())
        }

        item {
            CharacterName(state, modifier = Modifier.padding(top = 16.dp))
        }

        item {
            Table(entries = extractTableEntries(state), Modifier.padding(top = 16.dp))
        }
    }
}

@Composable
internal fun CharacterImage(url: URL, modifier: Modifier = Modifier) {
    SubcomposeAsyncImage(
        model = url.toString(),
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Fit,
        alignment = Alignment.TopCenter,
        loading = { CenteredProgressIndicator(size = 100.dp) },
        error = { ImageError() },
    )
}

@Composable
private fun CharacterName(state: CharacterDetailsUiState.Content, modifier: Modifier = Modifier) {
    Text(text = state.name, modifier = modifier, style = MaterialTheme.typography.headlineLarge)
}

@Composable
internal fun CharacterDetailsLoading(modifier: Modifier = Modifier) {
    CenteredProgressIndicator(size = 200.dp, modifier)
}

@Composable
internal fun CharacterDetailsError(onRetry: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "There was an error loading the character's details",
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.error,
                fontWeight = FontWeight.Bold,
            )

            FilledTonalButton(onClick = onRetry) {
                Text(text = "Retry")
            }
        }
    }
}

@Composable
internal fun CenteredProgressIndicator(size: Dp, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(Modifier.size(size))
    }
}

@Composable
internal fun ImageError() {
    Icon(
        painter = painterResource(id = R.drawable.character_details_loading_error),
        contentDescription = null,
    )
}

internal fun extractTableEntries(state: ContentState) =
    listOf(
        TableItem(TableLabel("Status:"), TableValue(state.status.stringResource)),
        TableItem(TableLabel("Species:"), TableValue(state.species.name)),
        TableItem(TableLabel("Type:"), TableValue(state.type)),
        TableItem(TableLabel("Gender:"), TableValue(state.gender.stringResource)),
        TableItem(TableLabel("Origin:"), TableValue(state.origin.name)),
        TableItem(TableLabel("Current Location:"), TableValue(state.currentLocation.name)),
    ).filter { it.value.content.isNotBlank() }

internal val Status.stringResource
    get() = when (this) {
        Status.ALIVE -> "Alive"
        Status.DEAD -> "Dead"
        Status.UNKNOWN -> "Unknown"
    }

internal val Gender.stringResource
    get() = when (this) {
        Gender.FEMALE -> "Female"
        Gender.MALE -> "Male"
        Gender.GENDERLESS -> "Genderless"
        Gender.UNKNOWN -> "Unknown"
    }

private val previewState =
    ContentState(
        name = "Character Name",
        imageUrl = URL("https://www.example.com"),
        status = Status.ALIVE,
        species = Species("Human"),
        type = "Humanoid",
        gender = Gender.MALE,
        origin = Location("Earth"),
        currentLocation = Location("Mars"),
    )

@Preview
@Composable
private fun CharacterDetailsPreview() {
    CharacterDetails(previewState, onNavigateUp = {}, onRetry = {}, modifier = Modifier)
}

@Preview
@Composable
private fun CharacterDetailsContentPreview() {
    RickAndMortyCharactersTheme {
        CharacterDetailsContent(previewState)
    }
}

@Preview
@Composable
private fun CharacterDetailsLoadingPreview() {
    RickAndMortyCharactersTheme {
        CharacterDetailsLoading()
    }
}

@Preview
@Composable
private fun CharacterDetailsErrorPreview() {
    RickAndMortyCharactersTheme {
        CharacterDetailsError(onRetry = {})
    }
}
