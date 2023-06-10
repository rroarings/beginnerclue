package scripts.tasks.anagram;

import scripts.data.clues.AnagramClue;

public class Fortunato extends AnagramClueTask {

    public static AnagramClue CLUE = AnagramClue.CLUES.get(6);

    public Fortunato() {
        super(CLUE);
    }
}
