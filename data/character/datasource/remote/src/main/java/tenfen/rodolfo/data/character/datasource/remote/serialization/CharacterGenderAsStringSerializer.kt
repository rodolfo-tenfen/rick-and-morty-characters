package tenfen.rodolfo.data.character.datasource.remote.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import tenfen.rodolfo.domain.character.Character.Gender

internal object CharacterGenderAsStringSerializer : KSerializer<Gender> {

    override val descriptor = PrimitiveSerialDescriptor("CharacterGender", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder) = when (val gender = decoder.decodeString()) {
        Gender.FEMALE.stringRepresentation -> Gender.FEMALE
        Gender.MALE.stringRepresentation -> Gender.MALE
        Gender.GENDERLESS.stringRepresentation -> Gender.GENDERLESS
        Gender.UNKNOWN.stringRepresentation -> Gender.UNKNOWN
        else -> throw IllegalArgumentException("Invalid Character Gender $gender")
    }

    override fun serialize(encoder: Encoder, value: Gender) =
        encoder.encodeString(value.stringRepresentation)
}

private val Gender.stringRepresentation
    get() = when (this) {
        Gender.FEMALE -> "Female"
        Gender.MALE -> "Male"
        Gender.GENDERLESS -> "Genderless"
        Gender.UNKNOWN -> "unknown"
    }
