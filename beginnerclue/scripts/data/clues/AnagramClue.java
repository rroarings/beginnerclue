package scripts.data.clues;

import org.tribot.script.sdk.types.WorldTile;

import java.util.Arrays;
import java.util.List;

public class AnagramClue extends ClueScroll implements NpcClue {

    public static final List<AnagramClue> CLUES = Arrays.asList(
            new AnagramClue(
                    "The anagram reveals<br> who to speak to next:<br>CARPET AHOY",
                    "Anagram: Apothecary - Varrock",
                    "Apothecary",
                    new WorldTile(3195, 3404, 0)
            ),
            new AnagramClue(
                    "The anagram reveals<br> who to speak to next:<br>I CORD",
                    "Anagram: Doric - North of Falador",
                    "Doric",
                    new WorldTile(2951, 3450, 0)
            ),
            new AnagramClue(
                    "The anagram reveals<br> who to speak to next:<br>IN BAR",
                    "Anagram: Brian - Port Sariam axe shop",
                    "Brian",
                    new WorldTile(3026, 3249, 0)
            ),
            new AnagramClue(
                    "The anagram reveals<br> who to speak to next:<br>RAIN COVE",
                    "Anagram: Veronica - Draynor Manor",
                    "Veronica",
                    new WorldTile(3110, 3329, 0)
            ),
            new AnagramClue(
                    "The anagram reveals<br> who to speak to next:<br>AN EARL",
                    "Anagram: Ranael - AL-kharid skirt shop",
                    "Ranael",
                    new WorldTile(3315, 3163, 0)
            ),
            new AnagramClue(
                    "The anagram reveals<br> who to speak to next:<br>CHAR GAME DISORDER",
                    "Anagram: Archmage Sedridor - Wizard's tower basement",
                    "Archmage Sedridor",
                    new WorldTile(3104, 9571, 0)
            ),
            new AnagramClue(
                    "The anagram reveals<br> who to speak to next:<br>TAUNT ROOF",
                    "Anagram: Fortunato - Draynor marketplace",
                    "Fortunato",
                    new WorldTile(3082, 3252, 0)
            ),
            new AnagramClue(
                    "The anagram reveals<br> who to speak to next:<br>RUG DETER",
                    "Anagram: Gertrude - West of Varrock",
                    "Gertrude",
                    new WorldTile(3150, 3410, 0)
            ),
            new AnagramClue(
                    "The anagram reveals<br> who to speak to next:<br>SIR SHARE RED",
                    "Anagram: Hairdresser - Falador",
                    "Hairdresser",
                    new WorldTile(2945, 3379, 0)
            )
    );

    private String npc;

    public AnagramClue(String step, String description, String npc, WorldTile tile) {
        super(step, description, tile);
        this.npc = npc;
    }

    @Override
    public String getNpc() {
        return npc;
    }

    public static AnagramClue forStep(String step)  {
        for (AnagramClue clue : CLUES) {
            if (clue.getStep().equals(step)) {
                return clue;
            }
        }
        return null;
    }

}