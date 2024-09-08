package industries.lemon.pokeinfo.thirdparty.tcgapi.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TcgSuperType {
    NONE("none"),
    ENERGY("Energy"),
    POKEMON("Pokémon"),
    TRAINER("Trainer");

    private final String value;

    TcgSuperType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static TcgSuperType fromValue(String value) {
        for (TcgSuperType status : values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return TcgSuperType.NONE;
    }
}
