package industries.lemon.pokeinfo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "verbose_effect")
public class VerboseEffect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private long id;

    @Column(nullable = false)
    private String effect;

    @Column(nullable = false)
    private String shortEffect;

    @Column(nullable = false)
    private String language;
}
