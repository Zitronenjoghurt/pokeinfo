package industries.lemon.pokeinfo.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum Language {
    FRENCH(5, "🇫🇷"),
    GERMAN(6, "🇩🇪"),
    SPANISH(7, "🇪🇸"),
    ITALIAN(8, "🇮🇹"),
    ENGLISH(9, "🇺🇸");

    private final int id;
    private final String flagEmoji;

    private static final Map<Integer, String> LANGUAGE_FLAGS = new HashMap<>();

    Language(int id, String flagEmoji) {
        this.id = id;
        this.flagEmoji = flagEmoji;
    }

    static {
        for (Language language : Language.values()) {
            LANGUAGE_FLAGS.put(language.getId(), language.getFlagEmoji());
        }
    }

    public static Map<Integer, String> getFlagMap() {
        return LANGUAGE_FLAGS;
    }
}
