package industries.lemon.pokeinfo.ui.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import industries.lemon.pokeinfo.entities.Pokemon;
import industries.lemon.pokeinfo.entities.PokemonAbility;
import industries.lemon.pokeinfo.enums.Language;
import industries.lemon.pokeinfo.services.AbilityService;
import industries.lemon.pokeinfo.ui.views.AbilityView;

public class AbilityList extends VerticalLayout {
    private final AbilityService abilityService;
    private final FlexLayout abilityLayout;

    public AbilityList(AbilityService abilityService) {
        this.abilityService = abilityService;

        setMaxWidth("auto");
        setAlignItems(Alignment.CENTER);
        getStyle()
                .set("padding", "0px")
                .set("border-radius", "var(--lumo-border-radius-l)")
                .set("background-color", "var(--lumo-contrast-5pct)")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.2)")
                .set("padding", "10px");

        H4 title = new H4("Abilities");

        abilityLayout = new FlexLayout();
        abilityLayout.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        abilityLayout.setFlexDirection(FlexLayout.FlexDirection.ROW);
        abilityLayout.setAlignItems(FlexLayout.Alignment.CENTER);
        abilityLayout.getStyle().set("gap", "10px");

        add(title, abilityLayout);
    }

    public void update(Pokemon pokemon) {
        abilityLayout.removeAll();
        for (PokemonAbility pokemonAbility: pokemon.getPokemonAbilities()) {
            abilityLayout.add(createButton(pokemonAbility));
        }
    }

    private Button createButton(PokemonAbility ability) {
        int id = ability.getAbility().getAbilityId();
        String name = abilityService.getNameByIdAndLanguage(id, Language.ENGLISH.getId());
        Button button = new Button(name);
        button.addClickListener(e -> UI.getCurrent().navigate(AbilityView.class, String.valueOf(id)));
        return button;
    }
}
