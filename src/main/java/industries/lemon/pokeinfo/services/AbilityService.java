package industries.lemon.pokeinfo.services;

import industries.lemon.pokeinfo.entities.*;
import industries.lemon.pokeinfo.thirdparty.pokeapi.PokeApiClient;
import industries.lemon.pokeinfo.thirdparty.pokeapi.models.AbilityFlavorTextResponse;
import industries.lemon.pokeinfo.thirdparty.pokeapi.models.AbilityResponse;
import industries.lemon.pokeinfo.thirdparty.pokeapi.models.VerboseEffectResponse;
import industries.lemon.pokeinfo.repositories.AbilityRepository;
import industries.lemon.pokeinfo.repositories.VerboseEffectRepository;
import industries.lemon.pokeinfo.utils.AbilityNameLibrary;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AbilityService extends BaseInitializableEntityService<Ability, AbilityRepository, AbilityResponse> {
    private final AbilityNameLibrary abilityNameLibrary;
    private final VerboseEffectRepository verboseEffectRepository;
    private final GenerationService generationService;
    private final LocalizedNameService localizedNameService;

    public AbilityService(
            PokeApiClient pokeApiClient,
            AbilityRepository abilityRepository,
            AbilityNameLibrary abilityNameLibrary,
            VerboseEffectRepository verboseEffectRepository,
            GenerationService generationService,
            LocalizedNameService localizedNameService
    ) {
        super(pokeApiClient, abilityRepository, Ability::new);
        this.abilityNameLibrary = abilityNameLibrary;
        this.verboseEffectRepository = verboseEffectRepository;
        this.generationService = generationService;
        this.localizedNameService = localizedNameService;
    }

    @Override
    public Optional<Ability> findById(int id) {
        return repository.findByAbilityId(id);
    }

    public List<String> getAbilityNames(int languageId) {
        return abilityNameLibrary.getNamesByLanguage(languageId);
    }

    public int getAbilityIdByName(String name, int languageId) {
        return abilityNameLibrary.getAbilityIdByName(name, languageId);
    }

    public String getNameByIdAndLanguage(int id, int languageId) {
        return abilityNameLibrary.getNameByIdAndLanguage(id, languageId);
    }

    public int getMaxId() {
        return abilityNameLibrary.getMaxId();
    }

    @Override
    protected Mono<AbilityResponse> fetchData(int id) {
        return pokeApiClient.getAbilityById(id);
    }

    @Override
    protected void pushDataFromResponse(Ability ability, AbilityResponse response) {
        Generation generation = generationService.findByIdOrCreate(response.getOriginGeneration().getId());
        Set<LocalizedName> localizedNames = localizedNameService.createLocalizedNames(response.getNames());
        Set<VerboseEffect> verboseEffects = createVerboseEffects(response);
        Set<AbilityFlavorText> flavorTexts = createFlavorTexts(response.getFlavorTextEntries());
        Set<Integer> pokemonReferenceIds = response.getPokemon().stream().map(pokemonResponse -> pokemonResponse.getPokemon().getId()).collect(Collectors.toSet());

        ability.applyResponse(response);
        ability.setGeneration(generation);
        ability.setLocalizedNames(localizedNames);
        ability.setVerboseEffects(verboseEffects);
        ability.setFlavorTexts(flavorTexts);
        ability.setPokemonReferenceIds(pokemonReferenceIds);
        ability.finishInitialization();
    }

    protected Set<VerboseEffect> createVerboseEffects(AbilityResponse response) {
        List<VerboseEffectResponse> verboseEffectResponses = response.getEffectEntries();
        return verboseEffectResponses.stream()
                .map(verboseEffectResponse -> {
                    VerboseEffect verboseEffect = verboseEffectResponse.intoVerboseEffect();
                    verboseEffectRepository.save(verboseEffect);
                    return verboseEffect;
                })
                .collect(Collectors.toSet());
    }

    protected Set<AbilityFlavorText> createFlavorTexts(List<AbilityFlavorTextResponse> flavorTextResponses) {
        return flavorTextResponses.stream()
                .map(AbilityFlavorTextResponse::intoFlavorText)
                .collect(Collectors.toSet());
    }
}
