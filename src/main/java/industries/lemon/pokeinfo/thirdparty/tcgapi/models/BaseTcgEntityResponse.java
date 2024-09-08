package industries.lemon.pokeinfo.thirdparty.tcgapi.models;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class BaseTcgEntityResponse {
    private String id;
}
