package industries.lemon.pokeinfo.ui.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;

public class EffectivenessIcon extends Div {
    public EffectivenessIcon(Double effectiveness, int size) {
        Span text = new Span(getEffectivenessText(effectiveness));
        text.getStyle()
                .set("color", "white")
                .set("font-weight", "bold")
                .set("font-size", Math.max(size / 2, 10) + "px")
                .set("line-height", "1");

        getStyle()
                .set("height", size + "px")
                .set("border-radius", "50%")
                .set("background-color", getEffectivenessColor(effectiveness))
                .set("display", "flex")
                .set("align-items", "center")
                .set("justify-content", "center");

        setWidthFull();
        setMaxWidth(size+"px");
        add(text);
    }

    // Color palette: https://lospec.com/palette-list/mulfok32
    private static String getEffectivenessColor(Double effectiveness) {
        if (effectiveness == 4.0) return "#5ba675";
        if (effectiveness == 2.0) return "#6bc96c";
        if (effectiveness == 1.0) return "#a6859f";
        if (effectiveness == 0.5) return "#ea6262";
        if (effectiveness == 0.25) return "#a32858";
        if (effectiveness == 0.0) return "#751756";
        return "#a6859f";
    }

    private static String getEffectivenessText(Double effectiveness) {
        if (effectiveness == 4.0) return "4×";
        if (effectiveness == 2.0) return "2×";
        if (effectiveness == 1.0) return "1×";
        if (effectiveness == 0.5) return "½×";
        if (effectiveness == 0.25) return "¼×";
        if (effectiveness == 0.0) return "0×";
        return String.format("%.1fx", effectiveness);
    }
}
