package industries.lemon.pokeinfo.services;

import industries.lemon.pokeinfo.entities.BaseInitializableEntity;
import industries.lemon.pokeinfo.thirdparty.pokeapi.PokeApiClient;
import industries.lemon.pokeinfo.thirdparty.pokeapi.models.BaseEntityResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;
import java.util.function.Supplier;

public abstract class BaseInitializableEntityService<E extends BaseInitializableEntity, R extends JpaRepository<E, Long>, P extends BaseEntityResponse>
        extends BaseEntityService<E, R, P> {
    private final Supplier<E> entitySupplier;

    public BaseInitializableEntityService(
            PokeApiClient pokeApiClient,
            R repository,
            Supplier<E> entitySupplier
    ) {
        super(pokeApiClient, repository);
        this.entitySupplier = entitySupplier;
    }

    public E findByIdOrCreate(int id) {
        Optional<E> entity = findById(id);
        if (entity.isPresent()) {
            return entity.get();
        }

        synchronized (this) {
            entity = findById(id);
            if (entity.isPresent()) {
                return entity.get();
            }

            E newEntity = entitySupplier.get();
            newEntity.setEntityId(id);
            return repository.save(newEntity);
        }
    }

    public Mono<E> getInitialized(E entity) {
        if (entity.isInitialized()) {
            return Mono.just(entity);
        }

        return fetchData(entity.getEntityId())
                .publishOn(Schedulers.boundedElastic())
                .map(response -> {
                    pushDataFromResponse(entity, response);
                    repository.save(entity);
                    return entity;
                });
    }

    public Mono<E> findInitialized(int id) {
        return getInitialized(findByIdOrCreate(id));
    }

    @Override
    protected E fromResponse(P response) {
        E entity = entitySupplier.get();
        pushDataFromResponse(entity, response);
        return entity;
    }

    protected abstract void pushDataFromResponse(E entity, P response);
}
