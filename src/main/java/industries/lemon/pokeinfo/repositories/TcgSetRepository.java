package industries.lemon.pokeinfo.repositories;

import industries.lemon.pokeinfo.entities.TcgSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TcgSetRepository extends JpaRepository<TcgSet, Integer> {
    Optional<TcgSet> findBySetId(String setId);
}