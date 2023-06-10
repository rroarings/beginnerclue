package scripts.data.clues;

import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.WorldTile;

public abstract class ClueScroll {

    private boolean spadeNeeded = false;
    private String step = null;
    private int widgetPath = 203;

    private String description = null;

    private WorldTile tile = null;

    private Area area = null;

    public ClueScroll(String step, String description) {
        this.step = step;
        this.description = description;
    }

    public ClueScroll(String step, String description, WorldTile tile) {
        this.step = step;
        this.description = description;
        this.tile = tile;
        this.area = Area.fromRadius(tile, 2);
    }

    public ClueScroll(String step, String description, WorldTile tile, int widgetPath) {
        this.step = step;
        this.description = description;
        this.tile = tile;
        this.area = Area.fromRadius(tile, 2);
        this.widgetPath = widgetPath;
    }

    public boolean isSpadeNeeded() {
        return spadeNeeded;
    }

    public void setSpadeNeeded(boolean spadeNeeded) {
        this.spadeNeeded = spadeNeeded;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public WorldTile getTile() {
        return tile;
    }

    public void setTile(WorldTile tile) {
        this.tile = tile;
    }

    public int getWidgetPath() {
        return widgetPath;
    }

    public void setWidgetPath(int widgetPath) {
        this.widgetPath = widgetPath;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

}