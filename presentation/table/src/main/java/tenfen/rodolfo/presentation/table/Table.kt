package tenfen.rodolfo.presentation.table

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tenfen.rodolfo.presentation.table.TableItem.Label
import tenfen.rodolfo.presentation.table.TableItem.Value

@Composable
fun Table(entries: List<TableItem>, modifier: Modifier = Modifier) {
    Column(modifier.border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))) {
        entries.forEachIndexed { i, entry ->
            TableEntry(label = entry.label, value = entry.value)

            if (i != entries.lastIndex) HorizontalDivider()
        }
    }
}

@Composable
internal fun TableEntry(label: Label, value: Value, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth().padding(horizontal = 8.dp).height(IntrinsicSize.Min)) {
        Label(label)

        VerticalDivider(modifier = Modifier.width(1.dp))

        Value(value)
    }
}

@Composable
internal fun RowScope.Label(label: Label, modifier: Modifier = Modifier) {
    Box(modifier = modifier.weight(1f).padding(8.dp)) {
        Text(
            text = label.content,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
        )
    }
}

@Composable
internal fun RowScope.Value(value: Value, modifier: Modifier = Modifier) {
    Box(modifier = modifier.weight(2f).padding(8.dp)) {
        Text(text = value.content, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview
@Composable
fun TablePreview() {
    val entries =
        listOf(
            TableItem(label = Label("Name:"), value = Value("John Doe")),
            TableItem(label = Label("Gender:"), value = Value("Male")),
        )

    Table(entries)
}

@Preview
@Composable
fun TableEntryPreview() {
    TableEntry(label = Label("Name:"), value = Value("John Doe"))
}
