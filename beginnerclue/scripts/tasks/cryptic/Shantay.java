package scripts.tasks.cryptic;

import org.tribot.script.sdk.types.Area;
import scripts.data.clues.CrypticClue;

public class Shantay extends CrypticClueTask {

    public static final CrypticClue CLUE = CrypticClue.CLUES.get(3);

    public Shantay() {
        super(CLUE);
        clue.setArea(Area.fromRadius(clue.getTile(), 5));
    }

}
