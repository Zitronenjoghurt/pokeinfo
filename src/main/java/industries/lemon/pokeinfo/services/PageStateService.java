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
    private Integer type1 = null;
    private Integer type2 = null;

    public Integer getType(boolean isFirst) {
        if (isFirst) {
            return getType1();
        } else {
            return getType2();
        }
    }
}
