package industries.lemon.pokeinfo.services;

import industries.lemon.pokeinfo.entities.Generation;
import industries.lemon.pokeinfo.repositories.GenerationRepository;
import org.springframework.stereotype.Service;

@Service
public class GenerationService {
    private final GenerationRepository generationRepository;

    public GenerationService(GenerationRepository generationRepository) {
        this.generationRepository = generationRepository;
    }

    public Generation findByIdOrCreate(int generationId) {
        return generationRepository.findByGenerationId(generationId)
                .orElseGet(() -> {
                    Generation newGeneration = new Generation();
                    newGeneration.setGenerationId(generationId);
                    generationRepository.save(newGeneration);
                    return newGeneration;
                });
    }
}
