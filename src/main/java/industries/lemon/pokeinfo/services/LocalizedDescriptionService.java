package industries.lemon.pokeinfo.services;

import industries.lemon.pokeinfo.entities.LocalizedDescription;
import industries.lemon.pokeinfo.pokeapi.models.DescriptionResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LocalizedDescriptionService {
    protected Set<LocalizedDescription> createLocalizedDescriptions(List<DescriptionResponse> descriptionResponses) {
        return descriptionResponses.stream()
                .map(DescriptionResponse::intoLocalizedDescription)
                .collect(Collectors.toSet());
    }
}
