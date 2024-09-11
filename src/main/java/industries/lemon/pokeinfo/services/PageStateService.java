package industries.lemon.pokeinfo.services;

import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import industries.lemon.pokeinfo.enums.PokemonTyping;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
@VaadinSessionScope
public class PageStateService {
    private int currentSpeciesId = 1;
    private boolean isShinyEnabled = false;
    private PokemonTyping primaryType = null;
    private PokemonTyping secondaryType = null;

    public PokemonTyping getType(boolean isPrimary) {
        if (isPrimary) {
            return getPrimaryType();
        } else {
            return getSecondaryType();
        }
    }
}
