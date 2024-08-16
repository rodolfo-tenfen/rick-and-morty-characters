package tenfen.rodolfo.presentation.character.item

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import java.net.URL
import tenfen.rodolfo.presentation.theme.RickAndMortyCharactersTheme

@Composable
fun CharacterItem(
    name: String,
    imageUrl: URL,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.wrapContentHeight().fillMaxWidth().clickable(onClick = onClick),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CharacterImage(imageUrl)

            Text(
                text = name,
                modifier = Modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}

@Composable
internal fun CharacterImage(url: URL, modifier: Modifier = Modifier) {
    var isLoading by remember { mutableStateOf(true) }

    Box(
        modifier.size(90.dp),
        contentAlignment = Alignment.Center,
    ) {
        AsyncImage(
            model = url.toString(),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
            error = painterResource(id = R.drawable.character_item_loading_error),
            onLoading = { isLoading = true },
            onSuccess = { isLoading = false },
            onError = { isLoading = false },
        )

        if (isLoading) CircularProgressIndicator(Modifier.size(50.dp))
    }
}

@Preview
@Composable
fun CharacterItemPreview() {
    RickAndMortyCharactersTheme {
        CharacterItem(
            name = "Character Name",
            imageUrl = URL("https://rickandmortyapi.com/api/character/avatar/1.jpeg"),
            onClick = { },
        )
    }
}
