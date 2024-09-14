package industries.lemon.pokeinfo.entities;

import industries.lemon.pokeinfo.interfaces.HasLanguage;
import industries.lemon.pokeinfo.interfaces.IFlavorText;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "flavor_texts")
public class FlavorText implements HasLanguage, IFlavorText {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private String version;
}
