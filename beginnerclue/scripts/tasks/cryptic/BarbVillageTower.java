package scripts.tasks.cryptic;

import org.tribot.script.sdk.ChatScreen;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.WorldTile;
import scripts.Task;
import scripts.data.Utilities;
import scripts.data.Vars;
import scripts.data.clues.CrypticClue;
import scripts.tasks.AbstractClueTask;

public class BarbVillageTower extends AbstractClueTask implements Task {

    public static final WorldTile FLOOR0_TILE = new WorldTile(3096, 3432, 0);
    public static final WorldTile FLOOR1_TILE = new WorldTile(3096, 3432, 1);
    public static final WorldTile FLOOR2_TILE = new WorldTile(3096, 3428, 2);
    public static final WorldTile FLOOR2_LADDER_TILE = new WorldTile(3097, 3432, 2);
    public static final Area FLOOR0_AREA = Area.fromRadius(FLOOR0_TILE, 2);
    public static final Area FLOOR1_AREA = Area.fromRadius(FLOOR1_TILE, 2);
    public static final Area FLOOR2_AREA = Area.fromRadius(FLOOR2_TILE, 2);
    public static final Area FLOOR2_LADDER_AREA = Area.fromRadius(FLOOR2_LADDER_TILE, 2);
    public static final int LADDER_ID = 17026;
    public static final String NPC = "Hunding";
    public static final String STEP = "In a village of barbarians, I am the one who guards the village from up high.";

    public static final CrypticClue CLUE = CrypticClue.CLUES.get(0);

    public BarbVillageTower() {
        super(CLUE);
    }

    @Override
    public void execute() {
        if (FLOOR2_LADDER_AREA.containsMyPlayer()) {
            // open door if needed
            if (!doorIsOpen() && Utilities.waitObject(1535) && Waiting.waitUntil(() -> !MyPlayer.isMoving()))
                Waiting.waitNormal(600, 90);

            // talk to guy
            if (Utilities.waitNpc(NPC)
                    && Waiting.waitUntil(ChatScreen::isClickContinueOpen)
                    && ChatScreen.clickContinue()) {
                Waiting.waitNormal(600, 90);
                Vars.get().setClue(null);
            }

            return;
        }

        if (FLOOR1_AREA.containsMyPlayer()) {
            if (Utilities.waitObject(LADDER_ID) && Waiting.waitUntil(FLOOR2_LADDER_AREA::containsMyPlayer))
                Waiting.waitNormal(600, 90);

            return;
        }

        if (FLOOR0_AREA.containsMyPlayer()) {
            if (Utilities.waitObject(LADDER_ID) && Waiting.waitUntil(FLOOR1_AREA::containsMyPlayer))
                Waiting.waitNormal(600, 90);

            return;
        }

        if (Utilities.walkWait(FLOOR0_AREA))
            Waiting.waitNormal(600, 90);
    }

    private boolean doorIsOpen() {
        return Query.gameObjects()
                .idEquals(1536)
                .isAny();
    }

}