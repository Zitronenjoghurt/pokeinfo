package industries.lemon.pokeinfo.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import industries.lemon.pokeinfo.ui.views.HomeView;
import industries.lemon.pokeinfo.ui.views.PokemonView;

import java.util.HashMap;
import java.util.Map;

@AnonymousAllowed
public class MainLayout extends AppLayout implements BeforeEnterObserver {

    private final Tabs tabs;
    private final Map<Class<? extends Component>, Tab> navigationTargetToTab = new HashMap<>();

    public MainLayout() {
        DrawerToggle toggle = new DrawerToggle();
        H1 title = new H1("Pokedata.xyz");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        this.tabs = createNavigation();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);

        addToDrawer(tabs);
        addToNavbar(toggle, title);

        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setSizeFull();
        contentLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        contentLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        setContent(contentLayout);
    }

    private Tabs createNavigation() {
        Tab homeTab = createTab("Home", HomeView.class);
        Tab pokemonTab = createTab("Pokemon", PokemonView.class);
        return new Tabs(homeTab, pokemonTab);
    }

    private Tab createTab(String label, Class<? extends Component> navigationTarget) {
        Tab tab = new Tab(new RouterLink(label, navigationTarget));
        navigationTargetToTab.put(navigationTarget, tab);
        return tab;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Class<?> targetType = event.getNavigationTarget();
        tabs.setSelectedTab(navigationTargetToTab.get(targetType));
    }
}
