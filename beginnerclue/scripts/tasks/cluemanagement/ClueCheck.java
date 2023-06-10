package scripts.tasks.cluemanagement;

import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.Widgets;
import org.tribot.script.sdk.input.Keyboard;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Widget;
import scripts.Priority;
import scripts.Task;
import scripts.data.Constants;
import scripts.data.Vars;
import scripts.data.clues.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClueCheck implements Task {

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return Inventory.contains(Constants.CLUE_ID) && Vars.get().getClue() == null;
    }

    private static List<Integer> widgetIds = null;

    public ClueCheck() {
        widgetIds = new ArrayList<>();
        widgetIds.add(203);
        widgetIds.add(346);
        widgetIds.add(347);
        widgetIds.add(348);
        widgetIds.add(351);
        widgetIds.add(356);
    }

    @Override
    public void execute() {
        if (clickClue() && Waiting.waitUntil(() -> getClueWidget() != null)) {
            Widget w = getClueWidget();

            if (w.getIndex() == 203) {
                Widget clueScreen = w.getChild(2).get();
                Optional<String> text = clueScreen.getText();
                if (text.isPresent()) {
                    Log.info("Clue: " + text.get());
                    ClueScroll scroll = fromStep(text.get());
                    if (scroll == null) {
                        Log.error("Clue not supported: " + text.get());
                        Vars.get().setRunning(false);
                        return;
                    }
                    Vars.get().setClue(scroll);
                } else {
                    Log.error("Clue not found!");
                    Vars.get().setRunning(false);
                }
            } else {
                // map clue
                ClueScroll scroll = MapClue.forWidget(w.getIndex());
                Log.info("Clue: " + scroll.getDescription());
                Vars.get().setClue(scroll);
                Log.info("Currently set clue: " + Vars.get().getClue().getDescription());
            }
            Waiting.waitUniform(600, 200);
            Keyboard.pressEscape();
        }
    }

    private boolean clickClue() {
        return Query.inventory()
                .idEquals(Constants.CLUE_ID)
                .findFirst()
                .map(clue -> clue.click())
                .orElse(false);
    }

    public static Widget getClueWidget() {
        Widget w = null;

        for (Integer id: widgetIds) {
            if (isWidgetVisible(id)) {
                w = Widgets.get(id).get();
                break;
            }
        }

        return w;
    }

    public static boolean isWidgetVisible(int... indexPath) {
        return Query.widgets()
                .inIndexPath(indexPath)
                .isVisible()
                .isAny();
    }

    public ClueScroll fromStep(String step) {
        AnagramClue anagramClue = AnagramClue.forStep(step);
        if (anagramClue != null)
            return anagramClue;

        CrypticClue crypticClue = CrypticClue.forStep(step);
        if (crypticClue != null)
            return crypticClue;

        CharlieClue charlieClue = CharlieClue.forStep(step);
        if (charlieClue != null)
            return charlieClue;

        EmoteClue emoteClue = EmoteClue.forStep(step);
        if (emoteClue != null)
            return emoteClue;

        HotColdClue hotColdClue = HotColdClue.forStep(step);
        if (hotColdClue != null)
            return hotColdClue;

        return null;
    }

    @Override
    public String toString() {
        return "Determining current clue...";
    }

}