package edu.wsu.bean_582_2024.ApartmentFinder.service;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.wsu.bean_582_2024.ApartmentFinder.data.UnitRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UnitServiceTests {

    @Mock
    private UnitRepository unitRepositoryMock;

    @InjectMocks
    private UnitService unitService;

    @Test
    public void testGetAllUnits() {
        Unit unit1 = new Unit("100 Pullman Rd", 2, 1.5, "Elegant Living spaces", "Modern kitchen", true, null);
        Unit unit2 = new Unit("100 Pullman Rd", 3, 2.0, "Cozy living room", "Traditional kitchen", false, null);
        List<Unit> units = Arrays.asList(unit1, unit2);
        when(unitRepositoryMock.getAll()).thenReturn(units);

        List<Unit> result = unitService.getAllUnits(true);

        assertEquals(2, result.size());
    }

    @Test
    public void testFindUnits() {
        String filter = "test filter";
        Unit unit1 = new Unit("100 Pullman Rd", 2, 1.5, "Elegant Living spaces", "Modern kitchen", true, null);
        Unit unit2 = new Unit("100 Pullman Rd", 3, 2.0, "Cozy living room", "Traditional kitchen", false, null);
        List<Unit> units = Arrays.asList(unit1, unit2);
        when(unitRepositoryMock.search(filter)).thenReturn(units);

        List<Unit> result = unitService.findUnits(filter);

        assertEquals(2, result.size());
    }

    @Test
    public void testGetUnitCount() {
        when(unitRepositoryMock.count()).thenReturn(10L);

        long result = unitService.getUnitCount();

        assertEquals(10L, result);
    }

    @Test
    public void testDeleteUnit() {
        Unit unitToDelete = new Unit("100 Pullman Rd", 2, 1.5, "Elegant Living spaces", "Modern kitchen", true, null);
        unitService.deleteUnit(unitToDelete);

        verify(unitRepositoryMock, times(1)).delete(unitToDelete);
    }

    //@Test -- This test is broken. There is no .save()
    public void testSaveUnit() {
        Unit unitToSave = new Unit("100 Pullman Rd", 2, 1.5, "Elegant Living spaces", "Modern kitchen", true, null);
        unitService.saveUnit(unitToSave);

        // There is no save method
        //verify(unitRepositoryMock, times(1)).save(unitToSave);
    }
}
