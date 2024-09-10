package industries.lemon.pokeinfo;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.AppShellSettings;
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

    @Override
    public void configurePage(AppShellSettings settings) {
        settings.addMetaTag("description", "Explore detailed information about Pokémon");
        settings.addMetaTag("keywords", "pokemon, pokedex, species, information");
        settings.addMetaTag("og:title", "Pokedata.xyz");
        settings.addMetaTag("og:description", "Your comprehensive Pokémon database");
        settings.addMetaTag("og:type", "website");
        settings.addMetaTag("theme-color", "#0085FD");
    }
}
