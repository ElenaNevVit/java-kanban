import java.util.ArrayList;
import java.util.List;

class Epic extends Task {
    private List<Integer> subtaskIds;

    public Epic(int id, String title, String description, TaskStatus status) {
        super(id, title, description, status);
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
    public String toString() {
        return "Epic{" +
                "subtaskIds=" + subtaskIds +
                "} " + super.toString();
    }
}