package industries.lemon.pokeinfo.services;

import industries.lemon.pokeinfo.entities.LocalizedName;
import industries.lemon.pokeinfo.pokeapi.models.LocalizedNameResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LocalizedNameService {
    protected Set<LocalizedName> createLocalizedNames(List<LocalizedNameResponse> localizedNameResponses) {
        return localizedNameResponses.stream()
                .map(LocalizedNameResponse::intoLocalizedName)
                .collect(Collectors.toSet());
    }
}
