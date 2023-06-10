package scripts.tasks.cryptic;

import org.tribot.script.sdk.types.Area;
import scripts.data.clues.CrypticClue;

public class HansTalk extends CrypticClueTask {

    public static final CrypticClue CLUE = CrypticClue.CLUES.get(2);

    public HansTalk() {
        super(CLUE);
        clue.setArea(Area.fromRadius(clue.getTile(), 12));
    }

}