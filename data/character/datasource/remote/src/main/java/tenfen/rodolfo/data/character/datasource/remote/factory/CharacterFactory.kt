package tenfen.rodolfo.data.character.datasource.remote.factory

import java.net.URI
import tenfen.rodolfo.data.character.datasource.remote.dto.CharacterBody
import tenfen.rodolfo.domain.character.Character

internal interface CharacterFactory {

    fun create(data: CharacterBody, locationFactory: LocationFactory): Character
}

internal val characterFactory = object : CharacterFactory {

    override fun create(data: CharacterBody, locationFactory: LocationFactory) = with(data) {
        Character(
            id = Character.Id(id),
            name = name,
            status = status,
            species = Character.Species(species),
            type = type,
            gender = gender,
            origin = locationFactory.create(origin),
            currentLocation = locationFactory.create(location),
            imageUri = URI(image),
        )
    }
}
