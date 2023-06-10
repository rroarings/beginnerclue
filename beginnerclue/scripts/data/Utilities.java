package scripts.data;

import org.tribot.script.sdk.ChatScreen;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.WorldTile;
import org.tribot.script.sdk.util.TribotRandom;
import org.tribot.script.sdk.walking.GlobalWalking;
import scripts.Emote;

public class Utilities {

    public static boolean waitObject(int id) {
        return Query.gameObjects()
                .idEquals(id)
                .findClosestByPathDistance()
                .map(obj -> obj.click())
                .orElse(false);
    }

    public static boolean waitNpc(String name) {
        return Query.npcs()
                .nameEquals(name)
                .findBestInteractable()
                .map(npc -> npc.interact("Talk-to"))
                .orElse(false);
    }

    public static boolean clickInventory(String name) {
        return Query.inventory()
                .nameEquals(name)
                .findFirst()
                .map(item -> item.click())
                .orElse(false);
    }

    public static boolean pickUp(String name) {
        return Query.groundItems()
                .nameEquals(name)
                .findBestInteractable()
                .map(item -> item.interact("Take"))
                .orElse(false);
    }

    public static boolean waitUri() {
        return Query.npcs()
                .nameEquals("Uri")
                .isInteractingWithMe()
                .findBestInteractable()
                .map(uri -> uri.click())
                .orElse(false);
    }

    public static boolean handleUriEmote(Emote emote) {
        return (emote.doEmote()
                && Waiting.waitUntil(TribotRandom.uniform(1800, 2400), Utilities::waitUri)
                && Waiting.waitUntil(ChatScreen::isClickContinueOpen)
                && ChatScreen.clickContinue());
    }

    public static boolean walkWait(WorldTile tile) {
        return GlobalWalking.walkTo(tile) && Waiting.waitUntil(() -> tile.equals(MyPlayer.getTile()));
    }

    public static boolean walkWait(Area area) {
        return GlobalWalking.walkTo(area.getCenter()) && Waiting.waitUntil(area::containsMyPlayer);
    }

    public static boolean inventoryContainsAll(String[] items) {
        return Query.inventory().
                nameEquals(items).
                count() == items.length;
    }

    public static boolean equipmentContainsAll(String[] items) {
        return Query.equipment()
                .nameEquals(items)
                .count() == items.length;
    }

    public static boolean itemsInInvOrEquipped(String[] items) {
        // inventory
        int invCount = Query.inventory()
                .nameEquals(items)
                .distinctByName()
                .count();

        // equipment
        int equipmentCount = Query.equipment()
                .nameEquals(items)
                .distinctByName()
                .count();

        return (invCount + equipmentCount) >= items.length;
    }
}
