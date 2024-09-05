package industries.lemon.pokeinfo.ui.components;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import industries.lemon.pokeinfo.entities.PokemonSpecies;

public class SpeciesContainer extends VerticalLayout {
    private ArtworkContainer artwork;

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

        add(artwork);
    }

    public void update(PokemonSpecies species) {
        String artworkUrl = species.getOfficialArtworkUrl(false);
        String alt = String.format("The official artwork of %s", species.getName());
        this.artwork.update(artworkUrl, alt);
    }
}
