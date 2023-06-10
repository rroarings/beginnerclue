package scripts.tasks.map;

import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import scripts.Task;
import scripts.data.Utilities;
import scripts.data.Vars;
import scripts.data.clues.MapClue;
import scripts.tasks.AbstractClueTask;

public class MapClueTask extends AbstractClueTask implements Task {

    public MapClueTask(MapClue clue) {
        super(clue);
    }

    @Override
    public void execute() {
        if (!Inventory.contains(952)) {
            Log.error("No spade found!");
            Vars.get().setRunning(false);
            return;
        }

        if (MyPlayer.getTile().distanceTo(clue.getTile()) == 0) {
            if (Utilities.clickInventory("Spade")
                    && Waiting.waitUntil(() -> !MyPlayer.isAnimating())) {
                Waiting.waitNormal(2000, 90);
                Vars.get().setClue(null);
            }
            return;
        }

        if (Utilities.walkWait(clue.getArea())
                && Waiting.waitUntil(() -> !MyPlayer.isMoving())
                && clue.getTile().interact("Walk here")
                && Waiting.waitUntil(() -> !MyPlayer.isMoving()))
            Waiting.waitNormal(600, 90);
    }

}
