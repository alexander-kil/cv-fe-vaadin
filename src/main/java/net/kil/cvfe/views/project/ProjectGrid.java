package net.kil.cvfe.views.project;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import net.kil.cvfe.restclient.model.ResourceProject;
import net.kil.cvfe.restclient.model.UsedSkill;

import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Grid of products, handling the visual presentation and filtering of a set of
 * items. This version uses an in-memory data source that is suitable for small
 * data sets.
 */
public class ProjectGrid extends Grid<ResourceProject> {

    private Integer totalMonth;

    public void setExtraData(final Integer totalMonth) {
        this.totalMonth = totalMonth;
    }

    public ProjectGrid() {
        setSizeFull();

        addColumn(this::formatProjectPeriod)
                .setHeader("Period")
                .setFlexGrow(20)
                .setSortable(true);

        addColumn(ResourceProject::getName)
                .setHeader("Project")
                .setFlexGrow(80);

        setItemDetailsRenderer(TemplateRenderer.<ResourceProject>of(
                "<div style='border: 1px solid gray; padding: 10px; width: 100%; box-sizing: border-box;'>"
                        + "<div>Skills in Project: [[item.skillList]]</div>"
                        + "</div>")
                .withProperty("skillList", ProjectGrid::formatSkills)
                .withEventHandler("handleClick", getDataProvider()::refreshItem));
    }

    private String formatProjectPeriod(ResourceProject project) {
        return project.getStartedAt().format(DateTimeFormatter.ofPattern("YYYY MMM")) + " +" + project.getMonths().toString() + getTranslation("projectGrid.monthShort");
    }

    public void refresh(ResourceProject project) {
        getDataCommunicator().refresh(project);
    }

    private static String formatSkills(ResourceProject project) {
        if (project.getMembers() == null || project.getMembers().isEmpty()) {
            return "";
        }

        Set<UsedSkill> usedSkills = new HashSet<>();

        project.getMembers().forEach(member -> usedSkills.addAll(member.getSkills()));

        return usedSkills.stream().map(UsedSkill::getName).collect(Collectors.joining(", "));
    }
}
