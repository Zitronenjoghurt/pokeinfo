package industries.lemon.pokeinfo.ui.components;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Tag;

@Tag("audio")
public class AudioPlayer extends HtmlComponent {
    private String currentUrl;

    public AudioPlayer() {
        getElement().setAttribute("controls", true);
        getElement().setAttribute("preload", "auto");

        getStyle()
                .set("border-radius", "var(--lumo-border-radius-l)")
                .set("background-color", "var(--lumo-contrast-5pct)")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.2)");
        setWidth("100%");
    }

    public void update(String url) {
        if (!url.equals(currentUrl)) {
            currentUrl = url;
            getElement().setAttribute("src", url);
            getElement().setAttribute("type", "audio/ogg; codecs=vorbis");
            getElement().executeJs("this.load();");
        }
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        getElement().executeJs("this.volume = 0.1;");
    }
}
