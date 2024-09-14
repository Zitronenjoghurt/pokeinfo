package industries.lemon.pokeinfo.entities;

import industries.lemon.pokeinfo.interfaces.HasLanguage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "verbose_effect")
public class VerboseEffect implements HasLanguage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false, length = 16024)
    private String effect;

    @Column(nullable = false, length = 16024)
    private String shortEffect;

    @Column(nullable = false)
    private String language;
}
