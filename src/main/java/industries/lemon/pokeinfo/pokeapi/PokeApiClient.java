package industries.lemon.pokeinfo.pokeapi;

import industries.lemon.pokeinfo.pokeapi.models.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PokeApiClient {
    private final WebClient webClient;

    public PokeApiClient(
            WebClient.Builder webClientBuilder,
            @Value("${pokeapi.base-url}") String baseUrl,
            @Value("${pokeapi.client-buffer-size}") int clientBufferSize
    ) {
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(clientBufferSize))
                .build();

        this.webClient = webClientBuilder
                .baseUrl(baseUrl)
                .exchangeStrategies(strategies)
                .build();
    }

    public Mono<PokemonResponse> getPokemonById(int id) {
        return webClient.get()
                .uri("/pokemon/{id}", id)
                .retrieve()
                .bodyToMono(PokemonResponse.class);
    }

    public Mono<AbilityResponse> getAbilityById(int id) {
        return webClient.get()
                .uri("/ability/{id}", id)
                .retrieve()
                .bodyToMono(AbilityResponse.class);
    }

    public Mono<GenerationResponse> getGenerationById(int id) {
        return webClient.get()
                .uri("/generation/{id}", id)
                .retrieve()
                .bodyToMono(GenerationResponse.class);
    }

    public Mono<PokemonSpeciesResponse> getPokemonSpeciesById(int id) {
        return webClient.get()
                .uri("/pokemon-species/{id}", id)
                .retrieve()
                .bodyToMono(PokemonSpeciesResponse.class);
    }

    public Mono<GrowthRateResponse> getGrowthRateById(int id) {
        return webClient.get()
                .uri("/growth-rate/{id}", id)
                .retrieve()
                .bodyToMono(GrowthRateResponse.class);
    }
}
