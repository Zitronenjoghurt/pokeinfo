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
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AbilityService {
    private final PokeApiClient pokeApiClient;
    private final AbilityRepository abilityRepository;
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
        this.pokeApiClient = pokeApiClient;
        this.abilityRepository = abilityRepository;
        this.localizedNameRepository = localizedNameRepository;
        this.verboseEffectRepository = verboseEffectRepository;
        this.generationService = generationService;
    }

    public Ability findByIdOrCreate(int abilityId) {
        return abilityRepository.findByAbilityId(abilityId)
            .orElseGet(() -> {
                Ability newAbility = new Ability();
                newAbility.setAbilityId(abilityId);
                abilityRepository.save(newAbility);
                return newAbility;
            });
    }

    public Mono<Ability> initializeAbility(Ability ability) {
        if (ability.isInitialized()) {
            return Mono.just(ability);
        }

        return pokeApiClient.getAbilityById(ability.getAbilityId())
            .publishOn(Schedulers.boundedElastic())
            .map(abilityResponse -> {
                Generation generation = generationService.findByIdOrCreate(abilityResponse.getOriginGeneration().getId());
                Set<LocalizedName> localizedNames = createLocalizedNames(abilityResponse);
                Set<VerboseEffect> verboseEffects = createVerboseEffects(abilityResponse);
                ability.initializeFromResponse(abilityResponse, generation, localizedNames, verboseEffects);
                abilityRepository.save(ability);
                return ability;
            });
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
