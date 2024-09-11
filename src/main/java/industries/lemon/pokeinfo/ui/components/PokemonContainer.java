package industries.lemon.pokeinfo.ui.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import industries.lemon.pokeinfo.entities.Pokemon;
import industries.lemon.pokeinfo.entities.PokemonType;
import industries.lemon.pokeinfo.enums.PokemonTyping;
import industries.lemon.pokeinfo.ui.views.TypeView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class PokemonContainer extends VerticalLayout {
    private final HorizontalLayout typeLabels;
    private final PokemonStatsContainer statsContainer;
    private final List<PokemonTyping> currentTypes = new ArrayList<>();

    private static int TYPE_ICON_SIZE = 30;
    private static int TYPE_ICON_FONT_SIZE = 20;
    private static int TYPE_ICON_WIDTH = 140;

    public PokemonContainer() {
        setAlignItems(Alignment.CENTER);

        getStyle()
                .set("border-radius", "var(--lumo-border-radius-l)");
                //.set("background-color", "var(--lumo-contrast-5pct)")
                //.set("box-shadow", "0 4px 8px rgba(0,0,0,0.2)");

        this.typeLabels = new HorizontalLayout();
        Button typeMatchupButton = new Button(new Icon(VaadinIcon.OPEN_BOOK));
        typeMatchupButton.addClickListener(e -> onTypeMatchupClicked());
        typeMatchupButton.getStyle().set("box-shadow", "0 4px 8px rgba(0,0,0,0.2)");
        HorizontalLayout statsBar = new HorizontalLayout(typeLabels, typeMatchupButton);

        this.statsContainer = new PokemonStatsContainer();

        add(statsBar, statsContainer);
    }

    public void update(Pokemon pokemon) {
        this.typeLabels.removeAll();
        addTypes(pokemon.getTypes());
        this.statsContainer.update(pokemon);
    }

    private void addTypes(Set<PokemonType> types) {
        currentTypes.clear();
        List<PokemonType> sortedTypes = new ArrayList<>(types);
        sortedTypes.sort(Comparator.comparingInt(PokemonType::getSlot));

        for (PokemonType type : sortedTypes) {
            PokemonTyping typing = PokemonTyping.fromName(type.getType());
            typeLabels.add(new PokemonTypeLabel(typing, TYPE_ICON_SIZE, TYPE_ICON_FONT_SIZE, TYPE_ICON_WIDTH, null));
            currentTypes.add(typing);
        }

        if (sortedTypes.size() == 1) {
            typeLabels.add(new PokemonTypeLabel(null, TYPE_ICON_SIZE, TYPE_ICON_FONT_SIZE, TYPE_ICON_WIDTH, null));
        }
    }

    private void onTypeMatchupClicked() {
        PokemonTyping primary = !currentTypes.isEmpty() ? currentTypes.getFirst() : null;
        PokemonTyping secondary = currentTypes.size() > 1 ? currentTypes.getLast() : null;
        UI.getCurrent().navigate(TypeView.class, TypeView.createQueryParameters(primary, secondary));
    }

    public void show() {
        setVisible(true);
    }

    public void hide() {
        setVisible(false);
    }
}
