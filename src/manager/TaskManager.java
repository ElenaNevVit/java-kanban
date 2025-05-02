package manager;

import task.Epic;
import task.Subtask;
import task.Task;
import task.TaskStatus;
import task.TaskType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {
    private int idCounter = 0;
    private HashMap<Integer, Task> tasks = new HashMap<>();

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public Task createTask(Task task) {
        if (task.getType() == TaskType.SUBTASK) {
            Subtask subtask = (Subtask) task;
            Task newTask = new Subtask(generateId(), subtask.getTitle(), subtask.getDescription(), subtask.getStatus(),
                    subtask.getEpicId());
            if (tasks.containsKey(subtask.getEpicId())) {
                tasks.put(newTask.getId(), newTask);
                Epic epic = (Epic) tasks.get(subtask.getEpicId());
                epic.addSubtaskId(newTask.getId());
                updateEpicStatus(subtask.getEpicId());
            } else {
                System.out.println("Epic with id " + subtask.getEpicId() + " not found");
                return null;
            }

        } else if (task.getType() == TaskType.EPIC) {
            task = new Epic(generateId(), task.getTitle(), task.getDescription());
            tasks.put(task.getId(), task);
        } else {
            task = new Task(generateId(), task.getTitle(), task.getDescription(), task.getStatus());
            tasks.put(task.getId(), task);
        }

        return task;
    }

    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
            if (task.getType() == TaskType.SUBTASK) {
                updateEpicStatus(((Subtask) task).getEpicId());
            }

        }
    }

    public List<Task> getTasks() {
        List<Task> taskList = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.getType() == TaskType.TASK) {
                taskList.add(task);
            }
        }
        return taskList;
    }

    public List<Epic> getEpics() {
        List<Epic> epicList = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.getType() == TaskType.EPIC) {
                epicList.add((Epic) task);
            }
        }
        return epicList;
    }

    public List<Subtask> getSubtasks() {
        List<Subtask> subtaskList = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.getType() == TaskType.SUBTASK) {
                subtaskList.add((Subtask) task);
            }
        }
        return subtaskList;
    }

    public void deleteAllTasks() {
        List<Integer> taskIdsToRemove = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.getType() == TaskType.TASK) {
                taskIdsToRemove.add(task.getId());
            }
        }
        for (Integer taskId : taskIdsToRemove) {
            deleteTaskById(taskId);
        }
    }

    public void deleteAllSubtasks() {
        List<Integer> subtaskIdsToRemove = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.getType() == TaskType.SUBTASK) {
                subtaskIdsToRemove.add(task.getId());
            }
        }
        for (Integer subtaskId : subtaskIdsToRemove) {
            deleteSubtaskById(subtaskId);
        }
    }

    public void deleteAllEpics() {
        List<Integer> epicIdsToRemove = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.getType() == TaskType.EPIC) {
                epicIdsToRemove.add(task.getId());
            }
        }
        for (Integer epicId : epicIdsToRemove) {
            deleteEpicById(epicId);
        }
    }

    public List<Subtask> getAllSubtasksOfEpic(int epicId) {
        List<Subtask> subtasks = new ArrayList<>();
        Epic epic = (Epic) tasks.get(epicId);
        if (epic != null) {
            for (Integer subtaskId : epic.getSubtaskIds()) {
                Task subtask = tasks.get(subtaskId);
                if (subtask != null && subtask.getType() == TaskType.SUBTASK) {
                    subtasks.add((Subtask) subtask);
                }
            }
        }
        return subtasks;
    }

    private void updateEpicStatus(int epicId) {
        Task task = tasks.get(epicId);
        if (task == null || task.getType() != TaskType.EPIC) {
            return;
        }

        Epic epic = (Epic) task;
        List<Integer> subtaskIds = epic.getSubtaskIds();
        boolean allNew = true;
        boolean allDone = true;
        for (Integer subtaskId : subtaskIds) {
            Task subtask = tasks.get(subtaskId);
            if (subtask == null) continue;
            if (subtask.getStatus() != TaskStatus.NEW) {
                allNew = false;
            }
            if (subtask.getStatus() != TaskStatus.DONE) {
                allDone = false;
            }
        }
        if (allDone) {
            epic.setStatus(TaskStatus.DONE);
        } else if (allNew) {
            epic.setStatus(TaskStatus.NEW);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    public void deleteSubtaskById(int id) {
        Task task = tasks.remove(id);
        if (task != null && task.getType() == TaskType.SUBTASK) {
            Subtask subtask = (Subtask) task;
            Epic epic = (Epic) getTaskById(subtask.getEpicId());
            if (epic != null) {
                epic.removeSubtaskId(id);
                updateEpicStatus(subtask.getEpicId());
            }
        }
    }

    public void deleteEpicById(int id) {
        Task task = tasks.remove(id);
        if (task != null && task.getType() == TaskType.EPIC) {
            Epic epic = (Epic) task;
            List<Integer> subtaskIds = new ArrayList<>(epic.getSubtaskIds());
            for (Integer subtaskId : subtaskIds) {
                deleteSubtaskById(subtaskId);
            }
        }
    }

    private int generateId() {
        return ++idCounter;
    }
}