package industries.lemon.pokeinfo.pokeapi.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NamedApiResource {
    private String name;
    private String url;

    public Integer getId() {
        if (url == null || url.isEmpty()) {
            return null;
        }
        String[] parts = url.split("/");
        String lastPart = parts[parts.length - 1];
        return lastPart.isEmpty() ? Integer.parseInt(parts[parts.length - 2]) : Integer.parseInt(lastPart);
    }
}
