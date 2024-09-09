package industries.lemon.pokeinfo.ui.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;

public class ArtworkContainer extends Div {
    private final Image image;

    public ArtworkContainer() {
        getStyle()
                .set("border-radius", "var(--lumo-border-radius-l)")
                .set("background-color", "var(--lumo-contrast-5pct)")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.2)")
                .set("width", "100%")
                .set("max-width", "400px")
                .set("aspect-ratio", "1 / 1")
                .set("display", "flex")
                .set("align-items", "center")
                .set("justify-content", "center");

        this.image = new Image();
        image.getStyle()
                .set("max-width", "100%")
                .set("max-height", "100%")
                .set("object-fit", "contain");
        image.setVisible(false);

        add(image);
    }

    public void update(String artworkUrl, String alt) {
        this.image.setSrc(artworkUrl);
        this.image.setAlt(alt);
        image.setVisible(true);
    }
}
