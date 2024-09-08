package industries.lemon.pokeinfo.services;

import industries.lemon.pokeinfo.entities.*;
import industries.lemon.pokeinfo.thirdparty.pokeapi.PokeApiClient;
import industries.lemon.pokeinfo.repositories.PokemonRepository;
import industries.lemon.pokeinfo.thirdparty.pokeapi.models.PokemonAbilityResponse;
import industries.lemon.pokeinfo.thirdparty.pokeapi.models.PokemonResponse;
import industries.lemon.pokeinfo.thirdparty.pokeapi.models.PokemonStatResponse;
import industries.lemon.pokeinfo.thirdparty.pokeapi.models.PokemonTypeResponse;
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
        PokemonSprites pokemonSprites = response.getSprites().intoPokemonSprites();
        Pokemon pokemon = response.intoPokemon();
        pokemon.setPokemonAbilities(findOrCreateAbilities(response, pokemon));
        pokemon.setSprites(pokemonSprites);
        pokemon.setStats(createStats(response.getStats()));
        pokemon.setTypes(createTypes(response.getTypes()));
        return pokemon;
    }

    protected Set<PokemonAbility> findOrCreateAbilities(PokemonResponse response, Pokemon pokemon) {
        List<PokemonAbilityResponse> pokemonAbilities = response.getAbilities();
        return pokemonAbilities.stream()
                .map(pokemonAbilityResponse -> {
                    Ability ability = abilityService.findByIdOrCreate(pokemonAbilityResponse.getId());
                    PokemonAbility pokemonAbility = new PokemonAbility();
                    pokemonAbility.setAbility(ability);
                    pokemonAbility.setSlot(pokemonAbilityResponse.getSlot());
                    pokemonAbility.setHidden(pokemonAbilityResponse.isHidden());
                    pokemonAbility.setPokemon(pokemon);
                    return pokemonAbility;
                })
                .collect(Collectors.toSet());
    }

    protected Set<PokemonStat> createStats(List<PokemonStatResponse> pokemonStatResponses) {
        return pokemonStatResponses.stream()
                .map(PokemonStatResponse::intoPokemonStat)
                .collect(Collectors.toSet());
    }

    protected Set<PokemonType> createTypes(List<PokemonTypeResponse> pokemonTypeResponses) {
        return pokemonTypeResponses.stream()
                .map(PokemonTypeResponse::intoPokemonType)
                .collect(Collectors.toSet());
    }
}
