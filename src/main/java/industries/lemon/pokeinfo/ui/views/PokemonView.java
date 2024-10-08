package industries.lemon.pokeinfo.ui.views;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import industries.lemon.pokeinfo.Constants;
import industries.lemon.pokeinfo.services.AbilityService;
import industries.lemon.pokeinfo.services.PageStateService;
import industries.lemon.pokeinfo.services.PokemonNameService;
import industries.lemon.pokeinfo.services.PokemonSpeciesService;
import industries.lemon.pokeinfo.ui.MainLayout;
import industries.lemon.pokeinfo.ui.components.LanguageSelector;
import industries.lemon.pokeinfo.ui.components.SpeciesContainer;

import java.util.List;

@Route(value = "pokemon", layout = MainLayout.class)
@PageTitle("Pokemon | Pokedata")
@AnonymousAllowed
public class PokemonView extends VerticalLayout implements HasUrlParameter<String> {
    private final PageStateService pageStateService;
    private final PokemonNameService pokemonNameService;
    private final PokemonSpeciesService pokemonSpeciesService;
    private final LanguageSelector languageSelector;
    private final ComboBox<String> nameSearchBar;
    private final IntegerField dexNumberSearchBar;
    private final SpeciesContainer speciesContainer;

    private boolean initialized = false;
    private boolean loading = false;

    public PokemonView(
            AbilityService abilityService,
            PageStateService pageStateService,
            PokemonNameService pokemonNameService,
            PokemonSpeciesService pokemonSpeciesService
    ) {
        this.pageStateService = pageStateService;
        this.pokemonNameService = pokemonNameService;
        this.pokemonSpeciesService = pokemonSpeciesService;

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setAlignItems(Alignment.CENTER);

        this.nameSearchBar = createNameSearchBar();
        this.dexNumberSearchBar = createDexNumberSearchBar();
        this.languageSelector = createLanguageSelector();

        FlexLayout searchLayout = new FlexLayout(languageSelector, nameSearchBar, dexNumberSearchBar);
        searchLayout.setFlexDirection(FlexLayout.FlexDirection.ROW);
        searchLayout.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        searchLayout.setAlignItems(Alignment.BASELINE);
        searchLayout.getStyle().set("gap", "10px");

        Checkbox shinyButton = createShinyButton();

        VerticalLayout topBar = new VerticalLayout(searchLayout, shinyButton);
        topBar.setAlignItems(Alignment.CENTER);

        this.speciesContainer = new SpeciesContainer(abilityService, pageStateService);
        speciesContainer.setVisible(false);

        languageSelector.setValue(9);
        shinyButton.setValue(pageStateService.isShinyEnabled());

        add(topBar, speciesContainer);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String nationalDex) {
        int speciesId = parseSpeciesId(nationalDex);

        // Ensures it only searches when the UI is ready
        if (initialized) {
            search(speciesId);
        } else {
            // If UI wasn't initialized yet the onAttach will handle the search
            pageStateService.setCurrentSpeciesId(speciesId);
        }
    }

    @Override
    public void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        search(pageStateService.getCurrentSpeciesId());
    }

    private void searchName() {
        if (isLoading()) {
            return;
        }

        String speciesName = nameSearchBar.getValue();
        if (speciesName == null) {
            return;
        }

        int languageId = languageSelector.getValue();
        int speciesId = pokemonNameService.getSpeciesIdByName(speciesName, languageId);
        if (speciesId < 1) {
            return;
        }

        UI.getCurrent().navigate(PokemonView.class, String.valueOf(speciesId));
    }

    private void searchDexNumber() {
        if (isLoading()) {
            return;
        }

        int speciesId = dexNumberSearchBar.getValue();
        UI.getCurrent().navigate(PokemonView.class, String.valueOf(speciesId));
    }

    public void search(int speciesId) {
        boolean speciesIdOutOfBounds = speciesId < 1 || speciesId > Constants.MAX_SPECIES_ID;
        boolean redundantSearch = speciesId == pageStateService.getCurrentSpeciesId() && initialized;
        if (isLoading() || speciesIdOutOfBounds || redundantSearch ) {
            return;
        }

        setIsLoading(true);
        dexNumberSearchBar.setValue(speciesId);
        nameSearchBar.setValue(getSpeciesNameById(speciesId));

        pokemonSpeciesService.fetch(speciesId).doOnNext(
                species -> getUI().ifPresent(ui -> ui.access(() -> {
                    this.speciesContainer.update(species);
                    this.speciesContainer.setVisible(true);
                    ui.push();
                    pageStateService.setCurrentSpeciesId(speciesId);
                    initialized = true;
                    setIsLoading(false);
                }))
        ).doOnError(e -> {
            initialized = true;
            setIsLoading(false);
        }).subscribe();
    }

    private void changeShinyState(boolean value) {
        pageStateService.setShinyEnabled(value);
        speciesContainer.onToggleShiny();
    }

    private Checkbox createShinyButton() {
        Checkbox shinyButton = new Checkbox();
        shinyButton.setLabel("Shiny?");
        shinyButton.addValueChangeListener(e -> changeShinyState(e.getValue()));
        return shinyButton;
    }

    private ComboBox<String> createNameSearchBar() {
        ComboBox<String> searchBar = new ComboBox<>("Name");
        searchBar.addValueChangeListener(e -> searchName());
        return searchBar;
    }

    private IntegerField createDexNumberSearchBar() {
        IntegerField searchBar = new IntegerField("National Dex");
        searchBar.setHelperText("Up to " + Constants.MAX_SPECIES_ID);
        searchBar.setMin(1);
        searchBar.setMax(Constants.MAX_SPECIES_ID);
        searchBar.setStepButtonsVisible(true);
        searchBar.addValueChangeListener(e -> searchDexNumber());
        return searchBar;
    }

    private void updateSearchBar(int languageId) {
        List<String> searchResults = pokemonNameService.getSpeciesNames(languageId);
        nameSearchBar.setItems(searchResults);
        nameSearchBar.getDataProvider().refreshAll();
    }

    private LanguageSelector createLanguageSelector() {
        LanguageSelector languageSelector = new LanguageSelector();
        languageSelector.addValueChangeListener(event -> {
            updateSearchBar(event.getValue());
            if (initialized) {
                nameSearchBar.setValue(getSpeciesNameById(pageStateService.getCurrentSpeciesId()));
            }
        });
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

    private String getSpeciesNameById(int speciesId) {
        int languageId = languageSelector.getValue();
        return pokemonNameService.getNameByIdAndLanguage(speciesId, languageId);
    }

    private int parseSpeciesId(String idString) {
        try {
            int id = Integer.parseInt(idString);
            return (id > 0 && id <= Constants.MAX_SPECIES_ID) ? id : 1;
        } catch (NumberFormatException e) {
            return pageStateService.getCurrentSpeciesId();
        }
    }
}
