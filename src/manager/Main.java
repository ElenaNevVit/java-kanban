package manager;

import task.Epic;
import task.Subtask;
import task.Task;
import task.TaskStatus;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Epic epic1 = new Epic(0, "Переезд", "Подготовка к переезду в новый офис");
        epic1 = (Epic) taskManager.createTask(epic1);
        System.out.println("Создан эпик: " + epic1);


        Subtask subtask1 = new Subtask(0, "Упаковка вещей", "Собрать все вещи в коробки", TaskStatus.NEW, epic1.getId());
        subtask1 = (Subtask) taskManager.createTask(subtask1);
        System.out.println("Создана подзадача: " + subtask1);


        Subtask subtask2 = new Subtask(0, "Поиск грузчиков", "Найти и заказать услуги грузчиков",
                TaskStatus.NEW, epic1.getId());
        subtask2 = (Subtask) taskManager.createTask(subtask2);
        System.out.println("Создана подзадача: " + subtask2);


        System.out.println("Подзадачи эпика " + epic1.getId() + ": " + taskManager.getAllSubtasksOfEpic(epic1.getId()));


        Subtask updatedSubtask1 = new Subtask(subtask1.getId(), subtask1.getTitle(), subtask1.getDescription(),
                TaskStatus.DONE, subtask1.getEpicId());
        taskManager.updateTask(updatedSubtask1);
        System.out.println("Статус подзадачи " + updatedSubtask1.getId() + " изменен на DONE");


        System.out.println("Статус эпика " + epic1.getId() + " после изменения статуса подзадачи: " +
                taskManager.getTaskById(epic1.getId()).getStatus());


        Subtask updatedSubtask2 = new Subtask(subtask2.getId(), subtask2.getTitle(), subtask2.getDescription(),
                TaskStatus.DONE, subtask2.getEpicId());
        taskManager.updateTask(updatedSubtask2);
        System.out.println("Статус подзадачи " + updatedSubtask2.getId() + " изменен на DONE");


        System.out.println("Статус эпика " + epic1.getId() + " после завершения всех подзадач: " +
                taskManager.getTaskById(epic1.getId()).getStatus());


        System.out.println("Список всех задач: " + taskManager.getTasks());
        System.out.println("Список всех эпиков: " + taskManager.getEpics());
        System.out.println("Список всех подзадач: " + taskManager.getSubtasks());


        subtask1.delete(taskManager);
        System.out.println("Список всех задач после удаления подзадачи " + subtask1.getId() + ": " + taskManager.getTasks());
    }
}