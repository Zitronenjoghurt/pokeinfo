package industries.lemon.pokeinfo.enums;

import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum PokemonTyping {
    NORMAL(
            "Normal",
            "#A8A77A"
    ),
    FIRE(
            "Fire",
            "#EE8130"
    ),
    WATER(
            "Water",
            "#6390F0"
    ),
    ELECTRIC(
            "Electric",
            "#F7D02C"
    ),
    GRASS(
            "Grass",
            "#7AC74C"
    ),
    ICE(
            "Ice",
            "#96D9D6"
    ),
    FIGHTING(
            "Fighting",
            "#C22E28"
    ),
    POISON(
            "Poison",
            "#A33EA1"
    ),
    GROUND(
            "Ground",
            "#E2BF65"
    ),
    FLYING(
            "Flying",
            "#A98FF3"
    ),
    PSYCHIC(
            "Psychic",
            "#F95587"
    ),
    BUG(
            "Bug",
            "#A6B91A"
    ),
    ROCK(
            "Rock",
            "#B6A136"
    ),
    GHOST(
            "Ghost",
            "#735797"
    ),
    DRAGON(
            "Dragon",
            "#6F35FC"
    ),
    DARK(
            "Dark",
            "#705746"
    ),
    STEEL(
            "Steel",
            "#B7B7CE"
    ),
    FAIRY(
            "Fairy",
            "#D685AD"
    );

    private final String displayName;
    private final String displayColor;

    PokemonTyping(
            String displayName,
            String displayColor
    ) {
        this.displayName = displayName;
        this.displayColor = displayColor;
    }

    private static final Map<String, PokemonTyping> NAME_MAP = Stream.of(values())
            .collect(Collectors.toMap(type -> type.getDisplayName().toLowerCase(), type -> type));

    public static PokemonTyping fromName(String name) {
        return NAME_MAP.get(name.toLowerCase());
    }
}
