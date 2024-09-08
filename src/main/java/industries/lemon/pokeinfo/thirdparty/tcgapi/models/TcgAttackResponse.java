package industries.lemon.pokeinfo.thirdparty.tcgapi.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import industries.lemon.pokeinfo.entities.TcgAttack;
import industries.lemon.pokeinfo.thirdparty.tcgapi.enums.TcgPokemonType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TcgAttackResponse {
    private String name;
    private List<TcgPokemonType> cost;
    private int convertedEnergyCost;
    private String damage;
    private String text;

    public TcgAttack intoAttack() {
        TcgAttack attack = new TcgAttack();
        attack.setName(getName());
        attack.setCost(getCost());
        attack.setConvertedEnergyCost(getConvertedEnergyCost());
        attack.setDamage(getDamage());
        attack.setText(getText());
        return attack;
    }
}
