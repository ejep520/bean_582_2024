package edu.wsu.bean_582_2024.ApartmentFinder.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.Tabs.Orientation;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import java.util.Optional;

public class MainView extends AppLayout {
  private final Tabs menu;
  private H1 viewTitle;
  public MainView() {
    setPrimarySection(Section.DRAWER);
    addToNavbar(true, createHeaderContent());
    menu = createMenu();
    addToDrawer(createDrawerContent(), menu);
  }
  private Component createHeaderContent() {
    HorizontalLayout layout = new HorizontalLayout();
    layout.setId("header");
    layout.getThemeList().set("dark", true);
    layout.setWidthFull();
    layout.setSpacing(false);
    layout.setAlignItems(Alignment.CENTER);
    viewTitle = new H1();
    layout.add(viewTitle);
    layout.add(new Image("images/user.svg", "Avatar"));
    return layout;
  }
  private Component createDrawerContent() {
    VerticalLayout layout = new VerticalLayout();
    layout.setSizeFull();
    layout.setPadding(false);
    layout.setSpacing(false);
    layout.getThemeList().set("spacing-s", true);
    layout.setAlignItems(Alignment.STRETCH);
    HorizontalLayout logoLayout = new HorizontalLayout();
    logoLayout.setId("logo");
    logoLayout.setAlignItems(Alignment.CENTER);
    logoLayout.add(new Image("images/logo.png", "the logo"));
    logoLayout.add(new H1("The Project"));
    layout.add(logoLayout, menu);
    return layout;
  }
  private Tabs createMenu() {
    final Tabs tabs = new Tabs();
    tabs.setOrientation(Orientation.VERTICAL);
    tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
    tabs.setId("tabs");
    tabs.add(createMenuItems());
    return tabs;
  }
  private Component[] createMenuItems() {
    return new Tab[] { createTab("HelloWorld", HomeView.class)};
  }
  private static Tab createTab(String text, Class<? extends Component> navigationalTarget) {
    final Tab tab = new Tab();
    tab.add(new RouterLink(text, navigationalTarget));
    ComponentUtil.setData(tab, Class.class, navigationalTarget);
    return tab;
  }
  @Override
  protected void afterNavigation() {
    super.afterNavigation();
    getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);
    viewTitle.setText(getCurrentPageTitle());
  }
  private Optional<Tab> getTabForComponent(Component component) {
    return menu.getChildren().filter(tab ->
        ComponentUtil.getData(tab, Class.class)
            .equals(component.getClass()))
        .findFirst().map(Tab.class::cast);
  }
  private String getCurrentPageTitle() {
    return getContent().getClass().getAnnotation(PageTitle.class).value();
  }
  
}
