package industries.lemon.pokeinfo.ui.components;

import com.vaadin.flow.component.shared.Tooltip;
import industries.lemon.pokeinfo.enums.PokemonTyping;

public class PokemonTypeIcon extends CircleIcon {

    private PokemonTypeIcon(
            String iconName,
            String backgroundColor,
            int size) {
        super(iconName, backgroundColor, size);
    }

    public static PokemonTypeIcon fromType(PokemonTyping type, int size) {
        String iconName = type.name().toLowerCase();
        String backgroundColor = type.getDisplayColor();
        PokemonTypeIcon icon = new PokemonTypeIcon(iconName, backgroundColor, size);

        Tooltip.forComponent(icon)
                .withText(type.getDisplayName())
                .withPosition(Tooltip.TooltipPosition.BOTTOM);

        return icon;
    }
}
