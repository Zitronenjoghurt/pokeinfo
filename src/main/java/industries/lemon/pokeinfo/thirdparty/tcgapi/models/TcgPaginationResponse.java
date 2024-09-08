package industries.lemon.pokeinfo.thirdparty.tcgapi.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TcgPaginationResponse {
    private int page;
    private int pageSize;
    private int count;
    private int totalCount;
}
