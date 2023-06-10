package scripts.tasks.anagram;

import org.tribot.script.sdk.ChatScreen;
import org.tribot.script.sdk.Waiting;
import scripts.Task;
import scripts.data.Utilities;
import scripts.data.Vars;
import scripts.data.clues.AnagramClue;
import scripts.tasks.AbstractClueTask;

public class AnagramClueTask extends AbstractClueTask implements Task {

    public AnagramClueTask(AnagramClue clue) {
        super(clue);
    }

    @Override
    public void execute() {
        if (!clue.getArea().containsMyPlayer()) {
            if (Utilities.walkWait(clue.getArea())) {
                Waiting.waitNormal(600, 90);
            }
        } else {
            Vars.get().setStatus(clue.getDescription());
            if (Utilities.waitNpc(((AnagramClue) clue).getNpc())
                    && Waiting.waitUntil(ChatScreen::isClickContinueOpen)
                    && ChatScreen.clickContinue()) {
                Waiting.waitNormal(600, 90);
                Vars.get().setClue(null);
            }
        }
    }

}