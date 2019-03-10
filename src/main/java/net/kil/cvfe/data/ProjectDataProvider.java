package net.kil.cvfe.data;

import com.vaadin.flow.data.provider.ListDataProvider;
import net.kil.cvfe.restclient.api.ProjectControllerApi;
import net.kil.cvfe.restclient.model.ResourceProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProjectDataProvider extends ListDataProvider<ResourceProject> {
    /**
     * Text filter that can be changed separately.
     */
    private String filterText = "";

    @Autowired
    public ProjectDataProvider(ProjectControllerApi projectControllerApi) {
        super(projectControllerApi.allUsingGET2().getContent());
    }

    /**
     * Filter is compared for project name, customer name.
     * @param filterString the string to filter by
     */
    public void setFilter(String filterString) {
        Objects.requireNonNull(filterString, "Filter text cannot be null.");
        if (Objects.equals(this.filterText, filterString.trim())) {
            return;
        }
        this.filterText = filterString.trim().toLowerCase();

        setFilter(project -> project.getName() != null && project.getName().toLowerCase().contains(this.filterText)
                || project.getCustomer().getName() != null && project.getCustomer().getName().toLowerCase().contains(this.filterText)
        );
    }

}
