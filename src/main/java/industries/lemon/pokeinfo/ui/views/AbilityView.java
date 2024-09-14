package industries.lemon.pokeinfo.ui.views;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import industries.lemon.pokeinfo.enums.Language;
import industries.lemon.pokeinfo.services.AbilityService;
import industries.lemon.pokeinfo.services.PageStateService;
import industries.lemon.pokeinfo.services.PokemonNameService;
import industries.lemon.pokeinfo.services.PokemonService;
import industries.lemon.pokeinfo.ui.MainLayout;
import industries.lemon.pokeinfo.ui.components.AbilityContainer;
import industries.lemon.pokeinfo.ui.components.LanguageSelector;

import java.util.List;

@Route(value = "ability", layout = MainLayout.class)
@PageTitle("Ability | Pokedata")
@AnonymousAllowed
public class AbilityView extends VerticalLayout implements HasUrlParameter<String> {
    private final AbilityService abilityService;
    private final PageStateService pageStateService;
    private final LanguageSelector languageSelector;
    private final ComboBox<String> nameSearchBar;
    private final AbilityContainer abilityContainer;

    private boolean initialized = false;
    private boolean loading = false;

    public AbilityView(
            AbilityService abilityService,
            PokemonService pokemonService,
            PageStateService pageStateService,
            PokemonNameService pokemonNameService) {
        this.abilityService = abilityService;
        this.pageStateService = pageStateService;

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setAlignItems(Alignment.CENTER);

        this.languageSelector = createLanguageSelector();
        this.nameSearchBar = createNameSearchBar();
        this.abilityContainer = new AbilityContainer(pokemonService, pokemonNameService);
        abilityContainer.setVisible(false);

        FlexLayout searchLayout = new FlexLayout(languageSelector, nameSearchBar);
        searchLayout.setFlexDirection(FlexLayout.FlexDirection.ROW);
        searchLayout.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        searchLayout.setAlignItems(Alignment.CENTER);
        searchLayout.getStyle().set("gap", "10px");

        languageSelector.setValue(Language.ENGLISH.getId());

        add(searchLayout, abilityContainer);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String abilityId) {
        int id = parseAbilityId(abilityId);

        // Ensures it only searches when the UI is ready
        if (initialized) {
            search(id);
        } else {
            // If UI wasn't initialized yet the onAttach will handle the search
            pageStateService.setCurrentAbilityId(id);
        }
    }

    @Override
    public void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        search(pageStateService.getCurrentAbilityId());
    }

    private void search(int abilityId) {
        boolean abilityIdOutOfBounds = abilityId < 1 || abilityId > abilityService.getMaxId();
        boolean redundantSearch = abilityId == pageStateService.getCurrentAbilityId() && initialized;
        if (isLoading() || abilityIdOutOfBounds || redundantSearch ) {
            return;
        }

        setLoading(true);
        String abilityName = abilityService.getNameByIdAndLanguage(abilityId, languageSelector.getValue());
        nameSearchBar.setValue(abilityName);

        abilityService.findInitialized(abilityId).doOnNext(
                ability -> getUI().ifPresentOrElse(ui -> ui.access(() -> {
                    this.abilityContainer.update(ability);
                    this.abilityContainer.setVisible(true);
                    ui.push();
                    pageStateService.setCurrentAbilityId(abilityId);
                    initialized = true;
                    setLoading(false);
                }), () -> {
                    initialized = true;
                    setLoading(false);
                })
        ).doOnError(
            e -> {
                initialized = true;
                setLoading(false);
            }
        ).subscribe();
    }

    public LanguageSelector createLanguageSelector() {
        LanguageSelector languageSelector = new LanguageSelector();
        languageSelector.addValueChangeListener(event -> onLanguageUpdated(event.getValue()));
        return languageSelector;
    }

    public ComboBox<String> createNameSearchBar() {
        ComboBox<String> nameSearchBar = new ComboBox<>();
        nameSearchBar.addValueChangeListener(event -> onNameUpdated(event.getValue()));
        return nameSearchBar;
    }

    private void onLanguageUpdated(Integer languageId) {
        List<String> searchResults = abilityService.getAbilityNames(languageId);
        nameSearchBar.setItems(searchResults);
        nameSearchBar.getDataProvider().refreshAll();
        if (initialized) {
            String abilityName = abilityService.getNameByIdAndLanguage(pageStateService.getCurrentAbilityId(), languageId);
            nameSearchBar.setValue(abilityName);
        }
    }

    private void onNameUpdated(String abilityName) {
        if (isLoading() || abilityName == null) {
            return;
        }

        int languageId = languageSelector.getValue();
        int abilityId = abilityService.getAbilityIdByName(abilityName, languageId);
        if (abilityId < 1) {
            return;
        }

        UI.getCurrent().navigate(AbilityView.class, String.valueOf(abilityId));
    }

    private void setLoading(boolean loading) {
        this.loading = loading;
        if (loading) {
            onLoadingStart();
        } else {
            onLoadingFinish();
        }
    }

    private boolean isLoading() {
        return loading;
    }

    private void onLoadingStart() {
        languageSelector.setEnabled(false);
        nameSearchBar.setEnabled(false);
        abilityContainer.getElement().getStyle().set("opacity", "0.5");
        abilityContainer.getElement().getStyle().set("pointer-events", "none");
    }

    private void onLoadingFinish() {
        languageSelector.setEnabled(true);
        nameSearchBar.setEnabled(true);
        abilityContainer.getElement().getStyle().set("opacity", "1");
        abilityContainer.getElement().getStyle().remove("pointer-events");
    }

    private int parseAbilityId(String idString) {
        try {
            int id = Integer.parseInt(idString);
            return (id > 0 && id <= abilityService.getMaxId()) ? id : pageStateService.getCurrentAbilityId();
        } catch (NumberFormatException e) {
            return pageStateService.getCurrentAbilityId();
        }
    }
}
