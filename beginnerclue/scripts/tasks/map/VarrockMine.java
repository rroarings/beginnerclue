package scripts.tasks.map;

import scripts.data.clues.MapClue;

public class VarrockMine extends MapClueTask {

    public static MapClue CLUE = MapClue.MAP_CLUES.get(1);

    public VarrockMine() {
        super(CLUE);
    }
}
