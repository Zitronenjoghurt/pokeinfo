package industries.lemon.pokeinfo.ui.tabs;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import industries.lemon.pokeinfo.services.PokemonNameService;

import java.util.List;
import java.util.Map;

public class PokemonView extends VerticalLayout {
    private final PokemonNameService pokemonNameService;
    private final ComboBox<Integer> languageSelector;
    private final ComboBox<String> searchBar;

    private static final Map<Integer, String> LANGUAGE_FLAGS = Map.of(
            5, "🇫🇷",  // French
            6, "🇩🇪",  // German
            7, "🇪🇸",  // Spanish
            8, "🇮🇹",  // Italian
            9, "🇺🇸"       // English
    );

    public PokemonView(PokemonNameService pokemonNameService) {
        this.pokemonNameService = pokemonNameService;

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

    private ComboBox<String> createSearchBar() {
        ComboBox<String> searchBar = new ComboBox<>("Pokemon");
        searchBar.setAllowCustomValue(true);
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
