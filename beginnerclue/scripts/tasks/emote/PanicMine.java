package scripts.tasks.emote;

import scripts.data.clues.EmoteClue;

public class PanicMine extends EmoteClueTask {

    public static EmoteClue CLUE = EmoteClue.CLUES.get(2);

    public PanicMine() {
        super(CLUE);
    }

}