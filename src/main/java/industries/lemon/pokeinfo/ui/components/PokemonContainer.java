package industries.lemon.pokeinfo.ui.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;
import industries.lemon.pokeinfo.entities.Pokemon;
import industries.lemon.pokeinfo.entities.PokemonType;
import industries.lemon.pokeinfo.enums.PokemonTyping;
import industries.lemon.pokeinfo.services.AbilityService;
import industries.lemon.pokeinfo.ui.views.TypeView;
import industries.lemon.pokeinfo.utils.BrowserSupport;
import industries.lemon.pokeinfo.utils.UnitsUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class PokemonContainer extends FlexLayout {
    private final HorizontalLayout typeLabels;
    private final PokemonStatsContainer statsContainer;
    private final List<PokemonTyping> currentTypes = new ArrayList<>();
    private final SpritesContainer spritesContainer;
    private final AudioPlayer audioPlayer;
    private final IconLabeledField heightField;
    private final IconLabeledField weightField;
    private final IconLabeledField baseXpField;
    private final AbilityList abilityList;

    private static final int TYPE_ICON_SIZE = 30;
    private static final int TYPE_ICON_FONT_SIZE = 20;
    private static final int TYPE_ICON_WIDTH = 140;

    public PokemonContainer(AbilityService abilityService) {
        setAlignItems(Alignment.START);
        setJustifyContentMode(JustifyContentMode.EVENLY);
        setFlexDirection(FlexDirection.ROW);
        setFlexWrap(FlexWrap.WRAP);

        this.typeLabels = new HorizontalLayout();
        Button typeMatchupButton = new Button(new Icon(VaadinIcon.ACADEMY_CAP));
        typeMatchupButton.addClickListener(e -> onTypeMatchupClicked());
        typeMatchupButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout typesBar = new HorizontalLayout(typeLabels, typeMatchupButton);

        this.statsContainer = new PokemonStatsContainer();
        VerticalLayout leftContainer = new VerticalLayout(typesBar, statsContainer);
        leftContainer.setAlignItems(Alignment.CENTER);
        leftContainer.setWidth("auto");

        this.spritesContainer = new SpritesContainer();
        this.abilityList = new AbilityList(abilityService);
        this.audioPlayer = new AudioPlayer();
        this.heightField = new IconLabeledField("Height", "size", "0", 20);
        this.weightField = new IconLabeledField("Weight", "weight", "0", 20);
        FlexLayout sizeContainer = new FlexLayout(heightField, weightField);
        sizeContainer.setFlexDirection(FlexLayout.FlexDirection.ROW);
        sizeContainer.setJustifyContentMode(JustifyContentMode.EVENLY);
        sizeContainer.setAlignItems(Alignment.STRETCH);
        sizeContainer.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        sizeContainer.setSizeFull();
        this.baseXpField = new IconLabeledField("Base Exp.", "xp", "0", 20);
        VerticalLayout rightContainer = new VerticalLayout(spritesContainer, abilityList, audioPlayer, sizeContainer, baseXpField);
        rightContainer.setAlignItems(Alignment.CENTER);
        rightContainer.setWidth("auto");

        add(leftContainer, rightContainer);
    }

    public void update(Pokemon pokemon) {
        this.typeLabels.removeAll();
        addTypes(pokemon.getTypes());
        this.statsContainer.update(pokemon);
        this.spritesContainer.update(pokemon.getSprites());
        this.baseXpField.setValue(String.valueOf(pokemon.getBaseExperience()));

        String heightMetric = UnitsUtils.formatDecimetersMetric(pokemon.getHeight());
        String heightImperial = UnitsUtils.formatDecimetersImperial(pokemon.getHeight());
        String weightMetric = UnitsUtils.formatHectogramsMetric(pokemon.getWeight());
        String weightImperial = UnitsUtils.formatHectogramsImperial(pokemon.getWeight());

        this.heightField.setValue(heightMetric + " | " + heightImperial);
        this.weightField.setValue(weightMetric + " | " + weightImperial);

        this.abilityList.update(pokemon);

        if (BrowserSupport.supportsOGG(VaadinSession.getCurrent().getBrowser())) {
            this.audioPlayer.update(pokemon.getCries().getLatest());
        } else {
            this.audioPlayer.setVisible(false);
        }
    }

    private void addTypes(Set<PokemonType> types) {
        currentTypes.clear();
        List<PokemonType> sortedTypes = new ArrayList<>(types);
        sortedTypes.sort(Comparator.comparingInt(PokemonType::getSlot));

        for (PokemonType type : sortedTypes) {
            PokemonTyping typing = PokemonTyping.fromName(type.getType());
            typeLabels.add(new PokemonTypeLabel(typing, TYPE_ICON_SIZE, TYPE_ICON_FONT_SIZE, TYPE_ICON_WIDTH, null));
            currentTypes.add(typing);
        }

        if (sortedTypes.size() == 1) {
            typeLabels.add(new PokemonTypeLabel(null, TYPE_ICON_SIZE, TYPE_ICON_FONT_SIZE, TYPE_ICON_WIDTH, null));
        }
    }

    private void onTypeMatchupClicked() {
        PokemonTyping primary = !currentTypes.isEmpty() ? currentTypes.getFirst() : null;
        PokemonTyping secondary = currentTypes.size() > 1 ? currentTypes.getLast() : null;
        UI.getCurrent().navigate(TypeView.class, TypeView.createQueryParameters(primary, secondary));
    }

    public void show() {
        setVisible(true);
    }

    public void hide() {
        setVisible(false);
    }
}
