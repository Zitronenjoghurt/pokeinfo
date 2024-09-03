package industries.lemon.pokeinfo.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import industries.lemon.pokeinfo.services.PokemonService;
import industries.lemon.pokeinfo.ui.tabs.PokemonView;

@Route("")
@PageTitle("Test")
@AnonymousAllowed
public class MainView extends AppLayout {
    private final PokemonService pokemonService;
    private final VerticalLayout contentLayout;
    private Button homeButton;
    private Button pokemonButton;

    public MainView(PokemonService pokemonService) {
        this.pokemonService = pokemonService;

        DrawerToggle toggle = new DrawerToggle();
        H1 title = new H1("Poke-Info");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        Tabs nav = createNavigation();

        addToDrawer(nav);
        addToNavbar(toggle, title);

        contentLayout = new VerticalLayout();
        contentLayout.setSizeFull();
        contentLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        contentLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        setContent(contentLayout);
        showWelcomeContent();
    }

    private Tabs createNavigation() {
        Tab homeTab = new Tab("Home");
        Tab pokemonTab = new Tab("Pokemon");

        Tabs tabs = new Tabs(homeTab, pokemonTab);
        tabs.setOrientation(Tabs.Orientation.VERTICAL);

        tabs.addSelectedChangeListener(event -> {
            if (event.getSelectedTab().equals(homeTab)) {
                showWelcomeContent();
            } else if (event.getSelectedTab().equals(pokemonTab)) {
                showPokemonContent();
            }
        });

        return tabs;
    }

    private void showWelcomeContent() {
        contentLayout.removeAll();
        contentLayout.add(new H1("Welcome to Poke-Info!"));
    }

    private void showPokemonContent() {
        contentLayout.removeAll();
        contentLayout.add(new PokemonView(pokemonService));
    }
}
