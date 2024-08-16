package tenfen.rodolfo.presentation.filter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tenfen.rodolfo.presentation.theme.RickAndMortyCharactersTheme

@Composable
fun FilterScreen(
    name: String,
    onNameChanged: (String) -> Unit,
    selectedStatusIndex: Int?,
    onStatusSelected: (Int) -> Unit,
    onApplyFilterClicked: () -> Unit,
    onClearFilterClicked: () -> Unit,
    onBackClicked: () -> Unit,
) {
    Scaffold(topBar = { FilterTopAppBar(onBackClicked) }) { innerPadding ->
        Column(Modifier.padding(innerPadding).fillMaxSize()) {
            Column(Modifier.fillMaxWidth().weight(1f)) {
                NameInput(
                    value = name,
                    onNameChanged = onNameChanged,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                )

                StatusFilter(
                    selectedStatusIndex = selectedStatusIndex,
                    onStatusSelected = onStatusSelected,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .padding(top = 8.dp),
                )
            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                ApplyFilterButton(onApplyFilterClicked, Modifier.padding(16.dp))

                ClearFilterButton(onClearFilterClicked, Modifier.padding(16.dp))
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun FilterTopAppBar(onNavigateUp: () -> Unit, modifier: Modifier = Modifier) {
    TopAppBar(
        title = { Text("Filter") },
        navigationIcon = { NavigateUpButton(onNavigateUp) },
        modifier = modifier,
    )
}

@Composable
internal fun NavigateUpButton(onNavigateUp: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick = onNavigateUp, modifier) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "NavigateUp",
        )
    }
}

@Composable
internal fun NameInput(
    value: String,
    onNameChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = value,
        onValueChange = onNameChanged,
        label = { Text("Name") },
        modifier = modifier,
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
    )
}

@Composable
internal fun StatusFilter(
    selectedStatusIndex: Int?,
    onStatusSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(text = "Status:", style = MaterialTheme.typography.titleLarge, modifier = modifier)

    Row(modifier, horizontalArrangement = Arrangement.Center) {
        FilterOptionButton(
            optionName = "Alive",
            isSelected = selectedStatusIndex == 0,
            onClick = { onStatusSelected(0) },
            modifier = Modifier.padding(8.dp),
        )

        FilterOptionButton(
            optionName = "Dead",
            isSelected = selectedStatusIndex == 1,
            onClick = { onStatusSelected(1) },
            modifier = Modifier.padding(8.dp),
        )

        FilterOptionButton(
            optionName = "Unknown",
            isSelected = selectedStatusIndex == 2,
            onClick = { onStatusSelected(2) },
            modifier = Modifier.padding(8.dp),
        )
    }
}

@Composable
internal fun FilterOptionButton(
    optionName: String,
    isSelected: Boolean,
    onClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.clickable { onClick(!isSelected) }) {
        RadioButton(selected = isSelected, onClick = null)

        Text(text = optionName)
    }
}

@Composable
private fun ApplyFilterButton(onApplyFilterClicked: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedButton(onClick = onApplyFilterClicked, modifier = modifier) {
        Text("Filter", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun ClearFilterButton(onClearFilterClicked: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedButton(onClick = onClearFilterClicked, modifier = modifier) {
        Text(text = "Clear filter", style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview
@Composable
private fun FilterScreenPreview() {
    RickAndMortyCharactersTheme {
        FilterScreen(
            name = "Name",
            selectedStatusIndex = 1,
            onNameChanged = {},
            onStatusSelected = {},
            onApplyFilterClicked = {},
            onClearFilterClicked = {},
            onBackClicked = {},
        )
    }
}

@Preview
@Composable
private fun FilterTopAppBarPreview() {
    RickAndMortyCharactersTheme {
        FilterTopAppBar(onNavigateUp = {})
    }
}

@Preview
@Composable
private fun NameInputPreview() {
    RickAndMortyCharactersTheme {
        NameInput(value = "Rick Sanchez", onNameChanged = {})
    }
}

@Preview
@Composable
private fun StatusFilterPreview() {
    RickAndMortyCharactersTheme {
        StatusFilter(selectedStatusIndex = 2, onStatusSelected = {})
    }
}

@Preview
@Composable
private fun FilterOptionButtonPreview() {
    RickAndMortyCharactersTheme {
        FilterOptionButton(optionName = "Alive", isSelected = true, onClick = {})
    }
}
