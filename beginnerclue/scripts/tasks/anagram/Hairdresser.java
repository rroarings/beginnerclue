package scripts.tasks.anagram;

import scripts.data.clues.AnagramClue;

public class Hairdresser extends AnagramClueTask {

    public static AnagramClue CLUE = AnagramClue.CLUES.get(8);

    public Hairdresser() {
        super(CLUE);
    }
}
