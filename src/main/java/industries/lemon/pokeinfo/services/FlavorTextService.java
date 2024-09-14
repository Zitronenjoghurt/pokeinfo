package industries.lemon.pokeinfo.services;

import industries.lemon.pokeinfo.entities.FlavorText;
import industries.lemon.pokeinfo.thirdparty.pokeapi.models.FlavorTextResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FlavorTextService {
    protected Set<FlavorText> createFlavorTexts(List<FlavorTextResponse> flavorTextResponses) {
        return flavorTextResponses.stream()
                .map(FlavorTextResponse::intoFlavorText)
                .collect(Collectors.toSet());
    }
}
