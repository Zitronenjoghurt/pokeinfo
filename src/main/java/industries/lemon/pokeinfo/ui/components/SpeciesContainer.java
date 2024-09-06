package industries.lemon.pokeinfo.ui.components;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import industries.lemon.pokeinfo.entities.Pokemon;
import industries.lemon.pokeinfo.entities.PokemonSpecies;

import java.util.Optional;

public class SpeciesContainer extends VerticalLayout {
    private final ArtworkContainer artwork;
    private final PokemonContainer defaultContainer;
    private final IconLabeledField captureRateField;
    private final IconLabeledField baseHappinessField;
    private final IconLabeledField growthRateField;

    public SpeciesContainer() {
        getStyle()
                .set("border-radius", "var(--lumo-border-radius-l)")
                .set("background-color", "var(--lumo-contrast-5pct)")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.2)");

        //setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setWidth("auto");
        setHeight("auto");

        this.artwork = new ArtworkContainer();
        this.captureRateField = new IconLabeledField("Catch Rate", "pokeball", "", 18);
        this.baseHappinessField = new IconLabeledField("Base Happiness", "happy", "", 18);
        this.growthRateField = new IconLabeledField("Growth Rate", "growth", "", 18);
        this.defaultContainer = new PokemonContainer();

        VerticalLayout basicInfo = new VerticalLayout(captureRateField, baseHappinessField, growthRateField);
        HorizontalLayout topLine = new HorizontalLayout(artwork, basicInfo);
        add(topLine, defaultContainer);
    }

    public void update(PokemonSpecies species) {
        String artworkUrl = species.getOfficialArtworkUrl(false);
        String alt = String.format("The official artwork of %s", species.getName());
        this.artwork.update(artworkUrl, alt);
        this.captureRateField.setValue(String.valueOf(species.getCaptureRate()));
        this.baseHappinessField.setValue(String.valueOf(species.getBaseHappiness()));
        this.growthRateField.setValue(species.getGrowthRate().getName());

        Optional<Pokemon> defaultPokemon = species.getDefaultVariant();
        defaultPokemon.ifPresentOrElse(
                pokemon -> {
                    this.defaultContainer.update(pokemon);
                    this.defaultContainer.show();
                },
                this.defaultContainer::hide
        );
    }
}
