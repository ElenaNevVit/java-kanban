package task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EpicTest {

    @Test
    void epicsWithSameIdShouldBeEqual() {
        Epic epic1 = new Epic(1, "Test Epic", "Test Description");
        Epic epic2 = new Epic(1, "Another Epic", "Another Description");

        assertEquals(epic1, epic2, "Эпики с одинаковым ID должны быть равны");
    }

    @Test
    void epicCannotAddItselfAsSubtask() {
        Epic epic = new Epic(1, "Test Epic", "Test Description");
        epic.addSubtaskId(epic.getId());

        assertFalse(epic.getSubtaskIds().contains(epic.getId()), "Epic не должен содержать себя в подзадачах");
    }
}