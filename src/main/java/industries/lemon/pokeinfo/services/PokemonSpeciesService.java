package industries.lemon.pokeinfo.services;

import industries.lemon.pokeinfo.entities.*;
import industries.lemon.pokeinfo.pokeapi.PokeApiClient;
import industries.lemon.pokeinfo.pokeapi.models.PokemonSpeciesResponse;
import industries.lemon.pokeinfo.pokeapi.models.PokemonSpeciesVarietyResponse;
import industries.lemon.pokeinfo.repositories.PokemonSpeciesRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PokemonSpeciesService extends BaseEntityService<PokemonSpecies, PokemonSpeciesRepository, PokemonSpeciesResponse> {
    private final PokemonService pokemonService;
    private final LocalizedNameService localizedNameService;
    private final GrowthRateService growthRateService;

    public PokemonSpeciesService(
            PokeApiClient pokeApiClient,
            PokemonSpeciesRepository repository,
            PokemonService pokemonService,
            LocalizedNameService localizedNameService,
            GrowthRateService growthRateService
    ) {
        super(pokeApiClient, repository);
        this.pokemonService = pokemonService;
        this.localizedNameService = localizedNameService;
        this.growthRateService = growthRateService;
    }

    @Override
    public Optional<PokemonSpecies> findById(int id) {
        return repository.findBySpeciesId(id);
    }

    @Override
    protected Mono<PokemonSpeciesResponse> fetchData(int id) {
        return pokeApiClient.getPokemonSpeciesById(id);
    }

    @Override
    protected PokemonSpecies fromResponse(PokemonSpeciesResponse response) {
        Set<PokemonSpeciesVariant> variants = findOrCreateSpeciesVariants(response);
        Set<LocalizedName> localizedNames = localizedNameService.createLocalizedNames(response.getNames());
        GrowthRate growthRate = growthRateService.fetch(response.getGrowthRate().getId()).block();
        PokemonSpecies species = response.intoPokemonSpecies();
        species.setVariants(variants);
        species.setLocalizedNames(localizedNames);
        species.setGrowthRate(growthRate);
        return species;
    }

    protected Set<PokemonSpeciesVariant> findOrCreateSpeciesVariants(PokemonSpeciesResponse response) {
        List<PokemonSpeciesVarietyResponse> pokemonSpeciesVarietyResponses = response.getVarieties();
        return pokemonSpeciesVarietyResponses.stream()
                .map(pokemonSpeciesVarietyResponse -> {
                    int pokemonId = pokemonSpeciesVarietyResponse.getPokemon().getId();
                    Pokemon pokemon = pokemonService.fetch(pokemonId).block();
                    PokemonSpeciesVariant pokemonSpeciesVariant = new PokemonSpeciesVariant();
                    pokemonSpeciesVariant.setPokemon(pokemon);
                    pokemonSpeciesVariant.setDefault(pokemonSpeciesVarietyResponse.isDefault());
                    return pokemonSpeciesVariant;
                })
                .collect(Collectors.toSet());
    }
}
