package industries.lemon.pokeinfo.ui.components;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class IconLabeledField extends VerticalLayout {

    private final Span label;
    private final Span valueSpan;

    public IconLabeledField(String labelText, String iconName, String initialValue, int iconSize) {
        setSpacing(false);
        setPadding(false);

        label = new Span(labelText);
        label.getElement().getThemeList().add("label");
        label.getStyle()
                .set("font-size", "var(--lumo-font-size-s)")
                .set("color", "var(--lumo-secondary-text-color)");

        CustomIcon icon = new CustomIcon(iconName, iconSize);

        valueSpan = new Span(initialValue);
        valueSpan.getStyle()
                .set("font-size", "1em")
                .set("color", "var(--lumo-body-text-color)");

        HorizontalLayout fieldContainer = new HorizontalLayout(icon, valueSpan);
        fieldContainer.setAlignItems(FlexComponent.Alignment.CENTER);
        fieldContainer.setSpacing(true);
        fieldContainer.getStyle()
                .set("border", "1px solid var(--lumo-contrast-20pct)")
                .set("border-radius", "var(--lumo-border-radius-m)")
                .set("padding", "var(--lumo-space-xs) var(--lumo-space-s)")
                .set("min-height", "var(--lumo-size-m)")
                .set("background-color", "var(--lumo-contrast-5pct)")
                .setWidth("100%");

        add(label, fieldContainer);
    }

    public void setValue(String value) {
        valueSpan.setText(value);
    }

    public String getValue() {
        return valueSpan.getText();
    }

    public void setLabel(String labelText) {
        label.setText(labelText);
    }
}
