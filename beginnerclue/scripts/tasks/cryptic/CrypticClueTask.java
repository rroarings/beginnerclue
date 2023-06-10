package scripts.tasks.cryptic;

import org.tribot.script.sdk.ChatScreen;
import org.tribot.script.sdk.Waiting;
import scripts.Task;
import scripts.data.Utilities;
import scripts.data.Vars;
import scripts.data.clues.ClueScroll;
import scripts.data.clues.CrypticClue;
import scripts.tasks.AbstractClueTask;

public class CrypticClueTask extends AbstractClueTask implements Task {

    public CrypticClueTask(ClueScroll clue) {
        super(clue);
    }

    @Override
    public void execute() {
        if (clue.getArea().containsMyPlayer()) {
            if (Utilities.waitNpc(((CrypticClue) clue).getNpc())
                    && Waiting.waitUntil(ChatScreen::isClickContinueOpen)
                    && ChatScreen.clickContinue()) {
                Waiting.waitNormal(600, 90);
                Vars.get().setClue(null);
            }

            return;
        }

        if (Utilities.walkWait(clue.getArea()))
            Waiting.waitNormal(600, 90);
    }
}
