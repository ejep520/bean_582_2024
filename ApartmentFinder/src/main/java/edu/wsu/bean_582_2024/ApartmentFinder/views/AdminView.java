package edu.wsu.bean_582_2024.ApartmentFinder.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import edu.wsu.bean_582_2024.ApartmentFinder.service.UserService;

@PageTitle("User Admin | Bean 582")
@Route(value="admin",layout = MainLayout.class)
public class AdminView extends VerticalLayout {
  private Grid<User> grid = new Grid<>(User.class, false);
  private TextField filterText = new TextField("Filter");
  private AdminForm adminForm;
  private UserService userService;
  
  public AdminView(UserService userService) {
    this.userService = userService;
    addClassName("admin-view");
    setSizeFull();
    
    configureGrid();
    configureForm();
    
    add(getToolbar(), getContent());
    updateList();
    closeEditor();
  }
  private Component getContent() {
    HorizontalLayout content = new HorizontalLayout(grid, adminForm);
    content.setFlexGrow(2, grid);
    content.setFlexGrow(1, adminForm);
    content.addClassNames("content");
    content.setSizeFull();

    return content;
  }

  private void configureGrid() {
    grid.setClassName("user-grid");
    grid.setSizeFull();
    grid.setColumns("username", "role", "enabled");
    grid.addColumn(User::getUsername).setHeader("Username");
    grid.addColumn(User::getRole).setHeader("Role");
    grid.addColumn(User::getEnabled).setHeader("Enabled");
    grid.getColumns().forEach(col -> col.setAutoWidth(true));
    grid.asSingleSelect().addValueChangeListener(event -> editUser(event.getValue()));
    grid.setItems(userService.getAllUsers());
  }
  
  private void editUser(User user) {
    if (user == null) {
      closeEditor();
      return;
    }
    adminForm.setUser(user);
    adminForm.setVisible(true);
    addClassName("editing");
  }
  
  private void configureForm() {
    adminForm = new AdminForm();
    adminForm.setWidth("25em");
    adminForm.addSaveListener(this::saveUser); // <1>
    adminForm.addDeleteListener(this::deleteUser); // <2>
    adminForm.addCloseListener(e -> closeEditor()); // <3>
  }
  
  private void saveUser(AdminForm.SaveEvent event) {
    userService.saveUser(event.getUser());
    updateList();
    closeEditor();
    updateList();
    closeEditor();
  }
  private void deleteUser(AdminForm.DeleteEvent event) {
    userService.deleteUser(event.getUser());
    updateList();
    closeEditor();
    updateList();
    closeEditor();
  }
  private void updateList() {
    grid.setItems(userService.findUsers(filterText.getValue()));
  }
  private void closeEditor() {
    adminForm.setUser(null);
    adminForm.setVisible(false);
    removeClassName("editing");
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
