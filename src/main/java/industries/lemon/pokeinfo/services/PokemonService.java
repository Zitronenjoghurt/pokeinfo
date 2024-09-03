package industries.lemon.pokeinfo.services;

import industries.lemon.pokeinfo.entities.Ability;
import industries.lemon.pokeinfo.entities.Pokemon;
import industries.lemon.pokeinfo.pokeapi.PokeApiClient;
import industries.lemon.pokeinfo.pokeapi.models.PokemonAbility;
import industries.lemon.pokeinfo.pokeapi.models.PokemonResponse;
import industries.lemon.pokeinfo.repositories.PokemonRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PokemonService extends BaseEntityService<Pokemon, PokemonRepository, PokemonResponse> {
    private final AbilityService abilityService;

    public PokemonService(
            PokeApiClient pokeApiClient,
            PokemonRepository pokemonRepository,
            AbilityService abilityService
    ) {
        super(pokeApiClient, pokemonRepository);
        this.abilityService = abilityService;
    }

    @Override
    public Optional<Pokemon> findById(int id) {
        return repository.findByPokemonId(id);
    }

    @Override
    protected Mono<PokemonResponse> fetchData(int id) {
        return pokeApiClient.getPokemonById(id);
    }

    @Override
    protected Pokemon fromResponse(PokemonResponse response) {
        Pokemon pokemon = response.intoPokemon();
        pokemon.setAbilities(findOrCreateAbilities(response));
        return pokemon;
    }

    public Mono<Set<Ability>> getInitializedAbilities(Pokemon pokemon) {
        return Mono.just(pokemon.getAbilities())
                .flatMapIterable(abilities -> abilities)
                .flatMap(abilityService::getInitialized)
                .collect(Collectors.toSet());
    }

    protected Set<Ability> findOrCreateAbilities(PokemonResponse response) {
        List<PokemonAbility> abilities = response.getAbilities();
        return abilities.stream()
                .map(pokemonAbility -> abilityService.findByIdOrCreate(pokemonAbility.getId()))
                .collect(Collectors.toSet());
    }
}
