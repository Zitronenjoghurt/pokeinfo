package industries.lemon.pokeinfo.ui.components;

import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;

public class LabeledProgressBar extends VerticalLayout {
    private NativeLabel labelText;
    private Span labelValue;
    private ProgressBar bar;

    public LabeledProgressBar(double min, double max, String label, String color, int fontSize, int barHeight) {
        setSpacing(false);
        setPadding(false);
        setMargin(false);

        this.bar = new ProgressBar(min, max, 0);
        bar.getElement().getStyle().set("--lumo-primary-color", color);
        bar.setHeight(barHeight+"px");

        this.labelText = new NativeLabel(label);
        labelText.setId("pblabel");
        bar.getElement().setAttribute("aria-labelledby", "pblabel");

        this.labelValue = new Span("");
        HorizontalLayout barlabel = new HorizontalLayout(labelText, labelValue);
        barlabel.setWidthFull();
        barlabel.getStyle().set("font-size", fontSize+"px");
        barlabel.getStyle().set("font-weight", "bold");
        barlabel.setJustifyContentMode(JustifyContentMode.BETWEEN);
        barlabel.setAlignItems(FlexComponent.Alignment.BASELINE);

        add(barlabel, bar);
    }

    public void setLabelText(String text) {
        this.labelText.setText(text);
    }

    public void setLabelValue(double value) {
        this.labelValue.setText(String.valueOf((int)value));
    }

    public void setProgress(double progress) {
        this.bar.setValue(progress);
        this.setLabelValue(progress);
    }
}
