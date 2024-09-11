package industries.lemon.pokeinfo.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum PokemonTyping {
    NORMAL("Normal", "#A8A77A"),
    FIRE("Fire", "#EE8130"),
    WATER("Water", "#6390F0"),
    ELECTRIC("Electric", "#F7D02C"),
    GRASS("Grass", "#7AC74C"),
    ICE("Ice", "#96D9D6"),
    FIGHTING("Fighting", "#C22E28"),
    POISON("Poison", "#A33EA1"),
    GROUND("Ground", "#E2BF65"),
    FLYING("Flying", "#A98FF3"),
    PSYCHIC("Psychic", "#F95587"),
    BUG("Bug", "#A6B91A"),
    ROCK("Rock", "#B6A136"),
    GHOST("Ghost", "#735797"),
    DRAGON("Dragon", "#6F35FC"),
    DARK("Dark", "#705746"),
    STEEL("Steel", "#B7B7CE"),
    FAIRY("Fairy", "#D685AD");

    private final String displayName;
    private final String displayColor;

    private static final Map<String, PokemonTyping> NAME_MAP = Stream.of(values())
            .collect(Collectors.toMap(type -> type.getDisplayName().toLowerCase(), type -> type));

    private static final Map<PokemonTyping, Map<PokemonTyping, Double>> EFFECTIVENESS_MAP = new EnumMap<>(PokemonTyping.class);

    private static final PokemonTyping[] VALUES = values();

    PokemonTyping(
            String displayName,
            String displayColor
    ) {
        this.displayName = displayName;
        this.displayColor = displayColor;
    }

    static {
        for (PokemonTyping type : values()) {
            EFFECTIVENESS_MAP.put(type, new EnumMap<>(PokemonTyping.class));
        }
        initializeEffectiveness();
    }

    public static PokemonTyping fromName(String name) {
        return NAME_MAP.getOrDefault(name.toLowerCase(), null);
    }

    public int getId() {
        return this.ordinal() + 1;
    }

    public static PokemonTyping fromId(int id) {
        return (id > 0 && id <= VALUES.length) ? VALUES[id - 1] : null;
    }

    public static int getMaxId() {
        return VALUES.length;
    }

    public double getEffectivenessAgainst(PokemonTyping defendingType) {
        return EFFECTIVENESS_MAP.get(this).getOrDefault(defendingType, 1.0);
    }

    public static Map<PokemonTyping, Double> calculateEffectiveness(PokemonTyping defendingType) {
        return Arrays.stream(PokemonTyping.values())
                .collect(Collectors.toMap(
                        attackingType -> attackingType,
                        attackingType -> calculateDamageMultiplier(attackingType, defendingType)
                ));
    }

    public static Map<PokemonTyping, Double> calculateEffectiveness(PokemonTyping defendingType1, PokemonTyping defendingType2) {
        return Arrays.stream(PokemonTyping.values())
                .collect(Collectors.toMap(
                        attackingType -> attackingType,
                        attackingType -> calculateDamageMultiplier(attackingType, defendingType1, defendingType2)
                ));
    }

    public static double calculateDamageMultiplier(PokemonTyping attackingType, PokemonTyping defendingType) {
        return attackingType.getEffectivenessAgainst(defendingType);
    }

    public static double calculateDamageMultiplier(PokemonTyping attackingType, PokemonTyping defendingType1, PokemonTyping defendingType2) {
        return attackingType.getEffectivenessAgainst(defendingType1) * attackingType.getEffectivenessAgainst(defendingType2);
    }

    private static void setEffectiveness(PokemonTyping attacker, PokemonTyping defender, double effectiveness) {
        EFFECTIVENESS_MAP.get(attacker).put(defender, effectiveness);
    }

    // Initialize type effectiveness
    private static void initializeEffectiveness() {
        setEffectiveness(NORMAL, ROCK, 0.5);
        setEffectiveness(NORMAL, STEEL, 0.5);
        setEffectiveness(NORMAL, GHOST, 0);

        setEffectiveness(FIGHTING, NORMAL, 2.0);
        setEffectiveness(FIGHTING, ROCK, 2.0);
        setEffectiveness(FIGHTING, STEEL, 2.0);
        setEffectiveness(FIGHTING, ICE, 2.0);
        setEffectiveness(FIGHTING, DARK, 2.0);
        setEffectiveness(FIGHTING, FLYING, 0.5);
        setEffectiveness(FIGHTING, POISON, 0.5);
        setEffectiveness(FIGHTING, BUG, 0.5);
        setEffectiveness(FIGHTING, PSYCHIC, 0.5);
        setEffectiveness(FIGHTING, FAIRY, 0.5);
        setEffectiveness(FIGHTING, GHOST, 0.5);

        setEffectiveness(FLYING, FIGHTING, 2.0);
        setEffectiveness(FLYING, BUG, 2.0);
        setEffectiveness(FLYING, GRASS, 2.0);
        setEffectiveness(FLYING, ROCK, 0.5);
        setEffectiveness(FLYING, STEEL, 0.5);
        setEffectiveness(FLYING, ELECTRIC, 0.5);

        setEffectiveness(POISON, GROUND, 2.0);
        setEffectiveness(POISON, FAIRY, 2.0);
        setEffectiveness(POISON, POISON, 0.5);
        setEffectiveness(POISON, GROUND, 0.5);
        setEffectiveness(POISON, ROCK, 0.5);
        setEffectiveness(POISON, GHOST, 0.5);
        setEffectiveness(POISON, STEEL, 0.0);

        setEffectiveness(GROUND, POISON, 2.0);
        setEffectiveness(GROUND, ROCK, 2.0);
        setEffectiveness(GROUND, STEEL, 2.0);
        setEffectiveness(GROUND, FIRE, 2.0);
        setEffectiveness(GROUND, ELECTRIC, 2.0);
        setEffectiveness(GROUND, BUG, 0.5);
        setEffectiveness(GROUND, GRASS, 0.5);
        setEffectiveness(GROUND, FLYING, 0.0);

        setEffectiveness(ROCK, FLYING, 2.0);
        setEffectiveness(ROCK, BUG, 2.0);
        setEffectiveness(ROCK, FIRE, 2.0);
        setEffectiveness(ROCK, ICE, 2.0);
        setEffectiveness(ROCK, FIGHTING, 0.5);
        setEffectiveness(ROCK, GROUND, 0.5);
        setEffectiveness(ROCK, STEEL, 0.5);

        setEffectiveness(BUG, GRASS, 2.0);
        setEffectiveness(BUG, PSYCHIC, 2.0);
        setEffectiveness(BUG, DARK, 2.0);
        setEffectiveness(BUG, FIGHTING, 0.5);
        setEffectiveness(BUG, FLYING, 0.5);
        setEffectiveness(BUG, POISON, 0.5);
        setEffectiveness(BUG, GHOST, 0.5);
        setEffectiveness(BUG, STEEL, 0.5);
        setEffectiveness(BUG, FIRE, 0.5);
        setEffectiveness(BUG, FAIRY, 0.5);

        setEffectiveness(GHOST, GHOST, 2.0);
        setEffectiveness(GHOST, PSYCHIC, 2.0);
        setEffectiveness(GHOST, DARK, 0.5);
        setEffectiveness(GHOST, NORMAL, 0.0);

        setEffectiveness(STEEL, ROCK, 2.0);
        setEffectiveness(STEEL, ICE, 2.0);
        setEffectiveness(STEEL, FAIRY, 2.0);
        setEffectiveness(STEEL, STEEL, 0.5);
        setEffectiveness(STEEL, FIRE, 0.5);
        setEffectiveness(STEEL, WATER, 0.5);
        setEffectiveness(STEEL, ELECTRIC, 0.5);

        setEffectiveness(FIRE, BUG, 2.0);
        setEffectiveness(FIRE, STEEL, 2.0);
        setEffectiveness(FIRE, GRASS, 2.0);
        setEffectiveness(FIRE, ICE, 2.0);
        setEffectiveness(FIRE, ROCK, 0.5);
        setEffectiveness(FIRE, FIRE, 0.5);
        setEffectiveness(FIRE, WATER, 0.5);
        setEffectiveness(FIRE, DRAGON, 0.5);

        setEffectiveness(WATER, GROUND, 2.0);
        setEffectiveness(WATER, ROCK, 2.0);
        setEffectiveness(WATER, FIRE, 2.0);
        setEffectiveness(WATER, WATER, 0.5);
        setEffectiveness(WATER, GROUND, 0.5);
        setEffectiveness(WATER, DRAGON, 0.5);

        setEffectiveness(GRASS, GROUND, 2.0);
        setEffectiveness(GRASS, ROCK, 2.0);
        setEffectiveness(GRASS, WATER, 2.0);
        setEffectiveness(GRASS, FLYING, 0.5);
        setEffectiveness(GRASS, POISON, 0.5);
        setEffectiveness(GRASS, BUG, 0.5);
        setEffectiveness(GRASS, STEEL, 0.5);
        setEffectiveness(GRASS, FIRE, 0.5);
        setEffectiveness(GRASS, GRASS, 0.5);
        setEffectiveness(GRASS, DRAGON, 0.5);

        setEffectiveness(ELECTRIC, FLYING, 2.0);
        setEffectiveness(ELECTRIC, WATER, 2.0);
        setEffectiveness(ELECTRIC, GRASS, 0.5);
        setEffectiveness(ELECTRIC, ELECTRIC, 0.5);
        setEffectiveness(ELECTRIC, DRAGON, 0.5);
        setEffectiveness(ELECTRIC, GROUND, 0.0);

        setEffectiveness(PSYCHIC, FIGHTING, 2.0);
        setEffectiveness(PSYCHIC, POISON, 2.0);
        setEffectiveness(PSYCHIC, STEEL, 0.5);
        setEffectiveness(PSYCHIC, PSYCHIC, 0.5);
        setEffectiveness(PSYCHIC, DARK, 0.0);

        setEffectiveness(ICE, FLYING, 2.0);
        setEffectiveness(ICE, GROUND, 2.0);
        setEffectiveness(ICE, GRASS, 2.0);
        setEffectiveness(ICE, DRAGON, 2.0);
        setEffectiveness(ICE, STEEL, 0.5);
        setEffectiveness(ICE, FIRE, 0.5);
        setEffectiveness(ICE, WATER, 0.5);
        setEffectiveness(ICE, ICE, 0.5);

        setEffectiveness(DRAGON, DRAGON, 2.0);
        setEffectiveness(DRAGON, STEEL, 0.5);
        setEffectiveness(DRAGON, FAIRY, 0.0);

        setEffectiveness(DARK, GHOST, 2.0);
        setEffectiveness(DARK, PSYCHIC, 2.0);
        setEffectiveness(DARK, FIGHTING, 0.5);
        setEffectiveness(DARK, DARK, 0.5);
        setEffectiveness(DARK, FAIRY, 0.5);

        setEffectiveness(FAIRY, FIGHTING, 2.0);
        setEffectiveness(FAIRY, DRAGON, 2.0);
        setEffectiveness(FAIRY, DARK, 2.0);
        setEffectiveness(FAIRY, POISON, 0.5);
        setEffectiveness(FAIRY, STEEL, 0.5);
        setEffectiveness(FAIRY, FIRE, 0.5);
    }
}
