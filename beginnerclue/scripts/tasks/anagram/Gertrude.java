package scripts.tasks.anagram;

import scripts.data.clues.AnagramClue;

public class Gertrude extends AnagramClueTask {

    public static AnagramClue CLUE = AnagramClue.CLUES.get(7);

    public Gertrude() {
        super(CLUE);
    }
}
