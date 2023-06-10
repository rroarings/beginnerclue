package scripts.tasks.hotcold;

import org.tribot.script.sdk.*;
import org.tribot.script.sdk.types.WorldTile;
import scripts.Task;
import scripts.data.Utilities;
import scripts.data.Vars;
import scripts.data.clues.HotColdClue;
import scripts.tasks.AbstractClueTask;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class HotCold extends AbstractClueTask implements Task {

    public static final int DEVICE = 23183;

    private WorldTile location = null;
    private HotColdSolver hotColdSolver = null;
    private HotColdTemperature temperature = null;

    public static HotColdClue CLUE = HotColdClue.CLUES.get(0);

    public HotCold() {
        super(CLUE);
        initHc();
        MessageListening.addServerMessageListener(listener -> {
            if (listener.startsWith("The device is")) {
                handleTemp(listener);
            }
        });
    }

    @Override
    public void execute() {
        if (!Inventory.contains(DEVICE)) {
            Log.error("No strange device found!");

            // attempt to walk to reldo and get device

            Vars.get().setRunning(false);
            return;
        }

        if (!Inventory.contains(952)) {
            Log.error("No spade found!");
            Vars.get().setRunning(false);
            return;
        }

        // update temperature
        if (location == null) {
            Vars.get().setStatus("Finding hot/cold location...");
            if (Utilities.clickInventory("Strange device")) {
                Waiting.waitNormal(2000, 90);

                if (location != null) {
                    Log.info("at location!");
                    return;
                }

                Log.info("Current temp: " + temperature);
                Set<HotColdLocation> locs = hotColdSolver.getPossibleLocations();
                for (HotColdLocation loc: locs)
                    Log.info("Possible locations: " + loc.getArea());

                Optional<HotColdLocation> hcl = locs.stream().findFirst();

                if (hcl.isPresent()) {
                    if (Utilities.walkWait(hcl.get().getWorldTile())
                            && Waiting.waitUntil(() -> !MyPlayer.isMoving())) {
                        Waiting.waitNormal(600, 90);
                    }
                } else {
                    // reset locations
                    initHc();
                }

            }

            return;
        }

        Vars.get().setStatus("Found location! Digging...");
        if (Utilities.clickInventory("Spade")
                && Waiting.waitUntil(() -> !MyPlayer.isAnimating())) {
            Waiting.waitNormal(2000, 90);

            // found chatscreen, clue/casket found
            if (ChatScreen.isOpen()) {
                Vars.get().setClue(null);
                initHc();
                return;
            }

            // not found, back to search
            location = null;
        }
    }

    public void initHc() {
        Set<HotColdLocation> locations = Arrays.stream(HotColdLocation.values())
                .collect(Collectors.toSet());
        hotColdSolver = new HotColdSolver(locations);
        location = null;
        temperature = null;
    }

    private void handleTemp(String message) {
        HotColdTemperature temp = HotColdTemperature.getFromTemperatureSet(HotColdTemperature.BEGINNER_HOT_COLD_TEMPERATURES, message);

        if (temp == null) {
            Log.info("Temperature not found!");
            return;
        }

        temperature = temp;
        if (temperature == HotColdTemperature.BEGINNER_VISIBLY_SHAKING) {
            location = MyPlayer.getTile();
            Log.info("Found location: " + MyPlayer.getTile());
            return;
        }

        HotColdTemperatureChange temperatureChange = HotColdTemperatureChange.of(message);
        hotColdSolver.signal(MyPlayer.getTile(), temperature, temperatureChange);
    }
}
