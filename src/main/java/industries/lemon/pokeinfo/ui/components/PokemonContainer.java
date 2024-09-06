package industries.lemon.pokeinfo.ui.components;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import industries.lemon.pokeinfo.entities.Pokemon;
import industries.lemon.pokeinfo.entities.PokemonType;
import industries.lemon.pokeinfo.enums.PokemonTyping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class PokemonContainer extends VerticalLayout {
    private final HorizontalLayout typeLabels;
    private final PokemonStatsContainer statsContainer;

    public PokemonContainer() {
        setAlignItems(Alignment.CENTER);

        getStyle()
                .set("border-radius", "var(--lumo-border-radius-l)");
                //.set("background-color", "var(--lumo-contrast-5pct)")
                //.set("box-shadow", "0 4px 8px rgba(0,0,0,0.2)");

        this.typeLabels = new HorizontalLayout();
        this.statsContainer = new PokemonStatsContainer();

        add(typeLabels, statsContainer);
    }

    public void update(Pokemon pokemon) {
        this.typeLabels.removeAll();
        addTypes(pokemon.getTypes());
        this.statsContainer.update(pokemon);
    }

    private void addTypes(Set<PokemonType> types) {
        List<PokemonType> sortedTypes = new ArrayList<>(types);
        sortedTypes.sort(Comparator.comparingInt(PokemonType::getSlot));

        for (PokemonType type : sortedTypes) {
            PokemonTyping typing = PokemonTyping.fromName(type.getType());
            typeLabels.add(new PokemonTypeLabel(typing));
        }

        if (sortedTypes.size() == 1) {
            typeLabels.add(new PokemonTypeLabel(null));
        }
    }

    public void show() {
        setVisible(true);
    }

    public void hide() {
        setVisible(false);
    }
}
