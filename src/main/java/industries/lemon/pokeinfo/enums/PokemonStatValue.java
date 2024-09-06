package industries.lemon.pokeinfo.enums;

import lombok.Getter;

@Getter
public enum PokemonStatValue {
    HP(
            "hp",
            "#2EEB5D"
    ),
    ATK(
            "attack",
            "#EED545"
    ),
    DEF(
            "defense",
            "#FD8732"
    ),
    SPATK(
            "special-attack",
            "#48CCD2"
    ),
    SPDEF(
            "special-defense",
            "#436BFF"
    ),
    SPEED(
            "speed",
            "#C33CFF"
    ),;

    private final String internalName;
    private final String color;

    PokemonStatValue(
            String internalName,
            String color
    ) {
        this.internalName = internalName;
        this.color = color;
    }
}
