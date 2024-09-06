package industries.lemon.pokeinfo.ui.components;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import industries.lemon.pokeinfo.entities.Pokemon;
import industries.lemon.pokeinfo.entities.PokemonStat;
import industries.lemon.pokeinfo.enums.PokemonStatValue;

import java.util.Optional;

public class PokemonStatsContainer extends VerticalLayout {

    private static final int MAX_STAT_VALUE = 255;
    private final LabeledProgressBar hpBar;
    private final LabeledProgressBar attackBar;
    private final LabeledProgressBar defenseBar;
    private final LabeledProgressBar specialAttackBar;
    private final LabeledProgressBar specialDefenseBar;
    private final LabeledProgressBar speedBar;
    private final Span baseStatTotal;

    public PokemonStatsContainer() {
        setSpacing(false);
        setPadding(false);
        setMargin(false);
        setAlignItems(Alignment.CENTER);

        getStyle().set("padding", "10px")
                .set("gap", "1px")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.2)")
                .set("border", "1px solid rgba(100,100,100,0.2)")
                .set("border-radius", "4px")
                .set("background-color", "var(--lumo-contrast-5pct)");

        Span title = new Span("Base Stats");
        title.getStyle().set("font-weight", "bold");

        this.baseStatTotal = new Span();
        this.baseStatTotal.getStyle().set("font-weight", "bold");

        this.hpBar             = createStatBar("Hp", PokemonStatValue.HP.getColor());
        this.attackBar         = createStatBar("Atk", PokemonStatValue.ATK.getColor());
        this.defenseBar        = createStatBar("Def", PokemonStatValue.DEF.getColor());
        this.specialAttackBar  = createStatBar("SpAtk", PokemonStatValue.SPATK.getColor());
        this.specialDefenseBar = createStatBar("SpDef", PokemonStatValue.SPDEF.getColor());
        this.speedBar          = createStatBar("Speed", PokemonStatValue.SPEED.getColor());

        add(title, hpBar, attackBar, defenseBar, specialAttackBar, specialDefenseBar, speedBar, baseStatTotal);
    }

    private LabeledProgressBar createStatBar(String statName, String color) {
        return new LabeledProgressBar(0, MAX_STAT_VALUE, statName, color, 14, 12);
    }

    public void update(Pokemon pokemon) {
        Optional<PokemonStat> hp = pokemon.getStatByName(PokemonStatValue.HP.getInternalName());
        Optional<PokemonStat> attack = pokemon.getStatByName(PokemonStatValue.ATK.getInternalName());
        Optional<PokemonStat> defense = pokemon.getStatByName(PokemonStatValue.DEF.getInternalName());
        Optional<PokemonStat> specialAttack = pokemon.getStatByName(PokemonStatValue.SPATK.getInternalName());
        Optional<PokemonStat> specialDefense = pokemon.getStatByName(PokemonStatValue.SPDEF.getInternalName());
        Optional<PokemonStat> speed = pokemon.getStatByName(PokemonStatValue.SPEED.getInternalName());

        hp.ifPresent(stat -> this.hpBar.setProgress(stat.getBaseStat()));
        attack.ifPresent(stat -> this.attackBar.setProgress(stat.getBaseStat()));
        defense.ifPresent(stat -> this.defenseBar.setProgress(stat.getBaseStat()));
        specialAttack.ifPresent(stat -> this.specialAttackBar.setProgress(stat.getBaseStat()));
        specialDefense.ifPresent(stat -> this.specialDefenseBar.setProgress(stat.getBaseStat()));
        speed.ifPresent(stat -> this.speedBar.setProgress(stat.getBaseStat()));

        this.baseStatTotal.setText("Total: "+pokemon.getBaseStatTotal());
    }
}
