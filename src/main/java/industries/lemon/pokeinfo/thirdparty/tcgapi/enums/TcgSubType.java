package industries.lemon.pokeinfo.thirdparty.tcgapi.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TcgSubType {
    NONE("none"),
    ACE_SPEC("ACE SPEC"),
    ANCIENT("Ancient"),
    BREAK("BREAK"),
    BABY("Baby"),
    BASIC("Basic"),
    EX("EX"),
    ETERNAMAX("Eternamax"),
    FUSION_STRIKE("Fusion Strike"),
    FUTURE("Future"),
    GX("GX"),
    GGC("Goldenrod Game Corner"),
    ITEM("Item"),
    LEGEND("LEGEND"),
    LEVEL_UP("Level-Up"),
    MEGA("MEGA"),
    POKEMON_TOOL("Pokémon Tool"),
    POKEMON_TOOL_F("Pokémon Tool F"),
    PRIME("Prime"),
    PRISM_STAR("Prime Star"),
    RADIANT("Radiant"),
    RAPID_STRIKE("Rapid Strike"),
    RESTORED("Restored"),
    RSM("Rocket's Secret Machine"),
    SP("SP"),
    SINGLE_STRIKE("Single Strike"),
    SPECIAL("Special"),
    STADIUM("Stadium"),
    STAGE_1("Stage 1"),
    STAGE_2("Stage 2"),
    STAR("Star"),
    SUPPORTER("Supporter"),
    TAG_TEAM("TAG TEAM"),
    TEAM_PLASMA("Team Plasma"),
    TM("Technical Machine"),
    TERA("Tera"),
    ULTRA_BREAST("Ultra Beast"),
    V("V"),
    V_UNION("V-UNION"),
    VMAX("VMAX"),
    VSTAR("VSTAR"),
    EX_ALT("ex");

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
