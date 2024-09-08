package industries.lemon.pokeinfo.thirdparty.tcgapi.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import industries.lemon.pokeinfo.entities.TcgCardImages;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TcgCardImagesResponse {
    private String small;
    private String large;

    public TcgCardImages intoCardImages() {
        TcgCardImages cardImages = new TcgCardImages();
        cardImages.setSmall(getSmall());
        cardImages.setLarge(getLarge());
        return cardImages;
    }
}
