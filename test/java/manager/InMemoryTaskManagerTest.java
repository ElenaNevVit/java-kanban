package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Subtask;
import task.Task;
import task.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerTest {

    private InMemoryTaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new InMemoryTaskManager();
    }

    @Test
    void canAddTaskAndFindById() {
        Task task = new Task(1, "Test Task", "Test Description", TaskStatus.NEW);
        taskManager.createTask(task);
        Task retrievedTask = taskManager.getTaskById(1);
        assertEquals(task, retrievedTask, "Задача должна быть добавлена и найдена корректно");
    }

    @Test
    void canAddEpicAndFindById() {
        Epic epic = new Epic(1, "Test Epic", "Test Description");
        taskManager.createEpic(epic);
        Epic retrievedEpic = taskManager.getEpicById(1);
        assertEquals(epic, retrievedEpic, "Epic должен быть добавлен и найден корректно");
    }

    @Test
    void canAddSubtaskAndFindById() {
        Epic epic = new Epic(1, "Test Epic", "Test Description");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask(2, "Test Subtask", "Test Description", TaskStatus.NEW, epic.getId());
        taskManager.createSubtask(subtask);
        Subtask retrievedSubtask = taskManager.getSubtaskById(2);
        assertEquals(subtask, retrievedSubtask, "Подзадача должна быть добавлена и найдена корректно");
    }

    @Test
    void utilityClassAlwaysReturnsInitializedManagers() {
        TaskManager taskManager = Managers.getDefault();
        assertNotNull(taskManager, "Утилитарный класс должен всегда возвращать проинициализированные и готовые к работе экземпляры TaskManager");
    }

    @Test
    void shouldReturnTheSameTask() {
        Task task = new Task(1, "Test Task", "Test Description", TaskStatus.NEW);
        taskManager.createTask(task);
        Task task1 = taskManager.getTaskById(task.getId());

        assertEquals(task.getDescription(), task1.getDescription(), "Описание должно совпадать");
        assertEquals(task.getId(), task1.getId(), "Id должен совпадать");
        assertEquals(task.getStatus(), task1.getStatus(), "Статус должен совпадать");
        assertEquals(task.getTitle(), task1.getTitle(), "Заголовок должен совпадать");
    }

    @Test
    void tasksWithGeneratedIdAndPresetIdMustNotCollide() {
        Task task1 = new Task(1, "Задача 1", "Описание 1", TaskStatus.NEW);
        taskManager.createTask(task1);

        Task task2 = new Task("Задача 2", "Описание 2", TaskStatus.NEW);
        taskManager.createTask(task2);

        assertNotEquals(task1.getId(), task2.getId(), "IDs не должны быть равны");

        assertNotNull(taskManager.getTaskById(task1.getId()), "Task1 должен быть в менеджере");
        assertNotNull(taskManager.getTaskById(task2.getId()), "Task2 должен быть в менеджере");
    }

    @Test
    void taskShouldRemainUnchangedAfterAddingToManager() {
        Task task = new Task(1, "Original Title", "Original Description", TaskStatus.NEW);

        taskManager.createTask(task);
        Task retrievedTask = taskManager.getTaskById(task.getId());

        assertEquals(task.getId(), retrievedTask.getId(), "Id должен быть тем же");
        assertEquals(task.getTitle(), retrievedTask.getTitle(), "Заголовок должен быть тем же");
        assertEquals(task.getDescription(), retrievedTask.getDescription(), "Описание должно быть тем же");
        assertEquals(task.getStatus(), retrievedTask.getStatus(), "Статус должен быть тем же");
    }

}