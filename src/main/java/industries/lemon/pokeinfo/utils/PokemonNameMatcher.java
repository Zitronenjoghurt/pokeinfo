package industries.lemon.pokeinfo.utils;

import com.helger.commons.csv.CSVReader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PokemonNameMatcher {

    private final Map<Integer, Map<Integer, String>> speciesNameMap = new HashMap<>();
    private final Map<Integer, List<String>> speciesNamesByLanguage = new HashMap<>();
    private final Map<Integer, Map<String, Set<Integer>>> trigramIndex = new HashMap<>();

    public void loadData(Reader reader) throws IOException {
        try (CSVReader csvReader = new CSVReader(reader)) {
            csvReader.readNext(); // Skip header

            csvReader.forEach(line -> {
                if (line.size() >= 4) {
                    int speciesId = Integer.parseInt(line.get(0));
                    int languageId = Integer.parseInt(line.get(1));
                    String name = line.get(2);

                    speciesNameMap
                            .computeIfAbsent(speciesId, k -> new HashMap<>())
                            .put(languageId, name.toLowerCase());

                    speciesNamesByLanguage
                            .computeIfAbsent(languageId, k -> new ArrayList<>())
                            .add(name);

                    buildTrigramIndex(name, speciesId, languageId);
                }
            });
        }
    }

    private void buildTrigramIndex(String name, int speciesId, int languageId) {
        generateTrigrams(name).forEach(trigram ->
                trigramIndex
                        .computeIfAbsent(languageId, k -> new HashMap<>())
                        .computeIfAbsent(trigram, k -> new HashSet<>())
                        .add(speciesId)
        );
    }

    private Set<String> generateTrigrams(String input) {
        return input.length() < 3
                ? Set.of(input)
                : IntStream.rangeClosed(0, input.length() - 3)
                .mapToObj(i -> input.substring(i, i + 3))
                .collect(Collectors.toSet());
    }

    public List<String> findClosestMatches(String input, int languageId, int limit) {
        input = input.toLowerCase();
        Map<Integer, Long> candidateScores = generateTrigrams(input).stream()
                .flatMap(trigram -> trigramIndex
                        .getOrDefault(languageId, Collections.emptyMap())
                        .getOrDefault(trigram, Collections.emptySet())
                        .stream())
                .collect(Collectors.groupingBy(
                        i -> i,
                        Collectors.counting()
                ));

        return candidateScores.entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                .limit(limit)
                .map(entry -> speciesNameMap.get(entry.getKey()).get(languageId))
                .collect(Collectors.toList());
    }

    public int getSpeciesIdByName(String name, int languageId) {
        name = name.toLowerCase();
        String finalName = name;
        return speciesNameMap.entrySet().stream()
                .filter(entry -> finalName.equals(entry.getValue().get(languageId)))
                .mapToInt(Map.Entry::getKey)
                .findFirst()
                .orElse(-1);
    }

    public List<String> getNamesByLanguage(int languageId) {
        return speciesNamesByLanguage.get(languageId);
    }
}