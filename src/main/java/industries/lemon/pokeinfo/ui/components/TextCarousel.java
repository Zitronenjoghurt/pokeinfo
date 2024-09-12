package industries.lemon.pokeinfo.ui.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;
import java.util.List;

public class TextCarousel extends VerticalLayout {
    private final List<TextItem> items = new ArrayList<>();
    private int currentIndex = 0;
    private final H5 titleElement;
    private final Paragraph textElement;

    public TextCarousel() {
        Div contentDiv = new Div();
        textElement = new Paragraph();
        contentDiv.add(textElement);
        contentDiv.getStyle().set("flex-grow", "1");

        titleElement = new H5();
        titleElement.getStyle().set("margin", "0");

        Button prevButton = new Button("<", e -> showPrevious());
        Button nextButton = new Button(">", e -> showNext());

        HorizontalLayout navigation = new HorizontalLayout(prevButton, titleElement, nextButton);
        navigation.setAlignItems(Alignment.CENTER);
        navigation.setJustifyContentMode(JustifyContentMode.CENTER);
        navigation.setWidth("auto");

        add(contentDiv, navigation);
        setAlignItems(Alignment.STRETCH);
        setWidthFull();
    }

    public void addItem(String title, String text) {
        items.add(new TextItem(title, text));
        if (items.size() == 1) {
            showCurrent();
        }
    }

    public void resetItems() {
        currentIndex = 0;
        items.clear();
    }

    private void showCurrent() {
        if (!items.isEmpty()) {
            TextItem currentItem = items.get(currentIndex);
            titleElement.setText(currentItem.title());
            textElement.setText(currentItem.text());
        }
    }

    private void showNext() {
        if (!items.isEmpty()) {
            currentIndex = (currentIndex + 1) % items.size();
            showCurrent();
        }
    }

    private void showPrevious() {
        if (!items.isEmpty()) {
            currentIndex = (currentIndex - 1 + items.size()) % items.size();
            showCurrent();
        }
    }

    private record TextItem(String title, String text) {}
}
