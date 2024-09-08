package industries.lemon.pokeinfo.repositories;

import industries.lemon.pokeinfo.entities.TcgCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TcgCardRepository extends JpaRepository<TcgCard, Long> {
    Optional<TcgCard> findByCardId(String cardId);

    @Query("SELECT c FROM TcgCard c WHERE :nationalDex MEMBER OF c.nationalPokedexNumbers")
    List<TcgCard> findByNationalPokedexNumber(@Param("nationalDex") Integer nationalDex);
}
