package scripts.tasks.map;

import scripts.data.clues.MapClue;

public class WizardsTower extends MapClueTask {

    public static MapClue CLUE = MapClue.MAP_CLUES.get(3);

    public WizardsTower() {
        super(CLUE);
    }
}
