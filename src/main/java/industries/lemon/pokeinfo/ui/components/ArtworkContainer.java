package industries.lemon.pokeinfo.ui.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;

public class ArtworkContainer extends Div {
    private final Image image;

    public ArtworkContainer() {
        getStyle()
                .set("border-radius", "var(--lumo-border-radius-l)")
                .set("background-color", "var(--lumo-contrast-5pct)")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.2)");

        this.image = new Image();
        image.setVisible(false);
        image.setWidth("auto");
        image.setHeight("auto");
        image.setMaxWidth("400px");

        add(image);
    }

    public void update(String artworkUrl, String alt) {
        this.image.setSrc(artworkUrl);
        this.image.setAlt(alt);
        image.setVisible(true);
    }
}
