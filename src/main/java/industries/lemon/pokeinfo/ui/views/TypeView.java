package industries.lemon.pokeinfo.ui.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import industries.lemon.pokeinfo.enums.PokemonTyping;
import industries.lemon.pokeinfo.services.PageStateService;
import industries.lemon.pokeinfo.ui.MainLayout;

import java.util.List;
import java.util.Map;

@Route(value = "types", layout = MainLayout.class)
@PageTitle("Typing | Pokedata")
@AnonymousAllowed
public class TypeView extends VerticalLayout implements BeforeEnterObserver {
    private final PageStateService pageStateService;

    private Integer type1 = null;
    private Integer type2 = null;

    public TypeView(PageStateService pageStateService) {
        this.pageStateService = pageStateService;

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Map<String, List<String>> parameters = event.getLocation().getQueryParameters().getParameters();

        String type1String = parameters.get("type1").getFirst();
        String type2String = parameters.get("type2").getFirst();

        type1 = parseTypeString(type1String, true);
        type2 = parseTypeString(type2String, false);

        pageStateService.setType1(type1);
        pageStateService.setType2(type2);
    }

    private Integer parseTypeString(String typeIndex, boolean isFirstType) {
        try {
            int index = Integer.parseInt(typeIndex);
            return (index >= 0 && index <= PokemonTyping.getMaxIndex()) ? index : pageStateService.getType(isFirstType);
        } catch (NumberFormatException e) {
            return pageStateService.getType(isFirstType);
        }
    }
}