package industries.lemon.pokeinfo;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Push
@SpringBootApplication
@Theme(themeClass = Lumo.class, variant = Lumo.DARK)
public class PokeinfoApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(PokeinfoApplication.class, args);
    }

}
