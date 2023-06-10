package scripts.data;

import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.WorldTile;

public enum ClueMonster {

    GOBLIN("Goblin", Area.fromRadius(new WorldTile(3258, 3228, 0), 35)),
    MAN("Man", Area.fromPolygon(new WorldTile(3231, 3214, 0),
            new WorldTile(3230, 3218, 0),
            new WorldTile(3230, 3221, 0),
            new WorldTile(3242, 3221, 0),
            new WorldTile(3242, 3216, 0),
            new WorldTile(3240, 3216, 0),
            new WorldTile(3240, 3212, 0),
            new WorldTile(3238, 3212, 0),
            new WorldTile(3250, 3195, 0),
            new WorldTile(3224, 3201, 0))),
    COW("Cow", Area.fromPolygon(new WorldTile(3192, 3303, 0),
            new WorldTile(3192, 3281, 0),
            new WorldTile(3214, 3284, 0),
            new WorldTile(3214, 3294, 0),
            new WorldTile(3210, 3304, 0)));

    private String name;
    private Area area;

    ClueMonster(String name, Area area) {
        this.name = name;
        this.area = area;
    }

    public static ClueMonster fromName(String name) {
        for (ClueMonster cm: ClueMonster.values()) {
            if (cm.name.equals(name))
                return cm;
        }
        return null;
    }
}
