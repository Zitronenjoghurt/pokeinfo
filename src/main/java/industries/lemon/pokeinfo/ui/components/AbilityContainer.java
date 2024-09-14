package industries.lemon.pokeinfo.ui.components;

import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import industries.lemon.pokeinfo.entities.*;
import industries.lemon.pokeinfo.interfaces.HasLanguage;
import industries.lemon.pokeinfo.services.PokemonNameService;
import industries.lemon.pokeinfo.services.PokemonService;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class AbilityContainer extends VerticalLayout {
    private final PokemonService pokemonService;
    private final PokemonNameService pokemonNameService;
    private final IconLabeledField isMainSeries;
    private final IconLabeledField originGeneration;
    private final Paragraph shortEffect;
    private final Paragraph longEffect;
    private final FlavorTextContainer flavorTextContainer;
    private final FlexLayout pokemonButtonGrid;

    public AbilityContainer(
            PokemonService pokemonService,
            PokemonNameService pokemonNameService
    ) {
        this.pokemonService = pokemonService;
        this.pokemonNameService = pokemonNameService;

        getStyle()
                .set("border-radius", "var(--lumo-border-radius-l)")
                .set("background-color", "var(--lumo-contrast-5pct)")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.2)");
        setHeight("auto");
        setWidth("auto");
        setAlignItems(Alignment.CENTER);

        this.isMainSeries = new IconLabeledField("Main series?", "pokeball", "no", 20);
        this.originGeneration = new IconLabeledField("Origin Gen.", "pokeball", "0", 20);
        HorizontalLayout info = new HorizontalLayout(isMainSeries, originGeneration);
        this.shortEffect = createShortEffect();
        VerticalLayout leftContainer = new VerticalLayout(info, shortEffect);
        leftContainer.setAlignItems(Alignment.CENTER);
        leftContainer.setPadding(false);
        leftContainer.setWidth("auto");
        this.flavorTextContainer = new FlavorTextContainer();
        HorizontalLayout topContainer = new HorizontalLayout(leftContainer, flavorTextContainer);
        topContainer.setAlignItems(Alignment.CENTER);
        topContainer.setPadding(false);
        topContainer.setWidth("auto");

        this.longEffect = createLongEffect();
        this.pokemonButtonGrid = createPokemonButtonGrid();

        add(topContainer, longEffect, pokemonButtonGrid);
    }

    public void update(Ability ability) {
        Set<Pokemon> pokemons = pokemonService.fetchMultiple(ability.getPokemonReferenceIds()).block();

        Boolean abilityIsMainSeries = ability.getIsMainSeries();
        isMainSeries.setValue(abilityIsMainSeries ? "Yes": "No");
        if (abilityIsMainSeries) {
            originGeneration.setValue(String.valueOf(ability.getGeneration().getGenerationId()));
            originGeneration.setVisible(true);
        } else {
            originGeneration.setVisible(false);
        }

        Set<VerboseEffect> effects = HasLanguage.filterByLanguage(ability.getVerboseEffects(), "en");
        if (!effects.isEmpty()) {
            VerboseEffect effect = effects.iterator().next();
            shortEffect.setText(effect.getShortEffect());
            longEffect.setText(effect.getEffect());
            shortEffect.setVisible(true);
            longEffect.setVisible(true);
        } else {
            shortEffect.setVisible(false);
            longEffect.setVisible(false);
        }

        Set<AbilityFlavorText> flavorTexts = HasLanguage.filterByLanguage(ability.getFlavorTexts(), "en");
        flavorTextContainer.update(flavorTexts);

        pokemonButtonGrid.removeAll();
        if (pokemons != null) {
            List<Pokemon> sortedPokemons = pokemons.stream()
                    .filter(pokemon -> pokemon.getSprites().getFrontDefault() != null)
                    .sorted(Comparator.comparing(Pokemon::getName))
                    .toList();

            for (Pokemon pokemon : sortedPokemons) {
                // If it has no front default sprite, it's not important
                if (pokemon.getSprites().getFrontDefault() == null) {
                    continue;
                }
                PokemonButton button = new PokemonButton(pokemonNameService, pokemon);
                pokemonButtonGrid.add(button);
            }
        }
    }

    private Paragraph createShortEffect() {
        Paragraph paragraph = new Paragraph();
        paragraph.getStyle()
                .set("border-radius", "var(--lumo-border-radius-l)")
                .set("background-color", "var(--lumo-contrast-5pct)")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.2)")
                .set("padding", "var(--lumo-space-m)")
                .set("text-align", "center");
        paragraph.setWidth("auto");
        paragraph.setMaxWidth("300px");
        return paragraph;
    }

    private Paragraph createLongEffect() {
        Paragraph paragraph = new Paragraph();
        paragraph.getStyle()
                .set("border-radius", "var(--lumo-border-radius-l)")
                .set("background-color", "var(--lumo-contrast-5pct)")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.2)")
                .set("padding", "var(--lumo-space-m)")
                .set("text-align", "center");
        paragraph.setWidth("auto");
        paragraph.setMaxWidth("800px");
        return paragraph;
    }

    private FlexLayout createPokemonButtonGrid() {
        FlexLayout layout = new FlexLayout();
        layout.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        layout.setJustifyContentMode(FlexLayout.JustifyContentMode.CENTER);
        layout.setFlexDirection(FlexLayout.FlexDirection.ROW);
        layout.getStyle().set("gap", "10px");
        layout.setMaxWidth("800px");
        layout.setWidth("auto");
        return layout;
    }
}
