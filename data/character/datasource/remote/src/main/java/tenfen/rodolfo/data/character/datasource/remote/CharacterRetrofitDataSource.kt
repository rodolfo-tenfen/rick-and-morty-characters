package tenfen.rodolfo.data.character.datasource.remote

import tenfen.rodolfo.data.character.datasource.remote.factory.CharacterFactory
import tenfen.rodolfo.data.character.datasource.remote.factory.LocationFactory
import tenfen.rodolfo.data.character.datasource.remote.service.CharacterService
import tenfen.rodolfo.domain.character.Character

internal class CharacterRetrofitDataSource(
    private val service: CharacterService,
    private val locationFactory: LocationFactory,
    private val characterFactory: CharacterFactory,
) : CharacterRemoteDataSource {

    override suspend fun getCharacter(id: Character.Id) =
        service.getCharacter(id.value).let { characterFactory.create(it, locationFactory) }

    override suspend fun getCharacters(startingIndex: Int, count: Int) =
        service.getCharacters().results.map { characterFactory.create(it, locationFactory) }

    override suspend fun getCharacters(name: String, status: Character.Status?) =
        service
            .getCharacters(name, status?.toString()?.lowercase() ?: "")
            .results
            .map { characterFactory.create(it, locationFactory) }
}
