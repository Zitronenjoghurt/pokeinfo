package industries.lemon.pokeinfo.services;

import industries.lemon.pokeinfo.entities.*;
import industries.lemon.pokeinfo.thirdparty.pokeapi.PokeApiClient;
import industries.lemon.pokeinfo.thirdparty.pokeapi.models.PokemonSpeciesResponse;
import industries.lemon.pokeinfo.thirdparty.pokeapi.models.PokemonSpeciesVarietyResponse;
import industries.lemon.pokeinfo.repositories.PokemonSpeciesRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PokemonSpeciesService extends BaseEntityService<PokemonSpecies, PokemonSpeciesRepository, PokemonSpeciesResponse> {
    private final FlavorTextService flavorTextService;
    private final GenerationService generationService;
    private final GrowthRateService growthRateService;
    private final LocalizedNameService localizedNameService;
    private final PokemonService pokemonService;
    private final TcgCardService tcgCardService;

    public PokemonSpeciesService(
            FlavorTextService flavorTextService,
            PokeApiClient pokeApiClient,
            PokemonSpeciesRepository repository,
            GenerationService generationService,
            GrowthRateService growthRateService,
            LocalizedNameService localizedNameService,
            PokemonService pokemonService,
            TcgCardService tcgCardService
    ) {
        super(pokeApiClient, repository);
        this.flavorTextService = flavorTextService;
        this.generationService = generationService;
        this.growthRateService = growthRateService;
        this.localizedNameService = localizedNameService;
        this.pokemonService = pokemonService;
        this.tcgCardService = tcgCardService;
    }

    public List<TcgCard> getTcgCards(PokemonSpecies species) {
        if (species.isTcgCardsInitialized()) {
            return tcgCardService.findCards(species.getSpeciesId());
        } else {
            List<TcgCard> cards = tcgCardService.initializeCards(species.getSpeciesId());
            species.setTcgCardsInitialized(true);
            repository.save(species);
            return cards;
        }
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
        Set<LocalizedName> localizedNames = localizedNameService.createLocalizedNames(response.getNames());
        Set<FlavorText> flavorTexts = flavorTextService.createFlavorTexts(response.getFlavorTextEntries());
        GrowthRate growthRate = growthRateService.fetch(response.getGrowthRate().getId()).block();
        Generation generation =generationService.findByIdOrCreate(response.getGeneration().getId());
        PokemonSpecies species = response.intoPokemonSpecies();
        Set<PokemonSpeciesVariant> variants = findOrCreateSpeciesVariants(response, species);
        species.setVariants(variants);
        species.setLocalizedNames(localizedNames);
        species.setFlavorTexts(flavorTexts);
        species.setGrowthRate(growthRate);
        species.setGeneration(generation);
        return species;
    }

    protected Set<PokemonSpeciesVariant> findOrCreateSpeciesVariants(PokemonSpeciesResponse response, PokemonSpecies species) {
        List<PokemonSpeciesVarietyResponse> pokemonSpeciesVarietyResponses = response.getVarieties();
        return pokemonSpeciesVarietyResponses.stream()
                .map(pokemonSpeciesVarietyResponse -> {
                    int pokemonId = pokemonSpeciesVarietyResponse.getPokemon().getId();
                    Pokemon pokemon = pokemonService.fetch(pokemonId).block();
                    PokemonSpeciesVariant pokemonSpeciesVariant = new PokemonSpeciesVariant();
                    pokemonSpeciesVariant.setPokemon(pokemon);
                    pokemonSpeciesVariant.setDefault(pokemonSpeciesVarietyResponse.isDefault());
                    pokemonSpeciesVariant.setSpecies(species);
                    return pokemonSpeciesVariant;
                })
                .collect(Collectors.toSet());
    }
}
