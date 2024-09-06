package industries.lemon.pokeinfo.ui.components;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import industries.lemon.pokeinfo.entities.Pokemon;
import industries.lemon.pokeinfo.entities.PokemonStat;
import industries.lemon.pokeinfo.enums.PokemonStatValue;

import java.util.Optional;

public class PokemonStatsContainer extends VerticalLayout {

    private static final int MAX_STAT_VALUE = 255;
    private LabeledProgressBar hpBar;
    private LabeledProgressBar attackBar;
    private LabeledProgressBar defenseBar;
    private LabeledProgressBar specialAttackBar;
    private LabeledProgressBar specialDefenseBar;
    private LabeledProgressBar speedBar;

    public PokemonStatsContainer() {
        setSpacing(false);
        setPadding(false);
        setMargin(false);

        getStyle().set("padding", "10px")
                .set("gap", "1px")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.2)")
                .set("border", "1px solid rgba(100,100,100,0.2)")
                .set("border-radius", "4px")
                .set("background-color", "var(--lumo-contrast-5pct)");

        this.hpBar             = createStatBar("Hp", PokemonStatValue.HP.getColor());
        this.attackBar         = createStatBar("Atk", PokemonStatValue.ATK.getColor());
        this.defenseBar        = createStatBar("Def", PokemonStatValue.DEF.getColor());
        this.specialAttackBar  = createStatBar("SpAtk", PokemonStatValue.SPATK.getColor());
        this.specialDefenseBar = createStatBar("SpDef", PokemonStatValue.SPDEF.getColor());
        this.speedBar          = createStatBar("Speed", PokemonStatValue.SPEED.getColor());

        add(hpBar, attackBar, defenseBar, specialAttackBar, specialDefenseBar, speedBar);
    }

    private LabeledProgressBar createStatBar(String statName, String color) {
        return new LabeledProgressBar(0, MAX_STAT_VALUE, statName, color, 14, 10);
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
    }
}
