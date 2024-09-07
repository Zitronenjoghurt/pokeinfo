package industries.lemon.pokeinfo.ui.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import industries.lemon.pokeinfo.ui.MainLayout;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Home | Pokedata")
@AnonymousAllowed
public class HomeView extends VerticalLayout {
    public HomeView() {
        add(new H1("Welcome to Pokedata.xyz!"));
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
    }
}
