package industries.lemon.pokeinfo.ui.components;

import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import industries.lemon.pokeinfo.entities.FlavorText;
import industries.lemon.pokeinfo.entities.Pokemon;
import industries.lemon.pokeinfo.entities.PokemonSpecies;
import industries.lemon.pokeinfo.services.PageStateService;
import industries.lemon.pokeinfo.interfaces.HasLanguage;

import java.util.Optional;
import java.util.Set;

public class SpeciesContainer extends VerticalLayout {
    private final PageStateService pageStateService;
    private final ArtworkContainer artwork;
    private final PokemonContainer defaultContainer;
    private final IconLabeledField captureRateField;
    private final IconLabeledField baseHappinessField;
    private final IconLabeledField growthRateField;
    private final IconLabeledField specialStateField;
    private final FlavorTextContainer flavorTextContainer;

    private PokemonSpecies currentSpecies;

    public SpeciesContainer(
            PageStateService pageStateService
    ) {
        this.pageStateService = pageStateService;

        getStyle()
                .set("border-radius", "var(--lumo-border-radius-l)")
                .set("background-color", "var(--lumo-contrast-5pct)")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.2)");

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setWidth("auto");
        setHeight("auto");

        this.captureRateField = new IconLabeledField("Catch Rate", "pokeball", "", 18);
        this.baseHappinessField = new IconLabeledField("Base Happiness", "happy", "", 18);
        this.growthRateField = new IconLabeledField("Growth Rate", "growth", "", 18);
        this.specialStateField = new IconLabeledField("Special State", "sparkles", "", 18);
        FlexLayout infoGrid = new FlexLayout();
        infoGrid.add(captureRateField, baseHappinessField, growthRateField, specialStateField);
        infoGrid.getStyle()
                .set("display", "grid")
                .set("grid-template-columns", "1fr 1fr")
                .set("gap", "10px");

        this.flavorTextContainer = new FlavorTextContainer();

        this.artwork = new ArtworkContainer(350);
        this.defaultContainer = new PokemonContainer();

        VerticalLayout basicInfo = new VerticalLayout(infoGrid, flavorTextContainer);
        basicInfo.setAlignItems(Alignment.CENTER);
        basicInfo.setPadding(false);
        basicInfo.setWidth("auto");

        FlexLayout topLine = new FlexLayout(artwork, basicInfo);
        topLine.setAlignItems(Alignment.CENTER);
        topLine.setJustifyContentMode(JustifyContentMode.EVENLY);
        topLine.setFlexDirection(FlexLayout.FlexDirection.ROW);
        topLine.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        topLine.getStyle().set("gap", "20px");
        topLine.setWidthFull();
        add(topLine, defaultContainer);
    }

    public void update(PokemonSpecies species) {
        currentSpecies = species;

        updateArtwork(species, pageStateService.isShinyEnabled());
        this.captureRateField.setValue(String.valueOf(species.getCaptureRate()));
        this.baseHappinessField.setValue(String.valueOf(species.getBaseHappiness()));
        this.growthRateField.setValue(species.getGrowthRate().getName());
        this.specialStateField.setValue(species.getSpecialStates());

        Set<FlavorText> flavorTexts = species.getFlavorTexts();
        Set<FlavorText> filteredFlavorTexts = HasLanguage.filterByLanguage(flavorTexts, "en");
        this.flavorTextContainer.update(filteredFlavorTexts);

        Optional<Pokemon> defaultPokemon = species.getDefaultVariant();
        defaultPokemon.ifPresentOrElse(
                pokemon -> {
                    this.defaultContainer.update(pokemon);
                    this.defaultContainer.show();
                },
                this.defaultContainer::hide
        );
    }

    public void updateArtwork(PokemonSpecies species, boolean shiny) {
        String artworkUrl = species.getOfficialArtworkUrl(shiny);
        String alt = String.format("The official artwork of %s", species.getName());
        this.artwork.update(artworkUrl, alt);
    }

    public void onToggleShiny() {
        if (currentSpecies != null) {
            updateArtwork(currentSpecies, pageStateService.isShinyEnabled());
        }
    }
}
