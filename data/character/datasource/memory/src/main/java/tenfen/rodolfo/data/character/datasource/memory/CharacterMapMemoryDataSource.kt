package tenfen.rodolfo.data.character.datasource.memory

import tenfen.rodolfo.domain.character.Character

internal object CharacterMapMemoryDataSource : CharacterMemoryDataSource {

    private var _characters = mutableMapOf<Character.Id, Character>()

    override var characters
        get() = _characters.values.toList()
        set(value) {
            _characters.clear()
            _characters = value.associateByTo(_characters, Character::id)
        }

    override fun get(id: Character.Id): Character? = _characters[id]

    override fun set(id: Character.Id, character: Character) {
        _characters[id] = character
    }
}
