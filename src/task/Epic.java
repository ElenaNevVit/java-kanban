package task;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> subtaskIds;

    public Epic(int id, String title, String description) {
        super(id, title, description, TaskStatus.NEW);
        this.subtaskIds = new ArrayList<>();
    }

    public List<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public void addSubtaskId(int subtaskId) {
        if (this.getId() != subtaskId) {
            this.subtaskIds.add(subtaskId);
        }
    }

    public void removeSubtaskId(int subtaskId) {
        this.subtaskIds.remove(Integer.valueOf(subtaskId));
    }
}