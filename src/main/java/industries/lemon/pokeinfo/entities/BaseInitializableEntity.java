package industries.lemon.pokeinfo.entities;

import industries.lemon.pokeinfo.thirdparty.pokeapi.models.BaseEntityResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseInitializableEntity<P extends BaseEntityResponse> extends BaseEntity {
    @Column(nullable = false)
    private boolean isInitialized = false;

    public void finishInitialization() {
        this.isInitialized = true;
    }

    public abstract void applyResponse(P response);
}
