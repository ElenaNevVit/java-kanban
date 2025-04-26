import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class TaskManager {
    private int idCounter = 0;
    private HashMap<Integer, Task> tasks = new HashMap<>();

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public Task createTask(Task task) {
        if (task instanceof Subtask) {
            task = new Subtask(generateId(), task.getTitle(), task.getDescription(), task.getStatus(), ((Subtask) task).getEpicId());
            if (tasks.containsKey(((Subtask) task).getEpicId())) {
                tasks.put(task.getId(), task);
                Epic epic = (Epic) tasks.get(((Subtask) task).getEpicId());
                epic.addSubtaskId(task.getId());
                updateEpicStatus(((Subtask) task).getEpicId());
            } else {
                System.out.println("Epic with id " + ((Subtask) task).getEpicId() + " not found");
                return null;
            }

        } else if (task instanceof Epic) {
            task = new Epic(generateId(), task.getTitle(), task.getDescription(), task.getStatus());
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
            if (task instanceof Subtask) {
                updateEpicStatus(((Subtask) task).getEpicId());
            }

        }
    }

    public void deleteTaskById(int id) {
        Task task = tasks.remove(id);
        if (task instanceof Epic) {
            Epic epic = (Epic) task;
            List<Integer> subtaskIds = new ArrayList<>(epic.getSubtaskIds());
            for (Integer subtaskId : subtaskIds) {
                tasks.remove(subtaskId);
            }
        } else if (task instanceof Subtask) {
            Epic epic = (Epic) tasks.get(((Subtask) task).getEpicId());
            if (epic != null) {
                epic.removeSubtaskId(id);
                updateEpicStatus(((Subtask) task).getEpicId());
            }

        }
    }

    public List<Subtask> getAllSubtasksOfEpic(int epicId) {
        List<Subtask> subtasks = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task instanceof Subtask && ((Subtask) task).getEpicId() == epicId) {
                subtasks.add((Subtask) task);
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

        if (subtaskIds.isEmpty()) {
            Epic updatedEpic = new Epic(epic.getId(), epic.getTitle(), epic.getDescription(), TaskStatus.NEW);
            updateTask(updatedEpic);
            return;
        }

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
            Epic updatedEpic = new Epic(epic.getId(), epic.getTitle(), epic.getDescription(), TaskStatus.DONE);
            updateTask(updatedEpic);
        } else if (allNew) {
            Epic updatedEpic = new Epic(epic.getId(), epic.getTitle(), epic.getDescription(), TaskStatus.NEW);
            updateTask(updatedEpic);
        } else {
            Epic updatedEpic = new Epic(epic.getId(), epic.getTitle(), epic.getDescription(), TaskStatus.IN_PROGRESS);
            updateTask(updatedEpic);
        }
    }

    private int generateId() {
        return ++idCounter;
    }
}