package scripts.tasks.cryptic;

import org.tribot.script.sdk.types.Area;
import scripts.data.clues.CrypticClue;

public class CookTalk extends CrypticClueTask {

    public static final CrypticClue CLUE = CrypticClue.CLUES.get(1);

    public CookTalk() {
        super(CLUE);
        clue.setArea(Area.fromRadius(clue.getTile(), 5));
    }

}
