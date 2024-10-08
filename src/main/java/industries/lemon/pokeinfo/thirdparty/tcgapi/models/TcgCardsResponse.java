package industries.lemon.pokeinfo.thirdparty.tcgapi.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class TcgCardsResponse extends TcgPaginationResponse {
    private Set<TcgCardResponse> data;
}
