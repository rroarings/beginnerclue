package scripts.tasks.emote;

import org.tribot.script.sdk.types.WorldTile;
import scripts.data.clues.EmoteClue;

public class BobClap extends EmoteClueTask {

    public static final WorldTile BANK_TILE = new WorldTile(3208, 3219, 2);

    public static final EmoteClue CLUE = EmoteClue.CLUES.get(5);

    public BobClap() {
        super(CLUE, BANK_TILE);
    }

}
