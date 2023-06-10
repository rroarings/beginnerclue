package scripts.tasks;

import org.tribot.script.sdk.Inventory;
import scripts.Priority;
import scripts.data.Constants;
import scripts.data.Vars;
import scripts.data.clues.ClueScroll;

public abstract class AbstractClueTask {

    protected ClueScroll clue;

    public AbstractClueTask(ClueScroll clue) {
        this.clue = clue;
    }

    public Priority priority() {
        return Priority.MEDIUM;
    }

    public boolean validate() {
        return Inventory.contains(Constants.CLUE_ID) &&
                (Vars.get().getClue().getStep() != null
                        ? Vars.get().getClue().getStep().equals(clue.getStep())
                        : Vars.get().getClue().getWidgetPath() == clue.getWidgetPath());
    }

    public ClueScroll getClue() {
        return clue;
    }

    protected void setClue(ClueScroll clue) {
        this.clue = clue;
    }

    public abstract void execute();

    @Override
    public String toString() {
        return clue.getDescription();
    }
}