package tenfen.rodolfo.data.character.datasource.remote.dto

import kotlinx.serialization.Serializable
import tenfen.rodolfo.data.character.datasource.remote.serialization.CharacterGenderAsStringSerializer
import tenfen.rodolfo.data.character.datasource.remote.serialization.CharacterStatusAsStringSerializer
import tenfen.rodolfo.domain.character.Character

@Serializable
internal data class CharacterBody(
    val id: Int,
    val name: String,
    @Serializable(with = CharacterStatusAsStringSerializer::class)
    val status: Character.Status,
    val species: String,
    val type: String,
    @Serializable(with = CharacterGenderAsStringSerializer::class)
    val gender: Character.Gender,
    val origin: LocationBody,
    val location: LocationBody,
    val image: String,
)
