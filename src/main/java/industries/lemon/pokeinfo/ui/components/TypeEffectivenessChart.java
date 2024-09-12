package industries.lemon.pokeinfo.ui.components;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import industries.lemon.pokeinfo.enums.PokemonTyping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class TypeEffectivenessChart extends VerticalLayout {
    private final H3 title;
    private final LabeledBox superEffectiveBox;
    private final LabeledBox notEffectiveBox;
    private final LabeledBox regularBox;

    public TypeEffectivenessChart() {
        setJustifyContentMode(JustifyContentMode.START);
        setAlignItems(Alignment.CENTER);
        setHeight("auto");
        setWidth("auto");

        getStyle()
                .set("border-radius", "var(--lumo-border-radius-l)")
                .set("background-color", "var(--lumo-contrast-5pct)")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.2)");

        title = new H3();
        title.getStyle().set("font-weight", "bold");

        superEffectiveBox = createLabeledBox("Super Effective");
        notEffectiveBox = createLabeledBox("Not Effective");
        regularBox = createLabeledBox("Regular");

        add(title, superEffectiveBox, notEffectiveBox, regularBox);
    }

    public void update(Map<PokemonTyping, Double> map) {
        superEffectiveBox.clearContent();
        notEffectiveBox.clearContent();
        regularBox.clearContent();

        List<Map.Entry<PokemonTyping, Double>> sortedEntries = new ArrayList<>(map.entrySet());
        sortedEntries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        for (Map.Entry<PokemonTyping, Double> entry : sortedEntries) {
            PokemonTyping type = entry.getKey();
            Double effectiveness = entry.getValue();

            PokemonTypeLabel typeLabel = createTypeLabel(type, effectiveness);
            if (effectiveness > 1.0) {
                superEffectiveBox.addContent(typeLabel);
            } else if (effectiveness < 1.0) {
                notEffectiveBox.addContent(typeLabel);
            } else {
                regularBox.addContent(typeLabel);
            }
        }
    }

    private PokemonTypeLabel createTypeLabel(PokemonTyping type, Double effectiveness) {
        return new PokemonTypeLabel(type, 30, 16, 150, effectiveness);
    }

    private LabeledBox createLabeledBox(String labelText) {
        LabeledBox box = new LabeledBox(labelText);
        box.getStyle().setMaxWidth("500px");
        return box;
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }
}
