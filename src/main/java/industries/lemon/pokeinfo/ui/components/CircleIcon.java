package industries.lemon.pokeinfo.ui.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.server.StreamResource;

import java.io.InputStream;

public class CircleIcon extends FlexLayout {
    private final Div circle;
    private final Image icon;

    public CircleIcon(
            String iconName,
            String backgroundColor,
            int size
    ) {
        circle = new Div();
        icon = new Image(createResource(iconName), "icon");

        setFlexWrap(FlexWrap.WRAP);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setAlignItems(FlexComponent.Alignment.CENTER);

        circle.getStyle()
                .set("width", size + "px")
                .set("height", size + "px")
                .set("border-radius", "50%")
                .set("background-color", backgroundColor)
                .set("display", "flex")
                .set("align-items", "center")
                .set("justify-content", "center");

        icon.getStyle()
                .set("width", "60%")
                .set("height", "60%")
                .set("object-fit", "contain");

        circle.add(icon);
        add(circle);
    }

    private StreamResource createResource(String iconName) {
        return new StreamResource(iconName, () -> {
            InputStream inputStream = getClass().getResourceAsStream("/static/icons/" + iconName);
            if (inputStream == null) {
                throw new RuntimeException("Icon not found: " + iconName);
            }
            return inputStream;
        });
    }
}
