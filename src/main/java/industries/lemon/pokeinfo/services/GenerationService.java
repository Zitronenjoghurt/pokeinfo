package industries.lemon.pokeinfo.services;

import industries.lemon.pokeinfo.entities.Generation;
import industries.lemon.pokeinfo.entities.LocalizedName;
import industries.lemon.pokeinfo.pokeapi.PokeApiClient;
import industries.lemon.pokeinfo.pokeapi.models.GenerationResponse;
import industries.lemon.pokeinfo.repositories.GenerationRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.Set;

@Service
public class GenerationService extends BaseInitializableEntityService<Generation, GenerationRepository, GenerationResponse> {
    private final LocalizedNameService localizedNameService;

    public GenerationService(
            PokeApiClient pokeApiClient,
            GenerationRepository generationRepository,
            LocalizedNameService localizedNameService
    ) {
        super(pokeApiClient, generationRepository, Generation::new);
        this.localizedNameService = localizedNameService;
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
        Set<LocalizedName> localizedNames = localizedNameService.createLocalizedNames(response.getNames());
        generation.applyResponse(response);
        generation.setLocalizedNames(localizedNames);
        generation.finishInitialization();
    }
}
