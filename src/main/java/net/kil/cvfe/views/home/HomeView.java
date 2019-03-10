package net.kil.cvfe.views.home;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import net.kil.cvfe.views.MainLayout;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Home")
public class HomeView extends HorizontalLayout {

    public static final String VIEW_NAME = "Home";

    public HomeView() {
        add(VaadinIcon.INFO_CIRCLE.create());
        add(new Span(" This is home Page"));

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
    }
}
