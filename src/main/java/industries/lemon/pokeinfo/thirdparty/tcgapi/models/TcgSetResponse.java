package industries.lemon.pokeinfo.thirdparty.tcgapi.models;

import industries.lemon.pokeinfo.entities.TcgSet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TcgSetResponse {
    private String id;
    private String name;
    private String series;
    private int printedTotal;
    private int total;
    private String ptcgoCode;
    private String releaseDate;
    private TcgSetImagesResponse images;

    public TcgSet intoTcgSet() {
        TcgSet tcgSet = new TcgSet();
        tcgSet.setSetId(getId());
        tcgSet.setName(getName());
        tcgSet.setSeries(getSeries());
        tcgSet.setPrintedTotal(getPrintedTotal());
        tcgSet.setTotal(getTotal());
        tcgSet.setPtcgoCode(getPtcgoCode());
        tcgSet.setReleaseDate(getReleaseDate());
        tcgSet.setImages(getImages().intoTcgSetImages());
        return tcgSet;
    }
}
