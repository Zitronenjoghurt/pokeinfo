package industries.lemon.pokeinfo.utils;

import com.helger.commons.csv.CSVReader;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AbilityNameLibrary {
    private final Map<Integer, Map<Integer, String>> abilityNameMap = new HashMap<>();
    private final Map<Integer, List<String>> abilityNamesByLanguage = new HashMap<>();

    @Getter
    private int maxId = 0;

    @Value("${pokemon.ability-names-csv-name}")
    private String csvFileName;

    @PostConstruct
    public void init() throws IOException {
        try (Reader reader = new InputStreamReader(new ClassPathResource(csvFileName).getInputStream())) {
            loadData(reader);
        }
    }

    public void loadData(Reader reader) throws IOException {
        try (CSVReader csvReader = new CSVReader(reader)) {
            csvReader.readNext();

            csvReader.forEach(line -> {
                if (line.size() >= 3) {
                    int abilityId = Integer.parseInt(line.get(0));
                    if (abilityId > maxId) {
                        maxId = abilityId;
                    }

                    int languageId = Integer.parseInt(line.get(1));
                    String name = line.get(2);

                    abilityNameMap
                            .computeIfAbsent(abilityId, k -> new HashMap<>())
                            .put(languageId, name);

                    abilityNamesByLanguage
                            .computeIfAbsent(languageId, k -> new ArrayList<>())
                            .add(name);
                }
            });
        }
    }

    public int getAbilityIdByName(String name, int languageId) {
        return abilityNameMap.entrySet().stream()
                .filter(entry -> name.equals(entry.getValue().get(languageId)))
                .mapToInt(Map.Entry::getKey)
                .findFirst()
                .orElse(-1);
    }

    public List<String> getNamesByLanguage(int languageId) {
        return abilityNamesByLanguage.get(languageId);
    }

    public String getNameByIdAndLanguage(int abilityId, int languageId) {
        Map<Integer, String> languageMap = abilityNameMap.get(abilityId);
        if (languageMap != null) {
            String name = languageMap.get(languageId);
            if (name != null) {
                return name;
            }
        }
        return "Unknown";
    }
}
