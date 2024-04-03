package edu.wsu.bean_582_2024.ApartmentFinder.views;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.value.ValueChangeMode;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.service.UnitService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class HomeViewTests {
	
    static {
        // Prevent Vaadin Development mode to launch browser window
        System.setProperty("vaadin.launch-browser", "false");
    }
    private HomeView homeView;
    @Mock
    private UnitService unitService;
    private final static String ONE_HUNDRED_PERCENT = "100%";

    private record Children(HorizontalLayout toolbar, HorizontalLayout content){ }
    
    @Test
    public void testGridSetupWithEmptyUnitList() {
      	when(unitService.getAllUnits(true)).thenReturn(Collections.emptyList());
        homeView = new HomeView(unitService);
        Grid<Unit> grid = homeView.getGrid();
  
        assertEquals(0, grid.getDataProvider().size(new Query<>())); // Ensure the grid is empty
    }

    @BeforeEach
    public void setup() {
        Unit unit1 = new Unit();
        unit1.setAddress("Address1");
        unit1.setBedrooms(2);
        unit1.setBathrooms(1.0);
        unit1.setKitchen("Gas Stove, Dishwasher");
        unit1.setLivingRoom("Coffee Table");
        unit1.setFeatured(false);

        Unit unit2 = new Unit();
        unit2.setAddress("Address2");
        unit2.setBedrooms(3);
        unit2.setBathrooms(2.5);
        unit2.setKitchen("Electric Stove");
        unit2.setLivingRoom("Table and Chairs, Coffee Table and End tables");
        unit2.setFeatured(true);

        List<Unit> units = List.of(unit1, unit2);

        // Stubbing leniently
        lenient().when(unitService.getAllUnits(true)).thenReturn(units);
        lenient().when(unitService.findUnits(anyString())).thenReturn(units);

        homeView = new HomeView(unitService);
    }

    @Test
    public void homeViewPropertiesTest() {
        assertEquals("home-view", homeView.getClassName());
        assertEquals(ONE_HUNDRED_PERCENT, homeView.getWidth());
        assertEquals(ONE_HUNDRED_PERCENT, homeView.getHeight());
    }

    @Test
    public void testGridSetup() {
        Grid<Unit> grid = homeView.getGrid();

        assertNotNull(grid);
        assertEquals(6, grid.getColumns().size()); // Ensure all columns are present
        assertEquals("address", grid.getColumns().get(0).getKey());
        assertEquals("bedrooms", grid.getColumns().get(1).getKey());
        assertEquals("bathrooms", grid.getColumns().get(2).getKey());
        assertEquals("kitchen", grid.getColumns().get(3).getKey());
        assertEquals("livingRoom", grid.getColumns().get(4).getKey());
        assertEquals("featured", grid.getColumns().get(5).getKey());

        assertEquals(2, grid.getDataProvider().size(new Query<>())); // Ensure correct number of items are present
        assertEquals("owner-grid", grid.getClassName());
        assertEquals(ONE_HUNDRED_PERCENT, grid.getWidth());
        assertEquals(ONE_HUNDRED_PERCENT, grid.getHeight());
        for (Column<Unit> column : grid.getColumns())
            assertTrue(column.isAutoWidth());
        Children children = getChildren();
        assertEquals(2d, children.content.getFlexGrow(grid));
        assertEquals("content", children.content.getClassName());
        assertEquals(ONE_HUNDRED_PERCENT, children.content.getHeight());
        assertEquals(ONE_HUNDRED_PERCENT, children.content.getWidth());
        TextField filterText = (TextField) children.toolbar.getChildren().findFirst()
            .orElseThrow();
        assertEquals("Filter by address", filterText.getPlaceholder());
        assertTrue(filterText.isClearButtonVisible());
        assertEquals(ValueChangeMode.LAZY, filterText.getValueChangeMode());
    }
    
    @Test
    void testConstructor() {
        assertNotNull(homeView);
        assertNotNull(homeView.getGrid());
    }
 
    @Test
    void testGetGrid() {
        assertNotNull(homeView.getGrid());
    }
    
    @Test
    public void testGridSetupWithNonEmptyUnitList() {
        Grid<Unit> grid = homeView.getGrid();

        // Ensure that the grid contains the same number of items as the list of units
        assertEquals(2, grid.getDataProvider().size(new Query<>()));
    }
    
    
    /* Test will pass if filter Text and updateList() are changed to packages instead of private
    // I am unable to find a workaround for when they are kept as private.
    // @Test
    public void testUpdateListWithEmptyFilterText() {
        String emptyFilterText = "";

        homeView.filterText.setValue(emptyFilterText);// filter text is private
        homeView.updateList();// update list is private

        // Verify that unitService.findUnits("") is called
        verify(unitService).findUnits(emptyFilterText);
    }*/
    

    @Test
    public void testFormNotShownWhenUnitIsDeleted() {
        
        Unit unitToDelete = new Unit(); // Create a mock unit to delete
        Grid<Unit> grid = homeView.getGrid();
        
        grid.setItems(unitToDelete); // Set the unit in the grid

        grid.setItems();

        assertEquals(0, grid.getDataProvider().size(new Query<>())); // Ensure no items in the grid
    }
    
    @Test
    public void testFormNotShownWhenUnitIsCancelled() {
        
        Unit unitToCancel = new Unit(); // Create a mock unit to cancel
        Grid<Unit> grid = homeView.getGrid();
        grid.setItems(unitToCancel); // Set the unit in the grid

        
        grid.setItems(); // Clear items in the grid (simulating cancellation)

        
        assertEquals(0, grid.getDataProvider().size(new Query<>())); // Ensure no items in the grid
    }
       
    
    /*
    //testFilterTextPlaceholder - future tests
    //testFilterTextClearButtonVisibility - future tests
    //testFilterTextValueChangeListener - future tests
    */
    
    private Children getChildren() {
        HorizontalLayout toolbar, content;
        List<Component> childrenList = homeView.getChildren().toList();
        toolbar = (HorizontalLayout) childrenList.stream()
            .filter(e -> "toolbar".equals(e.getClassName()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Unable to find the toolbar."));
        content = (HorizontalLayout) childrenList.stream()
            .filter(e -> "content".equals(e.getClassName()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Unable to find the content."));
        return new Children(toolbar, content);
    }
}
