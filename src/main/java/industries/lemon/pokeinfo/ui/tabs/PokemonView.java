package industries.lemon.pokeinfo.ui.tabs;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import industries.lemon.pokeinfo.services.PokemonNameService;
import industries.lemon.pokeinfo.services.PokemonSpeciesService;

import java.util.List;
import java.util.Map;

public class PokemonView extends VerticalLayout {
    private final PokemonNameService pokemonNameService;
    private final PokemonSpeciesService pokemonSpeciesService;
    private final ComboBox<Integer> languageSelector;
    private final ComboBox<String> searchBar;

    private static final Map<Integer, String> LANGUAGE_FLAGS = Map.of(
            5, "🇫🇷",  // French
            6, "🇩🇪",  // German
            7, "🇪🇸",  // Spanish
            8, "🇮🇹",  // Italian
            9, "🇺🇸"       // English
    );

    public PokemonView(
            PokemonNameService pokemonNameService,
            PokemonSpeciesService pokemonSpeciesService
    ) {
        this.pokemonNameService = pokemonNameService;
        this.pokemonSpeciesService = pokemonSpeciesService;

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setAlignItems(Alignment.CENTER);

        this.searchBar = createSearchBar();
        this.languageSelector = createLanguageSelector();

        languageSelector.addValueChangeListener(event -> {
            updateSearchBar(event.getValue());
        });

        languageSelector.setValue(9);
        updateSearchBar(9);

        HorizontalLayout searchLayout = new HorizontalLayout(languageSelector, searchBar);
        searchLayout.setAlignItems(Alignment.BASELINE);
        searchLayout.setSpacing(true);

        add(searchLayout);
    }

    private void search() {
        String speciesName = searchBar.getValue();
        int languageId = languageSelector.getValue();
        int speciesId = pokemonNameService.getSpeciesIdByName(speciesName, languageId);
        if (speciesId == -1) {
            return;
        }

        pokemonSpeciesService.fetch(speciesId).subscribe(
                species -> {
                    // Visualize pokemon species
                },
                error -> {}
        );
    }

    private ComboBox<String> createSearchBar() {
        ComboBox<String> searchBar = new ComboBox<>("Pokemon");
        searchBar.setAllowCustomValue(true);
        searchBar.addValueChangeListener(event -> {
            search();
        });
        searchBar.addCustomValueSetListener(event -> {
            search();
        });
        return searchBar;
    }

    private void updateSearchBar(int languageId) {
        List<String> searchResults = pokemonNameService.getSpeciesNames(languageId);
        searchBar.setItems(searchResults);
    }

    private ComboBox<Integer> createLanguageSelector() {
        ComboBox<Integer> languageSelector = new ComboBox<>();
        languageSelector.setItems(LANGUAGE_FLAGS.keySet());
        languageSelector.setItemLabelGenerator(LANGUAGE_FLAGS::get);
        languageSelector.setWidth("80px");
        languageSelector.getElement().setAttribute("aria-label", "Select Language");
        return languageSelector;
    }
}
