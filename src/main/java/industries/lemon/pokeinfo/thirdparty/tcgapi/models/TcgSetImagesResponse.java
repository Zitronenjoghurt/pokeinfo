package industries.lemon.pokeinfo.thirdparty.tcgapi.models;

import industries.lemon.pokeinfo.entities.TcgSetImages;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TcgSetImagesResponse {
    private String symbol;
    private String logo;

    public TcgSetImages intoTcgSetImages() {
        TcgSetImages images = new TcgSetImages();
        images.setSymbol(getSymbol());
        images.setLogo(getLogo());
        return images;
    }
}
