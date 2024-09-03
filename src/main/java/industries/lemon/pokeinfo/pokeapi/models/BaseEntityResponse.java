package industries.lemon.pokeinfo.pokeapi.models;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class BaseEntityResponse {
    private int id;
}
