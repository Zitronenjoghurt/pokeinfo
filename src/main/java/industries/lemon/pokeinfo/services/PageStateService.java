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
    private boolean isShinyEnabled = false;
    private int primaryTypeId = 0;
    private int secondaryTypeId = 0;

    public int getType(boolean isPrimary) {
        if (isPrimary) {
            return getPrimaryTypeId();
        } else {
            return getSecondaryTypeId();
        }
    }
}
