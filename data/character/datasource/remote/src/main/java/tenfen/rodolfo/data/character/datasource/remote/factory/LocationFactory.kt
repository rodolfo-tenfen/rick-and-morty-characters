package tenfen.rodolfo.data.character.datasource.remote.factory

import tenfen.rodolfo.data.character.datasource.remote.dto.LocationBody
import tenfen.rodolfo.domain.character.Character

internal interface LocationFactory {

    fun create(data: LocationBody): Character.Location
}

internal val locationFactory = object : LocationFactory {

    override fun create(data: LocationBody) = with(data) {
        Character.Location(name = name)
    }
}
