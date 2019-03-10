package net.kil.cvfe;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class implementing the I18NProvider for translations with caching.
 */
@SpringComponent
public class LocaleManager implements I18NProvider {

    public static final Locale LOCALE_US = new Locale("en", "US");
    public static final Locale LOCALE_DE = new Locale("de", "DE");
    public static final Locale LOCALE_RU = new Locale("ru", "RU");

    final List<Locale> providedLocales = Collections
            .unmodifiableList(Arrays.asList(LOCALE_US, LOCALE_DE, LOCALE_RU));

    public static final String BUNDLE_PREFIX = "translations";

    private static final LoadingCache<Locale, ResourceBundle> bundleCache = CacheBuilder
            .newBuilder().expireAfterWrite(1, TimeUnit.DAYS)
            .build(new CacheLoader<Locale, ResourceBundle>() {

                @Override
                public ResourceBundle load(final Locale locale)
                        throws Exception {
                    return readProperties(locale);
                }
            });

    @Override
    public List<Locale> getProvidedLocales() {
        return providedLocales;
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {
        if (key == null) {
            Logger.getLogger(LocaleManager.class.getName()).log(Level.WARNING,
                    "Translation for key with null value!");
            return "";
        }

        final ResourceBundle bundle = bundleCache.getUnchecked(locale);

        String value;
        try {
            value = bundle.getString(key);
            value = new String(value.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        } catch (final MissingResourceException e) {
            Logger.getLogger(LocaleManager.class.getName()).log(Level.WARNING,
                    "Missing resource", e);
            return "!" + locale.getLanguage() + ": " + key;
        }
        if (params.length > 0) {
            value = MessageFormat.format(value, params);
        }
        return value;
    }

    protected static ResourceBundle readProperties(final Locale locale) {
        final ClassLoader cl = LocaleManager.class.getClassLoader();

        try {
            return ResourceBundle.getBundle(BUNDLE_PREFIX, locale, cl);
        } catch (final MissingResourceException e) {
            Logger.getLogger(LocaleManager.class.getName()).log(Level.WARNING,
                    "Missing resource", e);
        }
        return null;
    }
}
