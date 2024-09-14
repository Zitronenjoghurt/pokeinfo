package industries.lemon.pokeinfo.ui.components;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import industries.lemon.pokeinfo.entities.Ability;
import industries.lemon.pokeinfo.entities.Pokemon;
import industries.lemon.pokeinfo.services.PokemonService;

import java.util.Set;

public class AbilityContainer extends VerticalLayout {
    private final PokemonService pokemonService;

    public AbilityContainer(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    public void update(Ability ability) {
        Set<Pokemon> pokemon = pokemonService.fetchMultiple(ability.getPokemonReferenceIds()).block();
    }
}
