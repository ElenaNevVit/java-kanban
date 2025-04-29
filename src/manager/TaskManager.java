import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class TaskManager {
    private int idCounter = 0;
    HashMap<Integer, Task> tasks = new HashMap<>();

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public Task createTask(Task task) {
        if (task.getType() == TaskType.SUBTASK) {
            Subtask subtask = (Subtask) task; // Приводим тип только один раз
            task = new Subtask(generateId(), subtask.getTitle(), subtask.getDescription(), subtask.getStatus(), subtask.getEpicId());
            if (tasks.containsKey(subtask.getEpicId())) {
                tasks.put(task.getId(), task);
                Epic epic = (Epic) tasks.get(subtask.getEpicId());
                epic.addSubtaskId(task.getId());
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
        List<Task> tasksList = new ArrayList<>(tasks.values());
        return tasksList;
    }

    public void deleteAllTasks() {
        for (Task task : tasks.values()) {
            if (task.getType() == TaskType.TASK) {
                task.delete(this);
            }
        }
    }

    public void deleteAllSubtasks() {
        for (Task task : tasks.values()) {
            if (task.getType() == TaskType.SUBTASK) {
                task.delete(this);
            }
        }
    }

    public void deleteAllEpics() {
        for (Task task : tasks.values()) {
            if (task.getType() == TaskType.EPIC) {
                task.delete(this);
            }
        }
    }

    public List<Subtask> getAllSubtasksOfEpic(int epicId) {
        List<Subtask> subtasks = new ArrayList<>();
        Task task = tasks.get(epicId);
        if (task instanceof Epic) {
            Epic epic = (Epic) task;
            for (Integer subtaskId : epic.getSubtaskIds()) {
                Task subtask = tasks.get(subtaskId);
                if (subtask instanceof Subtask) {
                    subtasks.add((Subtask) subtask);
                }
            }
        }
        return subtasks;
    }

    private void updateEpicStatus(int epicId) {
        Task task = tasks.get(epicId);
        if (!(task instanceof Epic)) {
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

    private int generateId() {
        return ++idCounter;
    }
}