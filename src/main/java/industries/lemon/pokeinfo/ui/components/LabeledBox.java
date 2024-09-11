package industries.lemon.pokeinfo.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class LabeledBox extends VerticalLayout {
    private final Span label;
    private final FlexLayout contentLayout;

    public LabeledBox(String labelText) {
        setSpacing(false);
        setPadding(false);
        setWidth("auto");
        setHeight("auto");
        setAlignItems(Alignment.CENTER);

        this.label = new Span(labelText);
        this.contentLayout = new FlexLayout();

        label.getStyle()
                .set("font-weight", "bold")
                .set("margin-bottom", "5px");

        Div boxContainer = new Div();
        //boxContainer.getStyle()
        //        .set("border-radius", "var(--lumo-border-radius-l)")
        //        .set("background-color", "var(--lumo-contrast-5pct)")
        //        .set("box-shadow", "0 4px 8px rgba(0,0,0,0.2)");

        contentLayout.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        contentLayout.setJustifyContentMode(JustifyContentMode.START);
        contentLayout.setAlignItems(Alignment.START);
        contentLayout.getStyle().set("gap", "5px");

        boxContainer.add(contentLayout);

        add(label, boxContainer);

    }

    public void addContent(Component... components) {
        contentLayout.add(components);
    }

    public void clearContent() {
        contentLayout.removeAll();
    }

    public void setLabelText(String text) {
        label.setText(text);
    }
}
