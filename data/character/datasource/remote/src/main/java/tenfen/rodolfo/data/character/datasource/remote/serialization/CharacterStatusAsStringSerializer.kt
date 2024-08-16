package tenfen.rodolfo.data.character.datasource.remote.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import tenfen.rodolfo.domain.character.Character.Status

internal object CharacterStatusAsStringSerializer : KSerializer<Status> {

    override val descriptor = PrimitiveSerialDescriptor("CharacterStatus", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder) = when (val status = decoder.decodeString()) {
        Status.ALIVE.stringRepresentation -> Status.ALIVE
        Status.DEAD.stringRepresentation -> Status.DEAD
        Status.UNKNOWN.stringRepresentation -> Status.UNKNOWN
        else -> throw IllegalArgumentException("Invalid Character Status $status")
    }

    override fun serialize(encoder: Encoder, value: Status) =
        encoder.encodeString(value.stringRepresentation)
}

private val Status.stringRepresentation
    get() = when (this) {
        Status.ALIVE -> "Alive"
        Status.DEAD -> "Dead"
        Status.UNKNOWN -> "unknown"
    }
