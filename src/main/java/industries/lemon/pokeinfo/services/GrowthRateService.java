package industries.lemon.pokeinfo.services;

import industries.lemon.pokeinfo.entities.GrowthRate;
import industries.lemon.pokeinfo.entities.GrowthRateExperienceLevel;
import industries.lemon.pokeinfo.entities.LocalizedDescription;
import industries.lemon.pokeinfo.pokeapi.PokeApiClient;
import industries.lemon.pokeinfo.pokeapi.models.GrowthRateExperienceLevelResponse;
import industries.lemon.pokeinfo.pokeapi.models.GrowthRateResponse;
import industries.lemon.pokeinfo.repositories.GrowthRateRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GrowthRateService extends BaseEntityService<GrowthRate, GrowthRateRepository, GrowthRateResponse> {
    private final LocalizedDescriptionService localizedDescriptionService;

    public GrowthRateService(
            PokeApiClient pokeApiClient,
            GrowthRateRepository repository,
            LocalizedDescriptionService localizedDescriptionService
    ) {
        super(pokeApiClient, repository);
        this.localizedDescriptionService = localizedDescriptionService;
    }

    @Override
    public Optional<GrowthRate> findById(int id) {
        return repository.findByGrowthRateId(id);
    }

    @Override
    protected Mono<GrowthRateResponse> fetchData(int id) {
        return pokeApiClient.getGrowthRateById(id);
    }

    @Override
    protected GrowthRate fromResponse(GrowthRateResponse response) {
        Set<LocalizedDescription> localizedDescriptions = localizedDescriptionService.createLocalizedDescriptions(response.getDescriptions());
        Set<GrowthRateExperienceLevel> levels = createGrowthRateExperienceLevels(response);
        GrowthRate growthRate = new GrowthRate();
        growthRate.setGrowthRateId(response.getId());
        growthRate.setName(response.getName());
        growthRate.setFormula(response.getFormula());
        growthRate.setLocalizedDescriptions(localizedDescriptions);
        growthRate.setLevels(levels);
        return growthRate;
    }

    protected Set<GrowthRateExperienceLevel> createGrowthRateExperienceLevels(GrowthRateResponse response) {
        List<GrowthRateExperienceLevelResponse> levelResponses = response.getLevels();
        return levelResponses.stream()
                .map(GrowthRateExperienceLevelResponse::intoGrowthRateExperienceLevel)
                .collect(Collectors.toSet());
    }
}
