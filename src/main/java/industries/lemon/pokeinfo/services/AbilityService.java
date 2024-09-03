package industries.lemon.pokeinfo.services;

import industries.lemon.pokeinfo.entities.Ability;
import industries.lemon.pokeinfo.entities.Generation;
import industries.lemon.pokeinfo.entities.LocalizedName;
import industries.lemon.pokeinfo.entities.VerboseEffect;
import industries.lemon.pokeinfo.pokeapi.PokeApiClient;
import industries.lemon.pokeinfo.pokeapi.models.AbilityResponse;
import industries.lemon.pokeinfo.pokeapi.models.LocalizedNameResponse;
import industries.lemon.pokeinfo.pokeapi.models.VerboseEffectResponse;
import industries.lemon.pokeinfo.repositories.AbilityRepository;
import industries.lemon.pokeinfo.repositories.LocalizedNameRepository;
import industries.lemon.pokeinfo.repositories.VerboseEffectRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AbilityService extends BaseInitializableEntityService<Ability, AbilityRepository, AbilityResponse> {
    private final LocalizedNameRepository localizedNameRepository;
    private final VerboseEffectRepository verboseEffectRepository;
    private final GenerationService generationService;

    public AbilityService(
            PokeApiClient pokeApiClient,
            AbilityRepository abilityRepository,
            LocalizedNameRepository localizedNameRepository,
            VerboseEffectRepository verboseEffectRepository,
            GenerationService generationService
    ) {
        super(pokeApiClient, abilityRepository, Ability::new);
        this.localizedNameRepository = localizedNameRepository;
        this.verboseEffectRepository = verboseEffectRepository;
        this.generationService = generationService;
    }

    @Override
    public Optional<Ability> findById(int id) {
        return repository.findByAbilityId(id);
    }

    @Override
    protected Mono<AbilityResponse> fetchData(int id) {
        return pokeApiClient.getAbilityById(id);
    }

    @Override
    protected void pushDataFromResponse(Ability ability, AbilityResponse response) {
        Generation generation = generationService.findByIdOrCreate(response.getOriginGeneration().getId());
        Set<LocalizedName> localizedNames = createLocalizedNames(response);
        Set<VerboseEffect> verboseEffects = createVerboseEffects(response);

        ability.applyResponse(response);
        ability.setGeneration(generation);
        ability.setLocalizedNames(localizedNames);
        ability.setVerboseEffects(verboseEffects);
        ability.finishInitialization();
    }

    protected Set<LocalizedName> createLocalizedNames(AbilityResponse response) {
        List<LocalizedNameResponse> localizedNameResponses = response.getNames();
        return localizedNameResponses.stream()
                .map(localizedNameResponse -> {
                    LocalizedName localizedName = localizedNameResponse.intoLocalizedName();
                    localizedNameRepository.save(localizedName);
                    return localizedName;
                })
                .collect(Collectors.toSet());
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
}
