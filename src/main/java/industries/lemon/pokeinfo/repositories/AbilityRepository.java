package industries.lemon.pokeinfo.repositories;

import industries.lemon.pokeinfo.entities.Ability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AbilityRepository extends JpaRepository<Ability, Long> {
    Optional<Ability> findByAbilityId(int abilityId);
}
