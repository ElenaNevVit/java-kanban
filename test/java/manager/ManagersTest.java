package manager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ManagersTest {

    @Test
    void getDefaultShouldReturnInitializedTaskManager() {
        TaskManager taskManager = Managers.getDefault();
        assertNotNull(taskManager, "getDefault() должен возвращать проинициализированный TaskManager");
    }

    @Test
    void getDefaultHistoryShouldReturnInitializedHistoryManager() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager, "getDefaultHistory() должен возвращать проинициализированный HistoryManager");
    }
}