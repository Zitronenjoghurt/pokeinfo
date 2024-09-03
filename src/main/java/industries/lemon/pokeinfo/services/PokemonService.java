package industries.lemon.pokeinfo.services;

import industries.lemon.pokeinfo.entities.Ability;
import industries.lemon.pokeinfo.entities.Pokemon;
import industries.lemon.pokeinfo.entities.PokemonAbility;
import industries.lemon.pokeinfo.pokeapi.PokeApiClient;
import industries.lemon.pokeinfo.pokeapi.models.PokemonAbilityResponse;
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
        pokemon.setPokemonAbilities(findOrCreateAbilities(response));
        return pokemon;
    }

    protected Set<PokemonAbility> findOrCreateAbilities(PokemonResponse response) {
        List<PokemonAbilityResponse> pokemonAbilities = response.getAbilities();
        return pokemonAbilities.stream()
                .map(pokemonAbilityResponse -> {
                    Ability ability = abilityService.findByIdOrCreate(pokemonAbilityResponse.getId());
                    PokemonAbility pokemonAbility = new PokemonAbility();
                    pokemonAbility.setAbility(ability);
                    pokemonAbility.setSlot(pokemonAbilityResponse.getSlot());
                    pokemonAbility.setHidden(pokemonAbilityResponse.isHidden());
                    return pokemonAbility;
                })
                .collect(Collectors.toSet());
    }
}
