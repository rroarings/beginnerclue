package scripts.tasks.restock;

import org.tribot.script.sdk.Log;
import scripts.Priority;
import scripts.Task;
import scripts.data.Vars;

public class Restocker implements Task {

    @Override
    public Priority priority() {
        return Priority.RESTOCK;
    }

    @Override
    public boolean validate() {
        return !Vars.get().getRestockJobs().isEmpty(); // items to restock in list
    }

    @Override
    public void execute() {
        Log.info("in restock");
    }
}
