package task;

public class Subtask extends Task {
    private int epicId;

    public Subtask(int id, String title, String description, TaskStatus status, int epicId) {
        super(id, title, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public TaskType getType() {
        return TaskType.SUBTASK;
    }

    @Override
    public void delete(manager.TaskManager taskManager) {
        Epic epic = (Epic) taskManager.getTaskById(this.epicId);
        if (epic != null) {
            epic.removeSubtaskId(this.getId());
        }
        taskManager.removeTask(this.id);
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epicId=" + epicId +
                "} " + super.toString();
    }
}