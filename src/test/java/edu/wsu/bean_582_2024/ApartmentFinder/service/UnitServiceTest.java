package edu.wsu.bean_582_2024.ApartmentFinder.service;

import edu.wsu.bean_582_2024.ApartmentFinder.TestCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.wsu.bean_582_2024.ApartmentFinder.data.UnitRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UnitServiceTest {
    // S8
    @Mock
    private UnitRepository unitRepositoryMock;
    @InjectMocks
    private UnitService unitService;
    private static final String ADDRESS_1 = "100 Pullman Rd";
    private static final int BEDROOMS_1 = 2;
    private static final int BEDROOMS_2 = 3;
    private static final double BATHROOMS_1 = 1.5D;
    private static final double BATHROOMS_2 = 2.0D;
    private static final String LIVING_ROOM_1 = "Elegant Living spaces";
    private static final String LIVING_ROOM_2 = "Cozy living room";
    private static final String KITCHEN_1 = "Modern kitchen";
    private static final String KITCHEN_2 = "Traditional kitchen";
    private static final Unit UNIT_1 = new Unit(ADDRESS_1, BEDROOMS_1, BATHROOMS_1, LIVING_ROOM_1, KITCHEN_1, true, null);
    private static final Unit UNIT_2 = new Unit(ADDRESS_1, BEDROOMS_2, BATHROOMS_2, LIVING_ROOM_2, KITCHEN_2, false, null);
    private static final List<Unit> UNITS = List.of(UNIT_1, UNIT_2);


    @TestCase("C81")
    @Test
    public void testGetAllUnits() {
        when(unitRepositoryMock.getAll()).thenReturn(UNITS);

        List<Unit> result = unitService.getAllUnits(true);

        assertEquals(2, result.size());
    }

    @TestCase("C82")
    @Test
    public void testFindUnits() {
        String filter = "test filter";
        when(unitRepositoryMock.search(filter)).thenReturn(UNITS);

        List<Unit> result = unitService.findUnits(filter);

        assertEquals(2, result.size());
    }

    @Test
    @TestCase("C83")
    public void testGetUnitCount() {
        when(unitRepositoryMock.count()).thenReturn(10L);

        long result = unitService.getUnitCount();

        assertEquals(10L, result);
    }

    @TestCase("C84")
    @Test
    public void testDeleteUnit() {
        unitService.deleteUnit(UNIT_1);

        verify(unitRepositoryMock).delete(UNIT_1);
    }

    @TestCase("C85")
    @Test
    public void testSaveUnit() {
        unitService.saveUnit(UNIT_1);

        verify(unitRepositoryMock).add(UNIT_1);
    }
}
