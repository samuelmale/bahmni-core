package org.bahmni.module.bahmnicore.web.v1_0.controller;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.bahmnicore.web.v1_0.controller.BahmniDrugOrderController;
import org.openmrs.module.bahmniemrapi.drugorder.contract.BahmniDrugOrder;
import org.openmrs.module.emrapi.encounter.domain.EncounterTransaction;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@org.springframework.test.context.ContextConfiguration(locations = {"classpath:TestingApplicationContext.xml"}, inheritLocations = true)
public class BahmniDrugOrderControllerIT extends BaseModuleWebContextSensitiveTest {

    @Autowired
    private BahmniDrugOrderController bahmniDrugOrderController;

    @Before
    public void setUp() throws Exception {
        executeDataSet("drugOrdersForVisits.xml");
    }


    @Test
    public void shouldReturnDrugOrdersForSpecifiedNumberOfVisits() {
        List<BahmniDrugOrder> prescribedDrugOrders = bahmniDrugOrderController.getPrescribedDrugOrders("86526ed5-3c11-11de-a0ba-001ed98eb67a", true, 3);
        assertEquals(4, prescribedDrugOrders.size());

        BahmniDrugOrder drugOrder1 = prescribedDrugOrders.get(0);
        assertEquals("d798916f-210d-4c4e-8978-467d1a969f31", drugOrder1.getVisit().getUuid());
        EncounterTransaction.DosingInstructions dosingInstructions1 = drugOrder1.getDosingInstructions();
        assertEquals("{\"dose\": \"1.5\", \"doseUnits\": \"Tablet\"}", dosingInstructions1.getAdministrationInstructions());
        assertEquals(15, drugOrder1.getDuration(), 0);
        assertEquals("Triomune-30", drugOrder1.getDrug().getName());
        assertEquals("2999-10-24 00:00:00.0", drugOrder1.getEffectiveStartDate().toString());
        assertEquals("2999-11-08 00:00:00.0", drugOrder1.getEffectiveStopDate().toString());

        BahmniDrugOrder drugOrder2 = prescribedDrugOrders.get(1);
        assertEquals("d798916f-210d-4c4e-8978-467d1a969f31", drugOrder2.getVisit().getUuid());
        EncounterTransaction.DosingInstructions dosingInstructions2 = drugOrder2.getDosingInstructions();
        assertEquals(4.5, dosingInstructions2.getDose(), 0);
        assertEquals("Before meals", drugOrder2.getInstructions());
        assertEquals("Take while sleeping", drugOrder2.getCommentToFulfiller());
        assertEquals("1/day x 7 days/week", dosingInstructions2.getFrequency());
        assertEquals("UNKNOWN", dosingInstructions2.getRoute());
        assertEquals(6, drugOrder2.getDuration(), 0);
        assertEquals("Paracetamol 250 mg", drugOrder2.getDrug().getName());
        assertEquals("2999-10-22 00:00:00.0", drugOrder2.getEffectiveStartDate().toString());
        assertEquals("2999-10-28 00:00:00.0", drugOrder2.getEffectiveStopDate().toString());

        BahmniDrugOrder drugOrder3 = prescribedDrugOrders.get(2);
        assertEquals("adf4fb41-a41a-4ad6-8835-2f59889acf5a", drugOrder3.getVisit().getUuid());
        EncounterTransaction.DosingInstructions dosingInstructions3 = drugOrder3.getDosingInstructions();
        assertEquals("{\"dose\": \"5.0\", \"doseUnits\": \"Tablet\"}", dosingInstructions3.getAdministrationInstructions());
        assertEquals("tab (s)", drugOrder3.getDrug().getForm());
        assertEquals(6, drugOrder3.getDuration(), 0);
        assertEquals("Triomune-30", drugOrder3.getDrug().getName());
        assertEquals("2005-09-23 08:00:00.0", drugOrder3.getEffectiveStartDate().toString());
        assertEquals("2005-09-30 00:00:00.0", drugOrder3.getEffectiveStopDate().toString());

        BahmniDrugOrder drugOrder4 = prescribedDrugOrders.get(3);
        assertEquals("adf4fb41-a41a-4ad6-8835-2f59889acf5a", drugOrder4.getVisit().getUuid());
        EncounterTransaction.DosingInstructions dosingInstructions4 = drugOrder4.getDosingInstructions();
        assertEquals("{\"dose\": \"2.5\", \"doseUnits\": \"Tablet\"}", dosingInstructions4.getAdministrationInstructions());
        assertEquals(6, drugOrder4.getDuration(), 0);
        assertEquals("Triomune-40", drugOrder4.getDrug().getName());
        assertEquals("2005-09-23 00:00:00.0", drugOrder4.getEffectiveStartDate().toString());
        assertEquals("2005-09-29 00:00:00.0", drugOrder4.getEffectiveStopDate().toString());
    }

    @Test
    public void shouldReturnAllScheduledDrugOrders() throws Exception {
        List<BahmniDrugOrder> scheduledDrugOrders = bahmniDrugOrderController.getScheduledDrugOrders("86526ed5-3c11-11de-a0ba-001ed98eb67a");

        assertNotNull("ScheduledDrugOrders is null", scheduledDrugOrders);
        assertEquals(2, scheduledDrugOrders.size());

        BahmniDrugOrder triomuneDrugOrder = scheduledDrugOrders.get(0);
        assertEquals("d798916f-210d-4c4e-8978-467d1a969f31", triomuneDrugOrder.getVisit().getUuid());
        EncounterTransaction.DosingInstructions dosingInstructions3 = triomuneDrugOrder.getDosingInstructions();
        assertEquals("{\"dose\": \"1.5\", \"doseUnits\": \"Tablet\"}", dosingInstructions3.getAdministrationInstructions());
        assertEquals("tab (s)", triomuneDrugOrder.getDrug().getForm());
        assertEquals(15, triomuneDrugOrder.getDuration(), 0);
        assertEquals("Triomune-30", triomuneDrugOrder.getDrug().getName());
        assertEquals("2999-10-24 00:00:00.0", triomuneDrugOrder.getEffectiveStartDate().toString());
        assertEquals("2999-11-08 00:00:00.0", triomuneDrugOrder.getEffectiveStopDate().toString());

        BahmniDrugOrder paracetamolDrugOrder = scheduledDrugOrders.get(1);
        assertEquals("d798916f-210d-4c4e-8978-467d1a969f31", paracetamolDrugOrder.getVisit().getUuid());
        EncounterTransaction.DosingInstructions dosingInstructions2 = paracetamolDrugOrder.getDosingInstructions();
        assertEquals("Paracetamol 250 mg", paracetamolDrugOrder.getDrug().getName());
        assertEquals(4.5, dosingInstructions2.getDose(), 0);
        assertEquals("Before meals", paracetamolDrugOrder.getInstructions());
        assertEquals("Take while sleeping", paracetamolDrugOrder.getCommentToFulfiller());
        assertEquals("1/day x 7 days/week", dosingInstructions2.getFrequency());
        assertEquals("UNKNOWN", dosingInstructions2.getRoute());
        assertEquals(6, paracetamolDrugOrder.getDuration(), 0);
        assertEquals("2999-10-22 00:00:00.0", paracetamolDrugOrder.getEffectiveStartDate().toString());
        assertEquals("2999-10-28 00:00:00.0", paracetamolDrugOrder.getEffectiveStopDate().toString());
    }
}
