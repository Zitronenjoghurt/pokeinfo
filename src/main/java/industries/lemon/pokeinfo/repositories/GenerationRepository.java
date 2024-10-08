package industries.lemon.pokeinfo.repositories;

import industries.lemon.pokeinfo.entities.Generation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenerationRepository extends JpaRepository<Generation, Long> {
    Optional<Generation> findByGenerationId(int generationId);
}
