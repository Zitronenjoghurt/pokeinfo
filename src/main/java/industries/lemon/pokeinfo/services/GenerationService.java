package industries.lemon.pokeinfo.services;

import industries.lemon.pokeinfo.entities.Generation;
import industries.lemon.pokeinfo.entities.LocalizedName;
import industries.lemon.pokeinfo.pokeapi.PokeApiClient;
import industries.lemon.pokeinfo.pokeapi.models.GenerationResponse;
import industries.lemon.pokeinfo.pokeapi.models.LocalizedNameResponse;
import industries.lemon.pokeinfo.repositories.GenerationRepository;
import industries.lemon.pokeinfo.repositories.LocalizedNameRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GenerationService extends BaseInitializableEntityService<Generation, GenerationRepository, GenerationResponse> {
    private final LocalizedNameRepository localizedNameRepository;

    public GenerationService(
            PokeApiClient pokeApiClient,
            GenerationRepository generationRepository,
            LocalizedNameRepository localizedNameRepository
    ) {
        super(pokeApiClient, generationRepository, Generation::new);
        this.localizedNameRepository = localizedNameRepository;
    }

    @Override
    public Optional<Generation> findById(int id) {
        return repository.findByGenerationId(id);
    }

    @Override
    protected Mono<GenerationResponse> fetchData(int id) {
        return pokeApiClient.getGenerationById(id);
    }

    @Override
    protected void pushDataFromResponse(Generation generation, GenerationResponse response) {
        Set<LocalizedName> localizedNames = createLocalizedNames(response);
        generation.applyResponse(response);
        generation.setLocalizedNames(localizedNames);
        generation.finishInitialization();
    }

    protected Set<LocalizedName> createLocalizedNames(GenerationResponse response) {
        List<LocalizedNameResponse> localizedNameResponses = response.getNames();
        return localizedNameResponses.stream()
                .map(localizedNameResponse -> {
                    LocalizedName localizedName = localizedNameResponse.intoLocalizedName();
                    localizedNameRepository.save(localizedName);
                    return localizedName;
                })
                .collect(Collectors.toSet());
    }
}
