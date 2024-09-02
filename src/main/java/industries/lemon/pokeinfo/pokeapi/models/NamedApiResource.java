package industries.lemon.pokeinfo.pokeapi.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
