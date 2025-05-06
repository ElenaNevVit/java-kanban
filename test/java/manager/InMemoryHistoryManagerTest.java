package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Task;
import task.TaskStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryHistoryManagerTest {

    private HistoryManager historyManager;
    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        historyManager = new InMemoryHistoryManager();
        task1 = new Task(1, "Test Task 1", "Test Description 1", TaskStatus.NEW);
        task2 = new Task(2, "Test Task 2", "Test Description 2", TaskStatus.IN_PROGRESS);
    }

    @Test
    void addShouldSaveTaskToHistory() {
        historyManager.add(task1);
        List<Task> history = historyManager.getHistory();
        assertFalse(history.isEmpty(), "История не должна быть пустой после добавления задачи");
        assertEquals(1, history.size(), "История должна содержать одну задачу");
        assertEquals(task1, history.get(0), "История должна содержать добавленную задачу");
    }

    @Test
    void getHistoryShouldReturnCorrectHistory() {
        historyManager.add(task1);
        historyManager.add(task2);
        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size(), "История должна содержать две задачи");
        assertEquals(task1, history.get(0), "Первая задача в истории должна быть task1");
        assertEquals(task2, history.get(1), "Вторая задача в истории должна быть task2");
    }

    @Test
    void historyShouldNotExceedMaxSize() {
        for (int i = 0; i < 15; i++) {
            Task task = new Task(i, "Task " + i, "Description " + i, TaskStatus.NEW);
            historyManager.add(task);
        }
        List<Task> history = historyManager.getHistory();
        assertEquals(10, history.size(), "История не должна превышать максимальный размер");
    }

    @Test
    void historyShouldStorePreviousVersionOfTask() {
        historyManager.add(task1);
        Task task2 = new Task(task1.getId(), task1.getTitle(), task1.getDescription(), TaskStatus.DONE);
        historyManager.add(task2);
        List<Task> history = historyManager.getHistory();

        assertEquals(TaskStatus.NEW, history.get(0).getStatus(), "Первая задача должна иметь статус NEW");
        assertEquals(TaskStatus.DONE, history.get(1).getStatus(), "Вторая задача должна иметь статус DONE");
    }
}