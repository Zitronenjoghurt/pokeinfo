package industries.lemon.pokeinfo.ui.components;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import industries.lemon.pokeinfo.interfaces.IFlavorText;

import java.util.Set;

public class FlavorTextContainer extends HorizontalLayout {
    private final TextCarousel textCarousel;

    public FlavorTextContainer() {
        this.textCarousel = new TextCarousel();
        getStyle()
                .set("border-radius", "var(--lumo-border-radius-l)")
                .set("background-color", "var(--lumo-contrast-5pct)")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.2)")
                .set("max-width", "350px")
                .set("text-align", "center");
        setHeightFull();

        add(textCarousel);
    }

    public void update(Set<? extends IFlavorText> flavorTexts) {
        textCarousel.resetItems();
        if (flavorTexts == null || flavorTexts.isEmpty()) {
            textCarousel.setVisible(false);
            return;
        }
        textCarousel.setVisible(true);

        for (IFlavorText flavorText : flavorTexts) {
            textCarousel.addItem(flavorText.getVersion(), flavorText.getText());
        }
    }
}
