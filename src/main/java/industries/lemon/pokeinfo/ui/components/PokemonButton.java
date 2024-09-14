package industries.lemon.pokeinfo.ui.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import industries.lemon.pokeinfo.entities.Pokemon;
import industries.lemon.pokeinfo.enums.Language;
import industries.lemon.pokeinfo.services.PokemonNameService;
import industries.lemon.pokeinfo.ui.views.PokemonView;

public class PokemonButton extends Button {

    public PokemonButton(
            PokemonNameService nameService,
            Pokemon pokemon
    ) {
        String imageUrl = pokemon.getSprites().getFrontDefault();
        int speciesId = pokemon.getSpeciesReferenceId();
        String name = nameService.getNameByIdAndLanguage(speciesId, Language.ENGLISH.getId());

        if (imageUrl == null) {
            imageUrl = "";
        }

        Image pokemonImage = new Image(imageUrl, name);
        pokemonImage.setWidth("96px");
        pokemonImage.setHeight("96px");
        pokemonImage.getElement().setAttribute("loading", "lazy");

        Span nameSpan = new Span(name);

        VerticalLayout layout = new VerticalLayout(pokemonImage, nameSpan);
        layout.setSpacing(false);
        layout.setPadding(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        getElement().appendChild(layout.getElement());

        getStyle()
                .set("padding", "0px")
                .set("background-color", "var(--lumo-contrast-5pct)")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.2)");

        setHeight("120px");
        setWidth("auto");
        addClickListener(createClickListener(speciesId));
    }

    private ComponentEventListener<ClickEvent<Button>> createClickListener(int speciesId) {
        return event -> UI.getCurrent().navigate(PokemonView.class, String.valueOf(speciesId));
    }
}
