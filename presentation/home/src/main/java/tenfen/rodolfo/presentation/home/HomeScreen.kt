package tenfen.rodolfo.presentation.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.net.URL
import org.koin.androidx.compose.koinViewModel
import tenfen.rodolfo.domain.character.Character.Id
import tenfen.rodolfo.presentation.character.item.CharacterItem
import tenfen.rodolfo.presentation.filter.FilterScreen
import tenfen.rodolfo.presentation.theme.RickAndMortyCharactersTheme

@Composable
internal fun HomeScreen(
    onShowCharacterDetails: (Id) -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.load(startingIndex = 0, count = 20)
    }

    state.let { it as? HomeUiState.Content }
        ?.detailedCharacterId
        ?.let {
            onShowCharacterDetails(it)
            viewModel.onCharacterDetailsShown()
        }

    BackHandler(state is HomeUiState.Filter) {
        viewModel.hideFilter()
    }

    HomeScreen(
        state = state,
        onShowFilter = viewModel::showFilter,
        onNameFilterChanged = viewModel::onNameFilterChanged,
        onStatusFilterSelected = viewModel::onStatusFilterSelected,
        onApplyFilter = viewModel::applyFilter,
        onClearFilter = viewModel::clearFilter,
        onHideFilter = viewModel::hideFilter,
        onItemClick = viewModel::onItemClick,
        onRetry = viewModel::retry,
    )
}

@Composable
internal fun HomeScreen(
    state: HomeUiState,
    onShowFilter: () -> Unit,
    onNameFilterChanged: (String) -> Unit,
    onStatusFilterSelected: (Int) -> Unit,
    onApplyFilter: () -> Unit,
    onClearFilter: () -> Unit,
    onHideFilter: () -> Unit,
    onItemClick: (CharacterItemUiState) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (state) {
                HomeUiState.Loading -> HomeLoading()
                is HomeUiState.Content ->
                    CharacterList(
                        characterItems = state.characters,
                        onFilterClicked = onShowFilter,
                        onItemClicked = onItemClick,
                        modifier = Modifier.fillMaxSize().padding(vertical = 4.dp),
                    )
                is HomeUiState.Filter ->
                    FilterScreen(
                        name = state.name,
                        onNameChanged = onNameFilterChanged,
                        selectedStatusIndex = state.selectedStatusIndex,
                        onStatusSelected = onStatusFilterSelected,
                        onApplyFilterClicked = onApplyFilter,
                        onClearFilterClicked = onClearFilter,
                        onBackClicked = onHideFilter,
                    )
                HomeUiState.Error -> HomeError(onRetry = onRetry)
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun HomeTopAppBar(onFilterClick: () -> Unit, modifier: Modifier = Modifier) {
    TopAppBar(
        title = { Text("Characters") },
        modifier = modifier,
        actions = { FilterAction(onFilterClick) },
    )
}

@Composable
private fun FilterAction(onFilterClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick = onFilterClick, modifier = modifier) {
        Icon(
            painter = painterResource(R.drawable.home_filter),
            contentDescription = "Filter",
        )
    }
}

@Composable
internal fun CharacterList(
    characterItems: List<CharacterItemUiState>,
    onFilterClicked: () -> Unit,
    onItemClicked: (CharacterItemUiState) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { HomeTopAppBar(onFilterClicked) },
    ) { innerPadding ->
        LazyColumn(Modifier.padding(innerPadding)) {
            items(characterItems, key = { it.id.value }) { character ->
                CharacterItem(
                    name = character.name,
                    imageUrl = character.imageUrl,
                    onClick = { onItemClicked(character) },
                    modifier = Modifier.padding(horizontal = 8.dp).padding(vertical = 4.dp),
                )
            }
        }
    }
}

@Composable
internal fun HomeLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(Modifier.size(200.dp))
    }
}

@Composable
internal fun HomeError(onRetry: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "No characters found",
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

private val previewCharacterState =
    CharacterItemUiState(
        id = Id(1),
        name = "Rick Sanchez",
        imageUrl = URL("https://www.example.com"),
    )

private val previewHomeState =
    HomeUiState.Content(characters = listOf(previewCharacterState), detailedCharacterId = null)

@Preview
@Composable
private fun HomeScreenPreview() {
    RickAndMortyCharactersTheme {
        HomeScreen(
            state = previewHomeState,
            onShowFilter = {},
            onNameFilterChanged = {},
            onStatusFilterSelected = {},
            onApplyFilter = {},
            onClearFilter = {},
            onHideFilter = {},
            onItemClick = {},
            onRetry = {},
        )
    }
}

@Preview
@Composable
private fun CharacterListPreview() {
    RickAndMortyCharactersTheme {
        CharacterList(
            characterItems = previewHomeState.characters,
            onFilterClicked = {},
            onItemClicked = {},
        )
    }
}

@Preview
@Composable
private fun HomeLoadingPreview() {
    RickAndMortyCharactersTheme {
        HomeLoading()
    }
}

@Preview
@Composable
private fun HomeErrorPreview() {
    RickAndMortyCharactersTheme {
        HomeError(onRetry = {})
    }
}
