package industries.lemon.pokeinfo.ui.components;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import industries.lemon.pokeinfo.enums.PokemonTyping;

public class PokemonTypeLabel extends HorizontalLayout {

    public PokemonTypeLabel(PokemonTyping type) {
        setAlignItems(Alignment.CENTER);
        setPadding(true);

        getStyle().set("padding", "4px")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.2)")
                .set("border", "1px solid rgba(100,100,100,0.2)")
                .set("border-radius", "4px")
                .set("background-color", "var(--lumo-contrast-5pct)")
                .set("font-weight", "bold");

        setWidth("140px");

        if (type != null) {
            PokemonTypeIcon icon = PokemonTypeIcon.fromType(type, 30);
            Span typeText = new Span(type.getDisplayName());
            typeText.getStyle().set("font-size", "16px");

            add(icon, typeText);
        }
    }
}
