package edu.wsu.bean_582_2024.ApartmentFinder.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.service.UnitService;

@PageTitle("Unit Viewing | Bean 582")
@Route(value="/home", layout = MainLayout.class)
public class HomeView extends VerticalLayout {
  private final UnitService unitService;
  private final TextField filterText = new TextField("Filter");
  private final Grid<Unit> grid = new Grid<>(Unit.class, false);
  public HomeView(UnitService unitService) {
    this.unitService = unitService;
    addClassName("home-view");
    setSizeFull();
    configureGrid();
    add(getToolbar(), getContent());
  }
  private Component getContent() {
    HorizontalLayout content = new HorizontalLayout(grid);
    content.setFlexGrow(2, grid);
    content.addClassNames("content");
    content.setSizeFull();

    return content;
  }

  private void configureGrid() {
    grid.setClassName("owner-grid");
    grid.setSizeFull();
    grid.setColumns("address", "bedrooms", "bathrooms", "kitchen", "living room", "featured");
    grid.addColumn(Unit::getAddress).setHeader("Address");
    grid.addColumn(Unit::getBedrooms).setHeader("Bedrooms");
    grid.addColumn(Unit::getBathrooms).setHeader("Bathrooms");
    grid.addColumn(Unit::getKitchen).setHeader("Kitchen");
    grid.addColumn(Unit::getLivingRoom).setHeader("Living Room");
    grid.addColumn(Unit::getFeatured).setHeader("Featured");
    grid.getColumns().forEach(col -> col.setAutoWidth(true));
    grid.setItems(unitService.getAllUnits());
  }
  private void updateList() {
    grid.setItems(unitService.findUnits(filterText.getValue()));
  }
  private Component getToolbar() {
    filterText.setPlaceholder("Filter by username");
    filterText.setClearButtonVisible(true);
    filterText.setValueChangeMode(ValueChangeMode.LAZY);
    filterText.addValueChangeListener(event -> updateList());
    HorizontalLayout toolbar = new HorizontalLayout(filterText);
    toolbar.addClassName("toolbar");
    return toolbar;
  }
}
