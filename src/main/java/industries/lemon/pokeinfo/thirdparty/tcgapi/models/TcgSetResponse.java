package industries.lemon.pokeinfo.thirdparty.tcgapi.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TcgSetResponse {
    private String id;
    private String name;
    private String series;
    private int printedTotal;
    private int total;
    private String ptcgoCode;
    private String releaseDate;
    private TcgSetImagesResponse images;
}
