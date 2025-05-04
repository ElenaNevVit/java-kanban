package task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SubtaskTest {

    @Test
    void subtasksWithSameIdShouldBeEqual() {
        Subtask subtask1 = new Subtask(1, "Test Subtask", "Test Description", TaskStatus.NEW, 2);
        Subtask subtask2 = new Subtask(1, "Another Subtask", "Another Description", TaskStatus.IN_PROGRESS, 3);

        assertEquals(subtask1, subtask2, "Подзадачи с одинаковым ID должны быть равны");
    }

    @Test
    void subtaskCannotBeItsOwnEpic() {
        Subtask subtask = new Subtask(1, "Test Subtask", "Test Description", TaskStatus.NEW, 2);

        assertNotEquals(subtask.getEpicId(), subtask.getId(), "Подзадача не может быть своим же эпиком");
    }
}