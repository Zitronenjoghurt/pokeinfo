package industries.lemon.pokeinfo.thirdparty.tcgapi.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TcgSubType {
    NONE("none"),
    BREAK("BREAK"),
    BABY("Baby"),
    BASIC("Basic"),
    EX("EX"),
    GX("GX"),
    GGC("Goldenrod Game Corner"),
    ITEM("Item"),
    LEGEND("LEGEND"),
    LEVEL_UP("Level-Up"),
    MEGA("MEGA"),
    POKEMON_TOOL("Pokémon Tool"),
    POKEMON_TOOL_F("Pokémon Tool F"),
    RAPID_STRIKE("Rapid Strike"),
    RESTORED("Restored"),
    RSM("Rocket's Secret Machine"),
    SINGLE_STRIKE("Single Strike"),
    SPECIAL("Special"),
    STADIUM("Stadium"),
    STAGE_1("Stage 1"),
    STAGE_2("Stage 2"),
    SUPPORTER("Supporter"),
    TAG_TEAM("TAG TEAM"),
    TM("Technical Machine"),
    V("V"),
    VMAX("VMAX");

    private final String value;

    TcgSubType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static TcgSubType fromValue(String value) {
        for (TcgSubType status : values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return TcgSubType.NONE;
    }
}
