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
    private NamedApiResourceResponse growthRate;
    private List<PokemonSpeciesDexEntryResponse> pokedexNumbers;
    private List<NamedApiResourceResponse> eggGroups;
    private NamedApiResourceResponse color;
    private NamedApiResourceResponse shape;
    private NamedApiResourceResponse evolvesFromSpecies;
    private NamedApiResourceResponse evolutionChain;
    private NamedApiResourceResponse habitat;
    private NamedApiResourceResponse generation;
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
