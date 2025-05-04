package manager;

import task.Epic;
import task.Subtask;
import task.Task;
import task.TaskStatus;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        Epic epic1 = new Epic(0, "Переезд", "Подготовка к переезду в новый офис");
        epic1 =  taskManager.createEpic(epic1);
        System.out.println("Создан эпик: " + epic1);

        Subtask subtask1 = new Subtask(0, "Упаковка вещей", "Собрать все вещи в коробки", TaskStatus.NEW, epic1.getId());
        subtask1 =  taskManager.createSubtask(subtask1);
        System.out.println("Создана подзадача: " + subtask1);

        Subtask subtask2 = new Subtask(0, "Поиск грузчиков", "Найти и заказать услуги грузчиков",
                TaskStatus.NEW, epic1.getId());
        subtask2 = taskManager.createSubtask(subtask2);
        System.out.println("Создана подзадача: " + subtask2);

        System.out.println("Подзадачи эпика " + epic1.getId() + ": " + taskManager.getAllSubtasksOfEpic(epic1.getId()));

        Subtask updatedSubtask1 = new Subtask(subtask1.getId(), subtask1.getTitle(), subtask1.getDescription(),
                TaskStatus.DONE, subtask1.getEpicId());
        taskManager.updateSubtask(updatedSubtask1);
        System.out.println("Статус подзадачи " + updatedSubtask1.getId() + " изменен на DONE");

        System.out.println("Статус эпика " + epic1.getId() + " после изменения статуса подзадачи: " +
                taskManager.getEpicById(epic1.getId()).getStatus());

        Subtask updatedSubtask2 = new Subtask(subtask2.getId(), subtask2.getTitle(), subtask2.getDescription(),
                TaskStatus.DONE, subtask2.getEpicId());
        taskManager.updateSubtask(updatedSubtask2);
        System.out.println("Статус подзадачи " + updatedSubtask2.getId() + " изменен на DONE");

        System.out.println("Статус эпика " + epic1.getId() + " после завершения всех подзадач: " +
                taskManager.getEpicById(epic1.getId()).getStatus());

        System.out.println("Список всех задач: " + taskManager.getAllTasks());
        System.out.println("Список всех эпиков: " + taskManager.getAllEpics());
        System.out.println("Список всех подзадач: " + taskManager.getAllSubtasks());

        taskManager.deleteSubtaskById(subtask1.getId());
        System.out.println("Список всех задач после удаления подзадачи " + subtask1.getId() + ": " + taskManager.getAllTasks());
    }
}