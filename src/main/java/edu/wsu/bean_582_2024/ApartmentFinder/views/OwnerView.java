package edu.wsu.bean_582_2024.ApartmentFinder.views;

import org.springframework.context.annotation.Scope;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;

import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.service.UnitService;
import jakarta.annotation.security.RolesAllowed;

@SpringComponent
@Scope("prototype")
@SuppressWarnings("serial")
@Route(value = "/owner", layout = MainLayout.class)
@PageTitle("Unit Administration | Bean 582")
@RolesAllowed({"OWNER", "ADMIN"})
public class OwnerView extends VerticalLayout {
  private final UnitService unitService;
  private OwnerForm ownerForm;
  private final TextField filterText = new TextField("Filter");
  private final Grid<Unit> grid = new Grid<>(Unit.class, false);

  public OwnerView(UnitService unitService) {
    this.unitService = unitService;
    addClassName("owner-view");
    setSizeFull();
    configureGrid();
    configureForm();
    add(getToolbar(), getContent());
    updateList();
    closeEditor();
  }

  private Component getContent() {
    HorizontalLayout content = new HorizontalLayout(getGrid(), getOwnerForm());
    content.setFlexGrow(2, getGrid());
    content.setFlexGrow(1, getOwnerForm());
    content.addClassNames("content");
    content.setSizeFull();

    return content;
  }

  private void configureGrid() {
    getGrid().setClassName("owner-grid");
    getGrid().setSizeFull();
    getGrid().setColumns("address", "bedrooms", "bathrooms", "kitchen", "livingRoom", "featured");
    getGrid().asSingleSelect().addValueChangeListener(event -> editUnit(event.getValue()));
    getGrid().setItems(unitService.getAllUnits());
  }

  private void editUnit(Unit unit) {
    if (unit == null) {
      closeEditor();
      return;
    }
    getOwnerForm().setUnit(unit);
    getOwnerForm().setVisible(true);
    addClassName("editing");
  }

  private void configureForm() {
    ownerForm = new OwnerForm();
    getOwnerForm().setWidth("25em");
    getOwnerForm().addSaveListener(this::saveUnit);
    getOwnerForm().addDeleteListener(this::deleteUnit);
    getOwnerForm().addCloseListener(e -> closeEditor());
  }

  private void saveUnit(OwnerForm.SaveEvent event) {
    unitService.saveUnit(event.getUnit());
    updateList();
    closeEditor();
    updateList();
    closeEditor();
  }

  private void deleteUnit(OwnerForm.DeleteEvent event) {
    unitService.deleteUnit(event.getUnit());
    updateList();
    closeEditor();
    updateList();
    closeEditor();
  }

  private void updateList() {
    getGrid().setItems(unitService.findUnits(filterText.getValue()));
  }

  private void closeEditor() {
    getOwnerForm().setUnit(null);
    getOwnerForm().setVisible(false);
    removeClassName("editing");
  }

  private Component getToolbar() {
    filterText.setPlaceholder("Filter");
    filterText.setClearButtonVisible(true);
    filterText.setValueChangeMode(ValueChangeMode.LAZY);
    filterText.addValueChangeListener(event -> updateList());
    Button addButton = new Button("Add Unit");
    addButton.addClickListener(event -> editUnit(new Unit()));
    HorizontalLayout toolbar = new HorizontalLayout(filterText, addButton);
    toolbar.addClassName("toolbar");
    return toolbar;
  }

/**
 * @return the ownerForm
 */
public OwnerForm getOwnerForm() {
	return ownerForm;
}

/**
 * @return the grid
 */
public Grid<Unit> getGrid() {
	return grid;
}
}
