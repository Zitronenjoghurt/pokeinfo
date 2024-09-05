package industries.lemon.pokeinfo.ui.tabs;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import industries.lemon.pokeinfo.services.PokemonNameService;
import industries.lemon.pokeinfo.services.PokemonSpeciesService;
import industries.lemon.pokeinfo.ui.components.SpeciesContainer;

import java.util.List;
import java.util.Map;

public class PokemonView extends VerticalLayout {
    private final PokemonNameService pokemonNameService;
    private final PokemonSpeciesService pokemonSpeciesService;
    private final ComboBox<Integer> languageSelector;
    private final ComboBox<String> nameSearchBar;
    private final IntegerField dexNumberSearchBar;
    private SpeciesContainer speciesContainer;

    private boolean loading = false;

    private static final int MAX_POKEDEX_NUMBER = 1025;

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

        this.nameSearchBar = createNameSearchBar();
        this.dexNumberSearchBar = createDexNumberSearchBar();
        this.languageSelector = createLanguageSelector();

        languageSelector.addValueChangeListener(event -> {
            updateSearchBar(event.getValue());
        });

        languageSelector.setValue(9);
        updateSearchBar(9);

        FlexLayout searchLayout = new FlexLayout(languageSelector, nameSearchBar, dexNumberSearchBar);
        searchLayout.setFlexDirection(FlexLayout.FlexDirection.ROW);
        searchLayout.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        searchLayout.setAlignItems(Alignment.BASELINE);
        searchLayout.getStyle().set("gap", "10px");

        this.speciesContainer = new SpeciesContainer();
        speciesContainer.setVisible(false);

        add(searchLayout, speciesContainer);
    }

    private void searchName() {
        String speciesName = nameSearchBar.getValue();
        int languageId = languageSelector.getValue();
        int speciesId = pokemonNameService.getSpeciesIdByName(speciesName, languageId);
        if (speciesId == -1) {
            return;
        }
        search(speciesId);
    }

    private void searchDexNumber() {
        int speciesId = dexNumberSearchBar.getValue();
        search(speciesId);
    }

    private void search(int speciesId) {
        if (isLoading() || speciesId < 1 || speciesId > MAX_POKEDEX_NUMBER) {
            return;
        }

        setIsLoading(true);
        pokemonSpeciesService.fetch(speciesId).doOnNext(
                species -> {
                    getUI().ifPresent(ui -> ui.access(() -> {
                        this.speciesContainer.update(species);
                        this.speciesContainer.setVisible(true);
                        ui.push();
                        setIsLoading(false);
                    }));
                }
        ).doOnError(throwable -> {}).subscribe();

        int languageId = languageSelector.getValue();
        String speciesName = pokemonNameService.getSpeciesNames(languageId).get(speciesId - 1);
        dexNumberSearchBar.setValue(speciesId);
        nameSearchBar.setValue(speciesName);
    }

    private ComboBox<String> createNameSearchBar() {
        ComboBox<String> searchBar = new ComboBox<>("Name");
        searchBar.addValueChangeListener(e -> searchName());
        return searchBar;
    }

    private IntegerField createDexNumberSearchBar() {
        IntegerField searchBar = new IntegerField("National Dex");
        searchBar.setHelperText("Up to " + MAX_POKEDEX_NUMBER);
        searchBar.setMin(1);
        searchBar.setMax(MAX_POKEDEX_NUMBER);
        searchBar.setStepButtonsVisible(true);
        searchBar.addValueChangeListener(e -> searchDexNumber());
        return searchBar;
    }

    private void updateSearchBar(int languageId) {
        List<String> searchResults = pokemonNameService.getSpeciesNames(languageId);
        nameSearchBar.setItems(searchResults);
    }

    private ComboBox<Integer> createLanguageSelector() {
        ComboBox<Integer> languageSelector = new ComboBox<>();
        languageSelector.setItems(LANGUAGE_FLAGS.keySet());
        languageSelector.setItemLabelGenerator(LANGUAGE_FLAGS::get);
        languageSelector.setWidth("80px");
        languageSelector.getElement().setAttribute("aria-label", "Select Language");
        return languageSelector;
    }

    private boolean isLoading() {
        return loading;
    }

    private void setIsLoading(boolean isLoading) {
        this.loading = isLoading;
        if (isLoading) {
            onLoadingStart();
        } else {
            onLoadingFinished();
        }
    }

    private void onLoadingStart() {
        languageSelector.setEnabled(false);
        nameSearchBar.setEnabled(false);
        dexNumberSearchBar.setEnabled(false);
        speciesContainer.getElement().getStyle().set("opacity", "0.5");
        speciesContainer.getElement().getStyle().set("pointer-events", "none");
    }

    private void onLoadingFinished() {
        languageSelector.setEnabled(true);
        nameSearchBar.setEnabled(true);
        dexNumberSearchBar.setEnabled(true);
        speciesContainer.getElement().getStyle().set("opacity", "1");
        speciesContainer.getElement().getStyle().remove("pointer-events");
    }
}
