package industries.lemon.pokeinfo.ui.components;

import com.vaadin.flow.component.orderedlayout.FlexLayout;
import industries.lemon.pokeinfo.entities.PokemonSprites;

public class SpritesContainer extends FlexLayout {

    public SpritesContainer() {
        getStyle()
                .set("display", "grid")
                .set("gap", "10px")
                .set("grid-template-columns", "1fr 1fr 1fr 1fr");
    }

    public void update(PokemonSprites pokemonSprites) {
        removeAll();

        ArtworkContainer frontDefault = new ArtworkContainer(100);
        ArtworkContainer backDefault = new ArtworkContainer(100);
        ArtworkContainer frontShiny = new ArtworkContainer(100);
        ArtworkContainer backShiny= new ArtworkContainer(100);

        add(frontDefault, backDefault, frontShiny, backShiny);

        if (pokemonSprites.getFrontFemale() != null) {
            ArtworkContainer femaleFrontDefault = new ArtworkContainer(100);
            ArtworkContainer femaleBackDefault = new ArtworkContainer(100);
            ArtworkContainer femaleFrontShiny = new ArtworkContainer(100);
            ArtworkContainer femaleBackShiny= new ArtworkContainer(100);

            frontDefault.update(pokemonSprites.getFrontDefault(), "male front default sprite");
            backDefault.update(pokemonSprites.getBackDefault(), "male back default sprite");
            frontShiny.update(pokemonSprites.getFrontShiny(), "male front shiny sprite");
            backShiny.update(pokemonSprites.getBackShiny(), "male back shiny sprite");
            femaleFrontDefault.update(pokemonSprites.getFrontFemale(), "female front default sprite");
            femaleBackDefault.update(pokemonSprites.getBackFemale(), "female back default sprite");
            femaleFrontShiny.update(pokemonSprites.getFrontShinyFemale(), "female front shiny sprite");
            femaleBackShiny.update(pokemonSprites.getBackShinyFemale(), "female back shiny sprite");

            add(femaleFrontDefault, femaleBackDefault, femaleFrontShiny, femaleBackShiny);
        } else {
            frontDefault.update(pokemonSprites.getFrontDefault(), "front default sprite");
            backDefault.update(pokemonSprites.getBackDefault(), "back default sprite");
            frontShiny.update(pokemonSprites.getFrontShiny(), "front shiny sprite");
            backShiny.update(pokemonSprites.getBackShiny(), "back shiny sprite");
        }
    }
}
