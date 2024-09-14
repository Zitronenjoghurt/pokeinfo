package industries.lemon.pokeinfo.entities;

import industries.lemon.pokeinfo.interfaces.HasLanguage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table
public class AbilityFlavorText implements HasLanguage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private String versionGroup;
}
