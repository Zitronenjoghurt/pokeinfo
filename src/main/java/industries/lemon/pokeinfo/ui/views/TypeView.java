package industries.lemon.pokeinfo.ui.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import industries.lemon.pokeinfo.enums.PokemonTyping;
import industries.lemon.pokeinfo.services.PageStateService;
import industries.lemon.pokeinfo.ui.MainLayout;
import industries.lemon.pokeinfo.ui.components.PokemonTypeLabel;
import industries.lemon.pokeinfo.ui.components.TypeEffectivenessChart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Route(value = "types", layout = MainLayout.class)
@PageTitle("Typing | Pokedata")
@AnonymousAllowed
public class TypeView extends VerticalLayout implements BeforeEnterObserver {
    private final PageStateService pageStateService;

    private final TypeEffectivenessChart effectivenessChart;
    private final Select<PokemonTyping> primarySelector;
    private final Select<PokemonTyping> secondarySelector;
    private PokemonTyping primaryType = null;
    private PokemonTyping secondaryType = null;
    private boolean initialized = false;

    public TypeView(PageStateService pageStateService) {
        this.pageStateService = pageStateService;
        this.effectivenessChart = new TypeEffectivenessChart();
        effectivenessChart.setTitle("When attacked...");

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);

        primarySelector = createTypeSelector(true);
        secondarySelector = createTypeSelector(false);
        FlexLayout searchBar = new FlexLayout(primarySelector, secondarySelector);
        searchBar.setFlexDirection(FlexLayout.FlexDirection.ROW);
        searchBar.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        searchBar.setAlignItems(Alignment.CENTER);
        searchBar.setJustifyContentMode(FlexLayout.JustifyContentMode.CENTER);
        searchBar.getStyle().set("gap", "5px");

        add(searchBar, effectivenessChart);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Map<String, List<String>> parameters = event.getLocation().getQueryParameters().getParameters();

        String primaryString = Optional.ofNullable(parameters.get("primary"))
                .map(List::getFirst)
                .orElse(null);
        String secondaryString = Optional.ofNullable(parameters.get("secondary"))
                .map(List::getFirst)
                .orElse(null);

        primaryType = parseTypeString(primaryString, true);
        secondaryType = parseTypeString(secondaryString, false);

        pageStateService.setPrimaryType(primaryType);
        pageStateService.setSecondaryType(secondaryType);

        primarySelector.setValue(primaryType);
        secondarySelector.setValue(secondaryType);

        updateTypeChart();

        initialized = true;
    }

    public static QueryParameters createQueryParameters(PokemonTyping primaryType, PokemonTyping secondaryType) {
        Map<String, String> parametersMap = new HashMap<>();
        parametersMap.put("primary", primaryType == null ? "none" : primaryType.getDisplayName().toLowerCase());
        parametersMap.put("secondary", secondaryType == null ? "none" : secondaryType.getDisplayName().toLowerCase());
        return QueryParameters.simple(parametersMap);
    }

    private void updateTypeChart() {
        effectivenessChart.setVisible(true);
        Map<PokemonTyping, Double> effectivenessMap = getEffectivenessMap();
        effectivenessChart.update(effectivenessMap);
    }

    private Map<PokemonTyping, Double> getEffectivenessMap() {
        if (primaryType == null && secondaryType == null) {
            effectivenessChart.setVisible(false);
            return new HashMap<>();
        } else if (primaryType == null) {
            return PokemonTyping.calculateEffectiveness(secondaryType);
        } else if (secondaryType == null) {
            return PokemonTyping.calculateEffectiveness(primaryType);
        } else {
            return PokemonTyping.calculateEffectiveness(primaryType, secondaryType);
        }
    }

    private Select<PokemonTyping> createTypeSelector(boolean isPrimary) {
        Select<PokemonTyping> typeSelector = new Select<>();

        if (isPrimary) {
            typeSelector.setLabel("Primary type");
        } else {
            typeSelector.setLabel("Secondary type");
        }

        typeSelector.setRenderer(new ComponentRenderer<>(
            type -> {
                if (type == null) {
                    return null;
                }
                return new PokemonTypeLabel(type, 25, 16, 140, null);
            }
        ));

        typeSelector.addValueChangeListener(event -> {
           if (initialized) {
               onTypeSelectionChange(isPrimary, event.getValue());
           }
        });

        typeSelector.setItems(PokemonTyping.values());
        typeSelector.setEmptySelectionAllowed(true);
        return typeSelector;
    }

    private void onTypeSelectionChange(boolean isPrimary, PokemonTyping type) {
        if (isPrimary) {
            primaryType = type;
        } else {
            secondaryType = type;
        }

        UI.getCurrent().navigate(TypeView.class, createQueryParameters(primaryType, secondaryType));
    }

    private PokemonTyping parseTypeString(String typeName, boolean isPrimaryType) {
        if (typeName == null) {
            return pageStateService.getType(isPrimaryType);
        }
        return PokemonTyping.fromName(typeName);
    }
}