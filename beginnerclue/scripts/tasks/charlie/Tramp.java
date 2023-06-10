package scripts.tasks.charlie;

import org.tribot.script.sdk.*;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.WorldTile;
import scripts.Task;
import scripts.data.Utilities;
import scripts.data.Vars;
import scripts.data.clues.CharlieClue;
import scripts.tasks.AbstractClueTask;

public class Tramp extends AbstractClueTask implements Task {

    public static final String NPC = "Charlie the Tramp";
    public static final String[] ITEMS = {
            "Iron ore",
            "Iron dagger",
            "Raw herring",
            "Raw trout",
            "Trout",
            "Pike",
            "Leather body",
            "Leather chaps"
    };

    public static final WorldTile VW_BANK_TILE = new WorldTile(3185, 3436, 0);
    public static final Area VW_BANK = Area.fromRadius(VW_BANK_TILE, 2);

    public static final WorldTile TRAMP_TILE = new WorldTile(3209, 3392, 0);
    public static final Area TRAMP_AREA = Area.fromRadius(TRAMP_TILE, 6);

    public static final CharlieClue CLUE = CharlieClue.CLUES.get(0);

    public Tramp() {
        super(CLUE);
    }

    @Override
    public void execute() {
        if (!Utilities.inventoryContainsAll(ITEMS)) {
            if (!VW_BANK.containsMyPlayer()) {
                if (Utilities.walkWait(VW_BANK))
                    Waiting.waitNormal(600, 90);

                return;
            }
            // at bank
            if (!Bank.isOpen() && Waiting.waitUntil(Bank::open))
                return;

            // withdraw items
            for (String item: ITEMS) {
                if (!Inventory.contains(item)) {
                    if (Bank.contains(item)) {
                        Bank.withdraw(item, 1);
                        Waiting.waitNormal(600, 90);
                    } else {
                        Log.info("Missing item: " + item);
                        Vars.get().setRunning(false);
                        return;
                    }
                }
            }
            Waiting.waitUntil(Bank::close);

        } else {
            // walk to tramp
            if (!TRAMP_AREA.containsMyPlayer() && Utilities.walkWait(TRAMP_AREA)) {
                Waiting.waitNormal(600, 90);
                return;
            }

            // handle charlie
            if (initialCharlie()) {
                Waiting.waitNormal(600, 90);
                Vars.get().setClue(null);
            }
        }
    }

    // TODO: redo all of this
    public static boolean initialCharlie() {
        if (Utilities.waitNpc(NPC)
                && Waiting.waitUntil(() -> ChatScreen.isOpen())) {

            if (ChatScreen.containsMessage("Spare some change guv?"))
                return true;

            // click to continue
            for (int i = 0; i <= 3; i++) {
                if (ChatScreen.clickContinue() && Waiting.waitUntil(() -> ChatScreen.isClickContinueOpen()))
                    Waiting.waitNormal(600, 90);
            }

            // give item
            if (Utilities.waitNpc(NPC)
                    && Waiting.waitUntil(() -> ChatScreen.isOpen())
                    && ChatScreen.containsMessage("You give Charlie")) {
                ChatScreen.clickContinue();
                return true;
            }
        }
        return false;
    }

}