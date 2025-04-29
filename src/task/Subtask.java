package task;

import task.TaskStatus;
import task.TaskType;

class Subtask extends Task {
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
    public void delete(TaskManager taskManager) {
        Epic epic = (Epic) taskManager.tasks.get(this.epicId);
        if (epic != null) {
            epic.removeSubtaskId(this.getId());
        }
        taskManager.tasks.remove(this.id);
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epicId=" + epicId +
                "} " + super.toString();
    }
}