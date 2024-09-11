package industries.lemon.pokeinfo.ui.components;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import industries.lemon.pokeinfo.enums.PokemonTyping;

public class PokemonTypeLabel extends HorizontalLayout {
    private final PokemonTyping type;

    public PokemonTypeLabel(
            PokemonTyping type,
            int iconSize,
            int fontSize,
            int width,
            Double effectiveness
    ) {
        this.type = type;

        setAlignItems(Alignment.CENTER);
        setPadding(true);
        setSpacing(false);

        getStyle().set("padding", "4px")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.2)")
                .set("border", "1px solid rgba(100,100,100,0.2)")
                .set("border-radius", "4px")
                .set("background-color", "var(--lumo-contrast-5pct)")
                .set("font-weight", "bold");

        setWidth(width+"px");

        if (type != null) {
            PokemonTypeIcon icon = PokemonTypeIcon.fromType(type, iconSize);
            Span typeText = new Span(type.getDisplayName());
            typeText.getStyle().set("font-size", fontSize+"px");

            HorizontalLayout textWrapper = new HorizontalLayout(typeText);
            textWrapper.setAlignItems(Alignment.CENTER);
            textWrapper.setJustifyContentMode(JustifyContentMode.CENTER);
            textWrapper.setSizeFull();
            textWrapper.setFlexGrow(1);

            add(icon, textWrapper);

            if (effectiveness != null) {
                EffectivenessIcon effectivenessIcon = new EffectivenessIcon(effectiveness, iconSize);
                add(effectivenessIcon);
            }
        }
    }

    public String getTypeName() {
        return type.getDisplayName();
    }
}
