package tenfen.rodolfo.data.character.datasource.memory

import tenfen.rodolfo.domain.character.Character

interface CharacterMemoryDataSource {

    var characters: List<Character>

    operator fun get(id: Character.Id): Character?

    operator fun set(id: Character.Id, character: Character)
}
