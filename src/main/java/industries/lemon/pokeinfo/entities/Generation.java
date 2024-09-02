package industries.lemon.pokeinfo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "generations")
public class Generation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false, updatable = false)
    private int generationId;

    @Column(nullable = false)
    private boolean isInitialized = false;

    @OneToMany(mappedBy = "generation")
    @JsonIgnore
    private Set<Ability> abilities = new HashSet<>();
}
