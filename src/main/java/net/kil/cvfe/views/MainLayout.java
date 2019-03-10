package net.kil.cvfe.views;

import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import net.kil.cvfe.views.about.AboutView;
import net.kil.cvfe.views.home.HomeView;
import net.kil.cvfe.views.project.ProjectView;

@HtmlImport("styles/shared-styles.html")
@Theme(value = Lumo.class)
@PWA(name = "Port-cv frontend application ", shortName = "Port-cv", enableInstallPrompt = false)
public class MainLayout extends FlexLayout implements RouterLayout {
    private final Menu menu;

    public MainLayout() {
        setSizeFull();
        setClassName("main-layout");

        menu = new Menu();
        menu.addView(HomeView.class, HomeView.VIEW_NAME);
        menu.addView(ProjectView.class, ProjectView.VIEW_NAME);
        menu.addView(AboutView.class, AboutView.VIEW_NAME);

        add(menu);
    }
}
