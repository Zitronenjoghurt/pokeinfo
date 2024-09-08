package industries.lemon.pokeinfo.thirdparty.tcgapi;

import industries.lemon.pokeinfo.thirdparty.tcgapi.models.TcgCardsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TcgApiClient {
    private final WebClient webClient;

    public TcgApiClient(
            WebClient.Builder webClientBuilder,
            @Value("${tcgapi.base-url}") String baseUrl,
            @Value("${tcgapi.api-key}") String apiKey,
            @Value("${tcgapi.client-buffer-size}") int clientBufferSize
    ) {
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(clientBufferSize))
                .build();

        this.webClient = webClientBuilder
                .baseUrl(baseUrl)
                .defaultHeader("X-Api-Key", apiKey)
                .exchangeStrategies(strategies)
                .build();
    }

    public Mono<TcgCardsResponse> getCardsByNationalDex(int nationalDex) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/cards")
                        .queryParam("q", "nationalPokedexNumbers:" + nationalDex)
                        .build())
                .retrieve()
                .bodyToMono(TcgCardsResponse.class);
    }
}
