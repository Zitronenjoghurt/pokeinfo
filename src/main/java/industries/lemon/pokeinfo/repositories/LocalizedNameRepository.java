package industries.lemon.pokeinfo.repositories;

import industries.lemon.pokeinfo.entities.LocalizedName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalizedNameRepository extends JpaRepository<LocalizedName, Long> {}
