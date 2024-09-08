package industries.lemon.pokeinfo.services;

import industries.lemon.pokeinfo.entities.TcgAttack;
import industries.lemon.pokeinfo.entities.TcgCard;
import industries.lemon.pokeinfo.entities.TcgSet;
import industries.lemon.pokeinfo.repositories.TcgCardRepository;
import industries.lemon.pokeinfo.repositories.TcgSetRepository;
import industries.lemon.pokeinfo.thirdparty.tcgapi.TcgApiClient;
import industries.lemon.pokeinfo.thirdparty.tcgapi.models.TcgAttackResponse;
import industries.lemon.pokeinfo.thirdparty.tcgapi.models.TcgCardResponse;
import industries.lemon.pokeinfo.thirdparty.tcgapi.models.TcgCardsResponse;
import industries.lemon.pokeinfo.thirdparty.tcgapi.models.TcgSetResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TcgCardService extends BaseTcgEntityService<TcgCard, TcgCardRepository, TcgCardResponse> {
    private final TcgSetRepository tcgSetRepository;

    public TcgCardService(
            TcgApiClient tcgApiClient,
            TcgCardRepository repository,
            TcgSetRepository tcgSetRepository
    ) {
        super(tcgApiClient, repository);
        this.tcgSetRepository = tcgSetRepository;
    }

    public List<TcgCard> findCards(Integer nationalDex) {
        return repository.findByNationalPokedexNumber(nationalDex);
    }

    @Transactional
    public List<TcgCard> initializeCards(Integer nationalDex) {
        TcgCardsResponse response = tcgApiClient.getCardsByNationalDex(nationalDex).block();
        if (response == null) {
            return List.of();
        }

        return response.getData().stream()
                .map(cardResponse -> findById(cardResponse.getId())
                        .orElseGet(() -> {
                            TcgCard newCard = fromResponse(cardResponse);
                            return repository.save(newCard);
                        }))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TcgCard> findById(String id) {
        return repository.findByCardId(id);
    }

    @Override
    protected Mono<TcgCardResponse> fetchData(String id) {
        return tcgApiClient.getCardById(id);
    }

    @Override
    @Transactional
    protected TcgCard fromResponse(TcgCardResponse response) {
        TcgCard card = response.intoCard();
        card.setAttacks(createAttacks(response.getAttacks()));
        card.setImages(response.getImages().intoCardImages());
        card.setSet(findOrCreateSet(response.getSet()));
        return card;
    }

    protected Set<TcgAttack> createAttacks(Set<TcgAttackResponse> attacks) {
        return attacks.stream()
                .map(TcgAttackResponse::intoAttack)
                .collect(Collectors.toSet());
    }

    @Transactional
    protected TcgSet findOrCreateSet(TcgSetResponse response) {
        return tcgSetRepository.findBySetId(response.getId())
                .orElseGet(() -> tcgSetRepository.save(response.intoTcgSet()));
    }
}
