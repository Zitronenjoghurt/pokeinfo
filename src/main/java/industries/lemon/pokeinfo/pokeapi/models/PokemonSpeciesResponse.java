package industries.lemon.pokeinfo.pokeapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import industries.lemon.pokeinfo.entities.PokemonSpecies;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PokemonSpeciesResponse extends BaseEntityResponse {
    private String name;
    private int order;
    private int genderRate;
    private int captureRate;
    private int baseHappiness;
    @JsonProperty("is_baby")
    private boolean isBaby;
    @JsonProperty("is_legendary")
    private boolean isLegendary;
    @JsonProperty("is_mythical")
    private boolean isMythical;
    private int hatchCounter;
    private boolean hasGenderDifferences;
    private boolean formsSwitchable;
    private NamedApiResource growthRate;
    private List<PokemonSpeciesDexEntry> pokedexNumbers;
    private List<NamedApiResource> eggGroups;
    private NamedApiResource color;
    private NamedApiResource shape;
    private NamedApiResource evolvesFromSpecies;
    private NamedApiResource evolutionChain;
    private NamedApiResource habitat;
    private NamedApiResource generation;
    private List<LocalizedNameResponse> names;
    // ToDo: palParkEncounters
    private List<FlavorTextResponse> flavorTextEntries;
    private List<DescriptionResponse> formDescriptions;
    private List<GenusResponse> genera;
    private List<PokemonSpeciesVarietyResponse> varieties;

    public PokemonSpecies intoPokemonSpecies() {
        PokemonSpecies species = new PokemonSpecies();
        species.setSpeciesId(getId());
        species.setName(getName());
        species.setDexOrder(getOrder());
        species.setGenderRate(getGenderRate());
        species.setCaptureRate(getCaptureRate());
        species.setBaseHappiness(getBaseHappiness());
        species.setBaby(isBaby());
        species.setLegendary(isLegendary());
        species.setMythical(isMythical());
        species.setHatchCounter(getHatchCounter());
        species.setHasGenderDifferences(isHasGenderDifferences());
        species.setFormsSwitchable(isFormsSwitchable());
        return species;
    }
}
