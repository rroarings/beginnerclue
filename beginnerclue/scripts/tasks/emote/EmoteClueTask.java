package scripts.tasks.emote;

import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.EquipmentItem;
import org.tribot.script.sdk.types.WorldTile;
import scripts.Task;
import scripts.data.RestockJob;
import scripts.data.Utilities;
import scripts.data.Vars;
import scripts.data.clues.ClueScroll;
import scripts.data.clues.EmoteClue;
import scripts.tasks.AbstractClueTask;

import java.util.Set;
import java.util.stream.Collectors;

public class EmoteClueTask extends AbstractClueTask implements Task {

    private WorldTile bankTile;
    private Area bankArea;

    public EmoteClueTask(ClueScroll clue) {
        super(clue);
        this.bankTile = null;
        this.bankArea = null;
    }

    public EmoteClueTask(ClueScroll clue, WorldTile bankTile) {
        super(clue);
        this.bankTile = bankTile;
        this.bankArea = Area.fromRadius(bankTile, 2);
    }

    @Override
    public void execute() {
        if (((EmoteClue) clue).getItems().length == 0) {
            noItemClue();
        } else {
            itemClue();
        }
    }

    private void noItemClue() {
        if (!clue.getArea().containsMyPlayer()) {
            if (Utilities.walkWait(clue.getArea())) {
                Waiting.waitNormal(600, 90);
            }
        } else {
            if (Utilities.handleUriEmote(((EmoteClue) clue).getEmote())) {
                Waiting.waitNormal(600, 90);
                Vars.get().setClue(null);
            }
        }
    }

    private void itemClue() {
        if (!Utilities.itemsInInvOrEquipped(((EmoteClue) clue).getItems())) {
            if (!bankArea.containsMyPlayer()) {
                if (Utilities.walkWait(bankArea))
                    Waiting.waitNormal(600, 90);

                return;
            }
            // at bank
            if (!Bank.isOpen() && Waiting.waitUntil(Bank::open))
                return;

            // withdraw items
            boolean missingItem = false;
            for (String item: ((EmoteClue) clue).getItems()) {
                if (!Inventory.contains(item) && !Equipment.contains(item)) {
                    if (Bank.contains(item)) {
                        Bank.withdraw(item, 1);
                        Waiting.waitNormal(600, 90);
                    } else {
                        Log.info("Missing item: " + item);
                        if (Vars.get().isBuyEmoteItems()) {
                            missingItem = true;
                            Vars.get().addRestockJob(new RestockJob(item, 1)); // 1 for emote items
                        } else {
                            Log.warn("Not set to buy emote items! Ending...");
                            Vars.get().setRunning(false);
                            return;
                        }
                    }
                }
            }
            Waiting.waitUntil(Bank::close);

            // items missing, go to restock
            if (missingItem)
                return;

        } else {

            if (Utilities.equipmentContainsAll(((EmoteClue) clue).getItems())) {

                if (!clue.getArea().containsMyPlayer() && Utilities.walkWait(clue.getArea())) {
                    Waiting.waitNormal(600, 90);
                    return;
                }

                // handle uri
                if (Utilities.handleUriEmote(((EmoteClue) clue).getEmote())) {
                    Waiting.waitNormal(600, 90);

                    Set<String> startingEquip = Vars.get().getEquipment();
                    if (startingEquip != null) {
                        for (String ei: startingEquip) {
                            if (!Equipment.contains(ei) && Inventory.contains(ei)) {
                                Equipment.equip(ei);
                                Waiting.waitNormal(600, 90);
                            }
                        }
                    }

                    Vars.get().setClue(null);
                }

            } else {
                // do equip
                for (String item: ((EmoteClue) clue).getItems()) {
                    if (!Equipment.contains(item)) {
                        Equipment.equip(item);
                        Waiting.waitNormal(600, 90);
                    }
                }
            }
        }
    }

}