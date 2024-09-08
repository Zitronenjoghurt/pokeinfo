package industries.lemon.pokeinfo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table
public class TcgSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(unique = true)
    private String setId;

    @Column
    private String name;

    @Column
    private String series;

    @Column
    private int printedTotal;

    @Column
    private int total;

    @Column
    private String ptcgoCode;

    @Column
    private String releaseDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private TcgSetImages images;
}
