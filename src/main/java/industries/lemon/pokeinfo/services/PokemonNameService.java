package industries.lemon.pokeinfo.services;

import industries.lemon.pokeinfo.utils.PokemonNameMatcher;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Service
public class PokemonNameService {

    private final PokemonNameMatcher pokemonNameMatcher;
    private final String csvFilePath;
    private final ResourceLoader resourceLoader;

    public PokemonNameService(
        PokemonNameMatcher pokemonNameMatcher,
        @Value("${pokemon.names-csv-path}") String csvFilePath,
        ResourceLoader resourceLoader
    ) {
        this.pokemonNameMatcher = pokemonNameMatcher;
        this.csvFilePath = csvFilePath;
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    public void init() {
        try {
            Resource resource = resourceLoader.getResource(csvFilePath);
            try (Reader reader = new InputStreamReader(resource.getInputStream())) {
                pokemonNameMatcher.loadData(reader);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Pokemon name data", e);
        }
    }

    public List<String> findClosestMatches(String name, int languageId) {
        return pokemonNameMatcher.findClosestMatches(name, languageId, 5);
    }

    public List<String> getSpeciesNames(int languageId) {
        return pokemonNameMatcher.getNamesByLanguage(languageId);
    }

    public int getSpeciesIdByName(String name, int languageId) {
        return pokemonNameMatcher.getSpeciesIdByName(name, languageId);
    }

    public String getNameByIdAndLanguage(int speciesId, int languageId) {
        return getSpeciesNames(languageId).get(speciesId - 1);
    }
}
