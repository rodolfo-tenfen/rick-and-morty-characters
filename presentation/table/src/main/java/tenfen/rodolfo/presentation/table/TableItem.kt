package tenfen.rodolfo.presentation.table

data class TableItem(val label: Label, val value: Value) {

    @JvmInline
    value class Label(val content: String)

    @JvmInline
    value class Value(val content: String)
}
