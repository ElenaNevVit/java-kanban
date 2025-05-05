package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Subtask;
import task.Task;
import task.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerTest {

    private TaskManager taskManager;
    private Task task1;
    private Epic epic1;
    private Subtask subtask1;


    @BeforeEach
    void setUp() {
        taskManager = Managers.getDefault();
        task1 = new Task(1, "Test Task", "Test Description", TaskStatus.NEW);
        epic1 = new Epic(1, "Test Epic", "Test Description");
        subtask1 =  new Subtask(2, "Test Subtask", "Test Description", TaskStatus.NEW, epic1.getId());
    }

    @Test
    void canAddTaskAndFindById() {
        taskManager.createTask(task1);
        Task retrievedTask = taskManager.getTaskById(task1.getId());
        assertEquals(task1, retrievedTask, "Задача должна быть добавлена и найдена корректно");
    }

    @Test
    void canAddEpicAndFindById() {
        taskManager.createEpic(epic1);
        Epic retrievedEpic = taskManager.getEpicById(epic1.getId());
        assertEquals(epic1, retrievedEpic, "Epic должен быть добавлен и найден корректно");
    }

    @Test
    void canAddSubtaskAndFindById() {
        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask1);
        Subtask retrievedSubtask = taskManager.getSubtaskById(subtask1.getId());
        assertEquals(subtask1, retrievedSubtask, "Подзадача должна быть добавлена и найдена корректно");
    }

    @Test
    void shouldReturnTheSameTask() {
        taskManager.createTask(task1);
        Task retrievedTask = taskManager.getTaskById(task1.getId());

        assertEquals(task1.getDescription(), retrievedTask.getDescription(), "Описание должно совпадать");
        assertEquals(task1.getId(), retrievedTask.getId(), "Id должен совпадать");
        assertEquals(task1.getStatus(), retrievedTask.getStatus(), "Статус должен совпадать");
        assertEquals(task1.getTitle(), retrievedTask.getTitle(), "Заголовок должен совпадать");
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
        taskManager.createTask(task1);
        Task retrievedTask = taskManager.getTaskById(task1.getId());

        assertEquals(task1.getId(), retrievedTask.getId(), "Id должен быть тем же");
        assertEquals(task1.getTitle(), retrievedTask.getTitle(), "Заголовок должен быть тем же");
        assertEquals(task1.getDescription(), retrievedTask.getDescription(), "Описание должно быть тем же");
        assertEquals(task1.getStatus(), retrievedTask.getStatus(), "Статус должен быть тем же");
    }
}