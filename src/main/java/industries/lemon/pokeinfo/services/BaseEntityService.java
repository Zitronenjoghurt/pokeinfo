package industries.lemon.pokeinfo.services;

import industries.lemon.pokeinfo.entities.BaseEntity;
import industries.lemon.pokeinfo.thirdparty.pokeapi.PokeApiClient;
import industries.lemon.pokeinfo.thirdparty.pokeapi.models.BaseEntityResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;

public abstract class BaseEntityService<E extends BaseEntity, R extends JpaRepository<E, Long>, P extends BaseEntityResponse> {
    protected final PokeApiClient pokeApiClient;
    protected final R repository;

    public BaseEntityService(
            PokeApiClient pokeApiClient,
            R repository) {
        this.pokeApiClient = pokeApiClient;
        this.repository = repository;
    }

    public Mono<E> fetch(int id) {
        return Mono.fromCallable(() -> findById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(optionalPokemon -> optionalPokemon
                        .map(Mono::just)
                        .orElseGet(() -> fetchAndSave(id)));
    }

    protected Mono<E> fetchAndSave(int id) {
        return fetchData(id)
                .publishOn(Schedulers.boundedElastic())
                .map(response -> {
                    E entity = fromResponse(response);
                    return repository.save(entity);
                });
    }

    public abstract Optional<E> findById(int id);
    protected abstract Mono<P> fetchData(int id);
    protected abstract E fromResponse(P response);
}
