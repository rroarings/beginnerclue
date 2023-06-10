package scripts.tasks.cluemanagement;

import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.Npc;
import org.tribot.script.sdk.types.WorldTile;
import org.tribot.script.sdk.util.TribotRandom;
import scripts.Priority;
import scripts.Task;
import scripts.data.Constants;
import scripts.data.Utilities;
import scripts.data.Vars;

import java.util.Optional;

public class NewClue implements Task {

    public static final WorldTile TILE = new WorldTile(3258, 3228, 0);
    public static final Area AREA = Area.fromRadius(TILE, 35);

    /**
     *
     * new WorldTile(3237, 3263, 0),
     *         new WorldTile(3253, 3263, 0),
     *         new WorldTile(3253, 3255, 0),
     *         new WorldTile(3265, 3255, 0),
     *         new WorldTile(3269, 3228, 0),
     *         new WorldTile(3267, 3210, 0),
     *         new WorldTile(3249, 3222, 0),
     *         new WorldTile(3239, 3236, 0)
     */

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return !Inventory.contains(Constants.CLUE) && Vars.get().getClue() == null;
    }

    @Override
    public void execute() {
        if (AREA.containsMyPlayer()) {
            // clue drop check
            if (findClue()
                    && Utilities.pickUp(Constants.CLUE)
                    && Waiting.waitUntil(() -> Inventory.contains(Constants.CLUE_ID))) {
                Waiting.waitNormal(600, 90);
                return;
            }

            if (!inCombat()) {
                Npc npc = findInteractingNPC();

                if (npc != null)
                    npc.interact("Attack");
                else
                    attackGoblin();

                Waiting.waitNormal(600, 90);
                if (Waiting.waitUntil(() -> !MyPlayer.isMoving() && MyPlayer.isAnimating()))
                    Waiting.waitNormal(600, 90);

                return;
            }

            if (MyPlayer.getRunEnergy() > TribotRandom.uniform(25, 45) && !Options.isRunEnabled())
                Options.setRunEnabled(true);

        } else {
            if (Utilities.walkWait(AREA))
                Waiting.waitNormal(600, 90);
        }
    }

    private boolean findClue() {
        return Query.groundItems()
                .idEquals(Constants.CLUE_ID)
                .isAny();
    }

    private Npc findInteractingNPC() {
        Optional<Npc> npc = Query.npcs()
                .nameEquals("Goblin")
                .isInteractingWithMe()
                .isHealthBarNotEmpty()
                .findBestInteractable();

        if (npc.isPresent()) {
            Npc n = npc.get();
            if (n.isInteractingWithMe() && n.isValid())
                return n;
        }
        return null;
    }

    private boolean inCombat() {
        return (Query.npcs()
                .nameEquals("Goblin")
                .isReachable()
                .isInteractingWithMe()
                .inArea(AREA)
                .hasLineOfSightTo(MyPlayer.getTile())
                .findBestInteractable()
                .isPresent() && (MyPlayer.isMoving() || MyPlayer.isAnimating()))
                ||
                Query.npcs()
                        .nameEquals("Goblin")
                        .isInteractingWithMe()
                        .isMyPlayerInteractingWith()
                        .isHealthBarNotEmpty()
                        .findBestInteractable()
                        .map(d -> d.isValid())
                        .orElse(false);
    }

    private boolean attackGoblin() {
        return Query.npcs()
                .inArea(AREA)
                .nameEquals("Goblin")
                .isHealthBarNotEmpty()
                .isNotBeingInteractedWith()
                //.sorted((npc1, npc2) -> npc1.isInteractingWithMe())
                .findBestInteractable()
                .map(d -> d.isValid() && d.interact("Attack"))
                .orElse(false);
    }

    @Override
    public String toString() {
        return "Getting new clue from goblins";
    }

}