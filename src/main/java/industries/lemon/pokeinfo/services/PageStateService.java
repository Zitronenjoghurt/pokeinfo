package industries.lemon.pokeinfo.services;

import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
@VaadinSessionScope
public class PageStateService {
    private int currentSpeciesId = 1;
}
