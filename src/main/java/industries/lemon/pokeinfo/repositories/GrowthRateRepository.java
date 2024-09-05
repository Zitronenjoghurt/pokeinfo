package industries.lemon.pokeinfo.repositories;

import industries.lemon.pokeinfo.entities.GrowthRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GrowthRateRepository extends JpaRepository<GrowthRate, Long> {
    Optional<GrowthRate> findByGrowthRateId(int growthRateId);
}
