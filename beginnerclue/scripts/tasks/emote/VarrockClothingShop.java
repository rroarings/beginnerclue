package scripts.tasks.emote;

import org.tribot.script.sdk.types.WorldTile;
import scripts.data.clues.EmoteClue;

public class VarrockClothingShop extends EmoteClueTask {

    public static final WorldTile BANK_TILE = new WorldTile(3185, 3436, 0);

    public static final EmoteClue CLUE = EmoteClue.CLUES.get(1);

    public VarrockClothingShop() {
        super(CLUE, BANK_TILE);
    }

}
