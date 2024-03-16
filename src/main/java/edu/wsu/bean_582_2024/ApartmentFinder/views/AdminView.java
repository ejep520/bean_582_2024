package edu.wsu.bean_582_2024.ApartmentFinder.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import edu.wsu.bean_582_2024.ApartmentFinder.service.AuthService;
import edu.wsu.bean_582_2024.ApartmentFinder.service.UserService;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("User Administration")
@Route(value = "admin", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class AdminView extends VerticalLayout {
  private final Grid<User> grid = new Grid<>(User.class, false);
  private final TextField filterText = new TextField("Filter");
  private AdminForm adminForm;
  private final UserService userService;
  private final AuthService authService;
  private Boolean isNewUser = null;
  public AdminView(UserService userService, AuthService authService) {
    this.userService = userService;
    this.authService = authService;
    addClassName("admin-view");
    setSizeFull();

    configureGrid();
    configureForm();

    add(getToolbar(), getContent());
    updateList();
    closeEditor();
  }

  public Grid<User> getGrid() {
    return grid;
  }

  public AdminForm getAdminForm() {
    return adminForm;
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
    grid.getColumns().forEach(col -> col.setAutoWidth(true));
    grid.asSingleSelect().addValueChangeListener(event -> editUser(event.getValue()));
    grid.setItems(userService.getAllUsers());
  }

  private void editUser(User user) {
    if (user == null) {
      closeEditor();
      return;
    }
    isNewUser = user.getId() == null || user.getId() <= 0;
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
    if (isNewUser != null) {
      if (isNewUser) authService.register(event.getUser());
      else userService.saveUser(event.getUser());
    }
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
    isNewUser = null;
  }

  private Component getToolbar() {
    filterText.setPlaceholder("Filter by username");
    filterText.setClearButtonVisible(true);
    filterText.setValueChangeMode(ValueChangeMode.LAZY);
    filterText.addValueChangeListener(event -> updateList());
    Button addUserButton = new Button("Add User");
    addUserButton.addClickListener(click -> addUser());
    HorizontalLayout toolbar = new HorizontalLayout(filterText, addUserButton);
    toolbar.addClassName("toolbar");
    return toolbar;
  }

  private void addUser() {
    grid.asSingleSelect().clear();
    editUser(new User());
  }
}
