package industries.lemon.pokeinfo.repositories;

import industries.lemon.pokeinfo.entities.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PokemonRepository extends JpaRepository<Pokemon, UUID> {
    Optional<Pokemon> findByPokemonId(int pokemonId);
}
