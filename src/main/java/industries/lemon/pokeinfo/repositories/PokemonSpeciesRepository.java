package industries.lemon.pokeinfo.repositories;

import industries.lemon.pokeinfo.entities.PokemonSpecies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PokemonSpeciesRepository extends JpaRepository<PokemonSpecies, Long> {
    Optional<PokemonSpecies> findBySpeciesId(int speciesId);
}