package task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    void tasksWithSameIdShouldBeEqual() {
        Task task1 = new Task(1, "Test Task", "Test Description", TaskStatus.NEW);
        Task task2 = new Task(1, "Another Task", "Another Description", TaskStatus.IN_PROGRESS);

        assertEquals(task1, task2, "Задачи с одинаковым ID должны быть равны");
    }
}