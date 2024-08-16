package tenfen.rodolfo.data.character.datasource.remote.dto

import kotlinx.serialization.Serializable
import tenfen.rodolfo.data.page.datasource.remote.dto.PageInfoBody

@Serializable
internal data class ResultBody(val info: PageInfoBody, val results: List<CharacterBody>)
