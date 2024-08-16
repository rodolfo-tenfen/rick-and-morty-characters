package tenfen.rodolfo.domain.character

import java.net.URI

data class Character(
    val id: Id,
    val name: String,
    val status: Status,
    val species: Species,
    val type: String,
    val gender: Gender,
    val origin: Location,
    val currentLocation: Location,
    val imageUri: URI,
) {

    @JvmInline
    value class Id(val value: Int)

    @JvmInline
    value class Species(val name: String)

    enum class Status {
        ALIVE,
        DEAD,
        UNKNOWN,
    }

    enum class Gender {
        FEMALE,
        MALE,
        GENDERLESS,
        UNKNOWN,
    }

    data class Location(val name: String)
}
