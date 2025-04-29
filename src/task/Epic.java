package task;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> subtaskIds;

    public Epic(int id, String title, String description) {
        super(id, title, description, TaskStatus.NEW);
        this.subtaskIds = new ArrayList<>();
    }

    public List<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public void addSubtaskId(int subtaskId) {
        this.subtaskIds.add(subtaskId);
    }

    public void removeSubtaskId(int subtaskId) {
        this.subtaskIds.remove(Integer.valueOf(subtaskId)); // Remove by value
    }

    @Override
    public TaskType getType() {
        return TaskType.EPIC;
    }

    @Override
    public void delete(manager.TaskManager taskManager) {
        // Удаляем все подзадачи эпика
        for (Integer subtaskId : subtaskIds) {
            Task subtask = taskManager.getTaskById(subtaskId);
            if (subtask != null) {
                subtask.delete(taskManager);
            }
        }
        taskManager.removeTask(this.id);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtaskIds=" + subtaskIds +
                "} " + super.toString();
    }
}