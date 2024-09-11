package industries.lemon.pokeinfo.ui.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import industries.lemon.pokeinfo.enums.PokemonTyping;
import industries.lemon.pokeinfo.services.PageStateService;
import industries.lemon.pokeinfo.ui.MainLayout;
import industries.lemon.pokeinfo.ui.components.PokemonTypeLabel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Route(value = "types", layout = MainLayout.class)
@PageTitle("Typing | Pokedata")
@AnonymousAllowed
public class TypeView extends VerticalLayout implements BeforeEnterObserver {
    private final PageStateService pageStateService;

    private final Select<PokemonTyping> primarySelector;
    private final Select<PokemonTyping> secondarySelector;
    private int primaryTypeId = 0;
    private int secondaryTypeId = 0;
    private boolean initialized = false;

    public TypeView(PageStateService pageStateService) {
        this.pageStateService = pageStateService;

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);

        primarySelector = createTypeSelector(true);
        secondarySelector = createTypeSelector(false);
        HorizontalLayout searchBar = new HorizontalLayout(primarySelector, secondarySelector);

        add(searchBar);
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

        primaryTypeId = parseTypeString(primaryString, true);
        secondaryTypeId = parseTypeString(secondaryString, false);

        pageStateService.setPrimaryTypeId(primaryTypeId);
        pageStateService.setSecondaryTypeId(secondaryTypeId);

        if (primaryTypeId == 0) {
            primarySelector.setValue(null);
        } else {
            primarySelector.setValue(PokemonTyping.fromId(primaryTypeId));
        }

        if (secondaryTypeId == 0) {
            secondarySelector.setValue(null);
        } else {
            secondarySelector.setValue(PokemonTyping.fromId(secondaryTypeId));
        }

        updateTypeChart();

        initialized = true;
    }

    private void updateTypeChart() {
        // ToDo: Render type effectiveness chart
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
                return new PokemonTypeLabel(type, 25, 16, 140);
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
            primaryTypeId = type != null ? type.getId() : 0;
        } else {
            secondaryTypeId = type != null ? type.getId() : 0;
        }

        Map<String, String> parametersMap = new HashMap<>();
        parametersMap.put("primary", String.valueOf(primaryTypeId));
        parametersMap.put("secondary", String.valueOf(secondaryTypeId));

        QueryParameters queryParameters = QueryParameters.simple(parametersMap);
        UI.getCurrent().navigate(TypeView.class, queryParameters);
    }

    private Integer parseTypeString(String typeId, boolean isPrimaryType) {
        try {
            int id = Integer.parseInt(typeId);
            return (id >= 0 && id <= PokemonTyping.getMaxId()) ? id : pageStateService.getType(isPrimaryType);
        } catch (NumberFormatException e) {
            return pageStateService.getType(isPrimaryType);
        }
    }
}