package industries.lemon.pokeinfo.ui.components;

import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.server.StreamResource;

public class CustomIcon extends SvgIcon {

    public CustomIcon(String iconName, int size) {
        super();

        String fileName = iconName + ".svg";
        StreamResource iconResource = new StreamResource(fileName,
                () -> getClass().getResourceAsStream("/icons/" + fileName));
        setSrc(iconResource);

        getStyle().set("width", size + "px")
                .set("height", size + "px");
    }
}
