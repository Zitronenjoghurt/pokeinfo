package industries.lemon.pokeinfo.ui.components;

import com.vaadin.flow.component.combobox.ComboBox;
import industries.lemon.pokeinfo.enums.Language;

public class LanguageSelector extends ComboBox<Integer> {
    public LanguageSelector() {
        setItems(Language.getFlagMap().keySet());
        setItemLabelGenerator(Language.getFlagMap()::get);
        setWidth("auto");
        getElement().setAttribute("aria-label", "Select Language");
    }
}
