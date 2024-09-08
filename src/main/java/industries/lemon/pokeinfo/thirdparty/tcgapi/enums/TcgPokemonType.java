package industries.lemon.pokeinfo.thirdparty.tcgapi.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TcgPokemonType {
    NONE("none"),
    COLORLESS("Colorless"),
    DARKNESS("Darkness"),
    DRAGON("Dragon"),
    FAIRY("Fairy"),
    FIGHTING("Fighting"),
    FIRE("Fire"),
    GRASS("Grass"),
    LIGHTNING("Lightning"),
    METAL("Metal"),
    PSYCHIC("Psychic"),
    WATER("Water");

    private final String value;

    TcgPokemonType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static TcgPokemonType fromValue(String value) {
        for (TcgPokemonType status : values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return TcgPokemonType.NONE;
    }
}
