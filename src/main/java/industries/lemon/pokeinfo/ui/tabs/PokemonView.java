package industries.lemon.pokeinfo.ui.tabs;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import industries.lemon.pokeinfo.entities.Pokemon;
import industries.lemon.pokeinfo.services.PokemonService;

public class PokemonView extends VerticalLayout /*implements HasUrlParameter<Integer>*/ {
    private final PokemonService pokemonService;
    private final H1 title;

    public PokemonView(PokemonService pokemonService) {
        this.pokemonService = pokemonService;

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        title = new H1("Loading Pokemon...");
        add(title);
    }

    //@Override
    //public void setParameter(BeforeEvent event, Integer parameter) {
    //    if (parameter == null) {
    //        return;
    //    }
    //
    //    UI ui = UI.getCurrent();
    //    pokemonService.fetch(parameter).subscribe(
    //            pokemon -> ui.access(() -> displayPokemon(pokemon)),
    //            error -> ui.access(() -> handleError(error))
    //    );
    //}

    private void displayPokemon(Pokemon pokemon) {
        title.setText(pokemon.getName());
    }

    private void handleError(Throwable error) {
        title.setText(error.getMessage());
    }
}
