package scripts.tasks.anagram;

import scripts.data.clues.AnagramClue;

public class Doric extends AnagramClueTask {

    public static final AnagramClue CLUE = AnagramClue.CLUES.get(1);

    public Doric() {
        super(CLUE);
    }
}
