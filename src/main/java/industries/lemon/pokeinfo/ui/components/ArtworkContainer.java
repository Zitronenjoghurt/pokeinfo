package industries.lemon.pokeinfo.ui.components;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class ArtworkContainer extends HorizontalLayout {
    private Image image;

    public ArtworkContainer() {
        getStyle()
                .set("border-radius", "var(--lumo-border-radius-l)")
                .set("background-color", "var(--lumo-contrast-5pct)")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.2)");

        this.image = new Image();
        image.setVisible(false);
        image.setWidth("100%");
        image.setHeight("auto");

        setWidth("100%");
        setHeight("auto");
        setMaxWidth("300px");

        add(image);
    }

    public void update(String artworkUrl, String alt) {
        this.image.setSrc(artworkUrl);
        this.image.setAlt(alt);
        image.setVisible(true);
    }
}
