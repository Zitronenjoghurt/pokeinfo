package industries.lemon.pokeinfo.repositories;

import industries.lemon.pokeinfo.entities.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
    Optional<Pokemon> findByPokemonId(int pokemonId);
}
