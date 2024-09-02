package industries.lemon.pokeinfo.services;

import industries.lemon.pokeinfo.entities.Pokemon;
import industries.lemon.pokeinfo.pokeapi.PokeApiClient;
import industries.lemon.pokeinfo.pokeapi.models.PokemonResponse;
import industries.lemon.pokeinfo.repositories.PokemonRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class PokemonService {
    private final PokeApiClient pokeApiClient;
    private final PokemonRepository pokemonRepository;

    public PokemonService(
            PokeApiClient pokeApiClient,
            PokemonRepository pokemonRepository
    ) {
        this.pokeApiClient = pokeApiClient;
        this.pokemonRepository = pokemonRepository;
    }

    public Mono<Pokemon> getById(int pokemonId) {
        return Mono.fromCallable(() -> pokemonRepository.findByPokemonId(pokemonId))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(optionalPokemon -> optionalPokemon
                        .map(Mono::just)
                        .orElseGet(() -> fetchAndSavePokemon(pokemonId)));
    }

    private Mono<Pokemon> fetchAndSavePokemon(int pokemonId) {
        return pokeApiClient.getPokemonById(pokemonId)
                .map(PokemonResponse::intoPokemon)
                .publishOn(Schedulers.boundedElastic())
                .map(pokemon -> {
                    pokemonRepository.save(pokemon);
                    return pokemon;
                });
    }
}
