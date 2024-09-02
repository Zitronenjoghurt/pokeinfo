package industries.lemon.pokeinfo.services;

import industries.lemon.pokeinfo.entities.Ability;
import industries.lemon.pokeinfo.entities.Pokemon;
import industries.lemon.pokeinfo.pokeapi.PokeApiClient;
import industries.lemon.pokeinfo.pokeapi.models.PokemonAbility;
import industries.lemon.pokeinfo.pokeapi.models.PokemonResponse;
import industries.lemon.pokeinfo.repositories.PokemonRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PokemonService {
    private final PokeApiClient pokeApiClient;
    private final PokemonRepository pokemonRepository;
    private final AbilityService abilityService;

    public PokemonService(
            PokeApiClient pokeApiClient,
            PokemonRepository pokemonRepository,
            AbilityService abilityService
    ) {
        this.pokeApiClient = pokeApiClient;
        this.pokemonRepository = pokemonRepository;
        this.abilityService = abilityService;
    }

    public Mono<Pokemon> getById(int pokemonId) {
        return Mono.fromCallable(() -> pokemonRepository.findByPokemonId(pokemonId))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(optionalPokemon -> optionalPokemon
                        .map(Mono::just)
                        .orElseGet(() -> fetchAndSavePokemon(pokemonId)));
    }

    public Mono<Set<Ability>> getInitializedAbilities(Pokemon pokemon) {
        return Mono.just(pokemon.getAbilities())
                .flatMapIterable(abilities -> abilities)
                .flatMap(abilityService::initializeAbility)
                .collect(Collectors.toSet());
    }

    protected Mono<Pokemon> fetchAndSavePokemon(int pokemonId) {
        return pokeApiClient.getPokemonById(pokemonId)
                .publishOn(Schedulers.boundedElastic())
                .map(response -> {
                    Pokemon pokemon = response.intoPokemon();
                    pokemon.setAbilities(getPokemonAbilities(response));
                    return pokemonRepository.save(pokemon);
                });
    }

    protected Set<Ability> getPokemonAbilities(PokemonResponse response) {
        List<PokemonAbility> abilities = response.getAbilities();
        return abilities.stream()
                .map(pokemonAbility -> abilityService.findByIdOrCreate(pokemonAbility.getId()))
                .collect(Collectors.toSet());
    }
}
