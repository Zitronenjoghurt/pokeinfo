package industries.lemon.pokeinfo.services;

import industries.lemon.pokeinfo.entities.Ability;
import industries.lemon.pokeinfo.entities.Generation;
import industries.lemon.pokeinfo.entities.LocalizedName;
import industries.lemon.pokeinfo.entities.VerboseEffect;
import industries.lemon.pokeinfo.thirdparty.pokeapi.PokeApiClient;
import industries.lemon.pokeinfo.thirdparty.pokeapi.models.AbilityResponse;
import industries.lemon.pokeinfo.thirdparty.pokeapi.models.VerboseEffectResponse;
import industries.lemon.pokeinfo.repositories.AbilityRepository;
import industries.lemon.pokeinfo.repositories.VerboseEffectRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AbilityService extends BaseInitializableEntityService<Ability, AbilityRepository, AbilityResponse> {
    private final VerboseEffectRepository verboseEffectRepository;
    private final GenerationService generationService;
    private final LocalizedNameService localizedNameService;

    public AbilityService(
            PokeApiClient pokeApiClient,
            AbilityRepository abilityRepository,
            VerboseEffectRepository verboseEffectRepository,
            GenerationService generationService,
            LocalizedNameService localizedNameService
    ) {
        super(pokeApiClient, abilityRepository, Ability::new);
        this.verboseEffectRepository = verboseEffectRepository;
        this.generationService = generationService;
        this.localizedNameService = localizedNameService;
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
        Set<LocalizedName> localizedNames = localizedNameService.createLocalizedNames(response.getNames());
        Set<VerboseEffect> verboseEffects = createVerboseEffects(response);

        ability.applyResponse(response);
        ability.setGeneration(generation);
        ability.setLocalizedNames(localizedNames);
        ability.setVerboseEffects(verboseEffects);
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
}
