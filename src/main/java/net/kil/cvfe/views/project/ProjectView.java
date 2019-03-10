package net.kil.cvfe.views.project;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import net.kil.cvfe.data.ProjectDataProvider;
import net.kil.cvfe.restclient.model.ResourceProject;
import net.kil.cvfe.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Route(value = "Projects", layout = MainLayout.class)
@PageTitle("Projects")
public class ProjectView extends VerticalLayout {

    public static final String VIEW_NAME = "Projects";
    private final ProjectGrid grid;
    private TextField filter;

    @Autowired
    private ProjectDataProvider dataProvider;

    public ProjectView() {
        setSizeFull();

        HorizontalLayout topLayout = createTopBar();

        grid = new ProjectGrid();
        grid.setSelectionMode(Grid.SelectionMode.NONE);

        VerticalLayout barAndGridLayout = new VerticalLayout();
        barAndGridLayout.add(topLayout);
        barAndGridLayout.add(grid);
        barAndGridLayout.setFlexGrow(1, grid);
        barAndGridLayout.setFlexGrow(0, topLayout);
        barAndGridLayout.setSizeFull();
        barAndGridLayout.setWidth("90%");
        barAndGridLayout.setAlignSelf(Alignment.CENTER);
        barAndGridLayout.expand(grid);

        add(barAndGridLayout);
        setHorizontalComponentAlignment(Alignment.CENTER, barAndGridLayout);
    }

    @PostConstruct
    private void bindToDataProvider() {
        grid.setDataProvider(dataProvider);
        // calculate aggregate datas for view such as totals etc
        grid.setExtraData(dataProvider.getItems().stream().mapToInt(ResourceProject::getMonths).sum());
    }

    public HorizontalLayout createTopBar() {
        filter = new TextField();
        filter.setPlaceholder("Filter by name, skill or customer");
        filter.addValueChangeListener(event -> dataProvider.setFilter(event.getValue()));

        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.add(filter);
        topLayout.setVerticalComponentAlignment(Alignment.START, filter);
        topLayout.expand(filter);
        return topLayout;
    }
}
