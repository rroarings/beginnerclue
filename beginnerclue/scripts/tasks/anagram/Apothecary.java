package scripts.tasks.anagram;

import scripts.data.clues.AnagramClue;

public class Apothecary extends AnagramClueTask {

    public static AnagramClue CLUE = AnagramClue.CLUES.get(0);

    public Apothecary() {
        super(CLUE);
    }

}