package industries.lemon.pokeinfo.repositories;

import industries.lemon.pokeinfo.entities.Ability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AbilityRepository extends JpaRepository<Ability, UUID> {
    Optional<Ability> findByAbilityId(int abilityId);
}
