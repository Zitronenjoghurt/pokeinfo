package industries.lemon.pokeinfo.interfaces;

import java.util.Set;
import java.util.stream.Collectors;

public interface HasLanguage {
    String getLanguage();

    static <T extends HasLanguage> Set<T> filterByLanguage(Set<T> entities, String targetLanguage) {
        return entities.stream()
                .filter(entity -> entity.getLanguage().equals(targetLanguage))
                .collect(Collectors.toSet());
    }
}
