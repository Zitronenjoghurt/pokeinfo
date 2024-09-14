package industries.lemon.pokeinfo.utils;

import com.vaadin.flow.server.WebBrowser;

public class BrowserSupport {
    public static boolean supportsOGG(WebBrowser browser) {
        // Most browsers support it: https://caniuse.com/?search=ogg
        return !browser.isSafari();
    }
}
