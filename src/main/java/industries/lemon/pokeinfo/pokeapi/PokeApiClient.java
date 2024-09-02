package industries.lemon.pokeinfo.pokeapi;

import industries.lemon.pokeinfo.pokeapi.models.PokemonResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PokeApiClient {
    private final WebClient webClient;


    public PokeApiClient(
            WebClient.Builder webClientBuilder,
            @Value("${pokeapi.base-url}") String baseUrl
    ) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public Mono<PokemonResponse> getPokemonById(int id) {
        return webClient.get()
                .uri("/pokemon/{id}", id)
                .retrieve()
                .bodyToMono(PokemonResponse.class);
    }
}
