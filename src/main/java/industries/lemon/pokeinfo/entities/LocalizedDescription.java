package industries.lemon.pokeinfo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "localized_descriptions")
public class LocalizedDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false, length = 16024)
    private String description;

    @Column(nullable = false)
    private String language;
}
