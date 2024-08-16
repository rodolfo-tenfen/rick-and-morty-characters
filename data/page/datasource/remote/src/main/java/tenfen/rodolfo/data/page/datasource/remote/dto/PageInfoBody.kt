package tenfen.rodolfo.data.page.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PageInfoBody(
    val count: Int,
    val pages: Int,
    // val next: String,
    // val prev: String,
)
