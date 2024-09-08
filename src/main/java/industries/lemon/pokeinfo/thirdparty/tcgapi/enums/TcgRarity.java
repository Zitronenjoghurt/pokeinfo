package industries.lemon.pokeinfo.thirdparty.tcgapi.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TcgRarity {
    NONE("none"),
    ACE_SPEC_RARE("ACE SPEC Rare"),
    AMAZING_RARE("Amazing Rare"),
    CLASSIC_COLLECTION("Classic Collection"),
    COMMON("Common"),
    DOUBLE_RARE("Double Rare"),
    HYPER_RARE("Hyper Rare"),
    ILLUSTRATION_RARE("Illustration Rare"),
    LEGEND("LEGEND"),
    PROMO("Promo"),
    RARE("Rare"),
    RADIANT_RARE("Radiant Rare"),
    RARE_ACE("Rare ACE"),
    RARE_BREAK("Rare BREAK"),
    RARE_HOLO("Rare Holo"),
    RARE_HOLO_EX("Rare Holo EX"),
    RARE_HOLO_GX("Rare Holo GX"),
    RARE_HOLO_LVX("Rare Holo LV.X"),
    RARE_HOLO_STAR("Rare Holo Star"),
    RARE_HOLO_V("Rare Holo V"),
    RARE_HOLO_VMAX("Rare Holo VMAX"),
    RARE_HOLO_VSTAR("Rare Holo VSTAR"),
    RARE_PRIME("Rare Prime"),
    RARE_PRISM_STAR("Rare Prime Star"),
    RARE_RAINBOW("Rare Rainbow"),
    RARE_SECRET("Rare Secret"),
    RARE_SHINING("Rare Shining"),
    RARE_SHINY("Rare Shiny"),
    RARE_SHINY_GX("Rare Shiny GX"),
    RARE_ULTRA("Rare Ultra"),
    SHINY_RARE("Shiny Rare"),
    SHINY_ULTRA_RARE("Shiny Ultra Rare"),
    SPECIAL_ILLUSTRATION_RARE("Special Illustration Rare"),
    TRAINER_GALLERY_RARE_HOLO("Trainer Gallery Rare Holo"),
    ULTRA_RARE("Ultra Rare"),
    UNCOMMON("Uncommon");

    private final String value;

    TcgRarity(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static TcgRarity fromValue(String value) {
        for (TcgRarity status : values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return TcgRarity.NONE;
    }
}
