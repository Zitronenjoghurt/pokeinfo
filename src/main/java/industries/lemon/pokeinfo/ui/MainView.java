package industries.lemon.pokeinfo.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import industries.lemon.pokeinfo.Constants;
import industries.lemon.pokeinfo.services.PokemonNameService;
import industries.lemon.pokeinfo.services.PokemonSpeciesService;
import industries.lemon.pokeinfo.ui.tabs.PokemonView;

import java.util.List;

@Route("")
@PageTitle("Test")
@RouteAlias(value = "pokemon")
@RouteAlias(value = "pokemon/:nationalDex")
@AnonymousAllowed
public class MainView extends AppLayout implements BeforeEnterObserver {
    private final VerticalLayout contentLayout;
    private final Tabs tabs;
    private Tab homeTab;
    private Tab pokemonTab;

    private final PokemonView pokemonView;

    public MainView(
            PokemonNameService pokemonNameService,
            PokemonSpeciesService pokemonSpeciesService
    ) {
        DrawerToggle toggle = new DrawerToggle();
        H1 title = new H1("Poke-Info");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        this.pokemonView = new PokemonView(pokemonNameService, pokemonSpeciesService);
        this.tabs = createNavigation();

        addToDrawer(tabs);
        addToNavbar(toggle, title);

        contentLayout = new VerticalLayout();
        contentLayout.setSizeFull();
        contentLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        contentLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        setContent(contentLayout);
    }

    private Tabs createNavigation() {
        this.homeTab = new Tab("Home");
        this.pokemonTab = new Tab("Pokemon");

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
        contentLayout.add(pokemonView);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        List<String> segments = event.getLocation().getSegments();
        if (segments.isEmpty()) {
            return;
        }

        if (segments.getFirst().isEmpty()) {
            showWelcomeContent();
            return;
        }

        if (segments.getFirst().equals("pokemon")) {
            routePokemon(segments);
        }
    }

    private void routePokemon(List<String> segments) {
        tabs.setSelectedTab(pokemonTab);
        if (segments.size() == 1) {
            showPokemonContent();
            return;
        }

        int speciesId = parseSpeciesId(segments.get(1));
        showPokemonContent();
        pokemonView.search(speciesId);
    }

    private int parseSpeciesId(String idString) {
        try {
            int id = Integer.parseInt(idString);
            return (id > 0 && id <= Constants.MAX_SPECIES_ID) ? id : 1;
        } catch (NumberFormatException e) {
            return 1;
        }
    }
}
