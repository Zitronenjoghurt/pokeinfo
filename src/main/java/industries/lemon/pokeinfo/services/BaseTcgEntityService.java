package industries.lemon.pokeinfo.services;

import industries.lemon.pokeinfo.entities.BaseTcgEntity;
import industries.lemon.pokeinfo.thirdparty.tcgapi.TcgApiClient;
import industries.lemon.pokeinfo.thirdparty.tcgapi.models.BaseTcgEntityResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;

public abstract class BaseTcgEntityService<E extends BaseTcgEntity, R extends JpaRepository<E, Long>, P extends BaseTcgEntityResponse> {
    protected final TcgApiClient tcgApiClient;
    protected final R repository;

    public BaseTcgEntityService(
            TcgApiClient tcgApiClient,
            R repository) {
        this.tcgApiClient = tcgApiClient;
        this.repository = repository;
    }

    public Mono<E> fetch(String id) {
        return Mono.fromCallable(() -> findById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(optionalPokemon -> optionalPokemon
                        .map(Mono::just)
                        .orElseGet(() -> fetchAndSave(id)));
    }

    protected Mono<E> fetchAndSave(String id) {
        return fetchData(id)
                .publishOn(Schedulers.boundedElastic())
                .map(response -> {
                    E entity = fromResponse(response);
                    return repository.save(entity);
                });
    }

    public abstract Optional<E> findById(String id);
    protected abstract Mono<P> fetchData(String id);
    protected abstract E fromResponse(P response);
}
