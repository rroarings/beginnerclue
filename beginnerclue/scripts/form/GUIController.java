package scripts.form;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Login;
import org.tribot.script.sdk.query.Query;
import scripts.data.ClueMonster;
import scripts.data.Vars;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class GUIController extends AbstractGUIController {

    @Override
    public boolean getEnableNotifications() {
        return false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        monsterBox.getItems().addAll(
                "Goblin",
                "Man",
                "Cow"
        );
        locationBox.getItems().addAll(
                "Lumbridge"
        );

        monsterBox.getSelectionModel().selectFirst();
        locationBox.getSelectionModel().selectFirst();
    }

    /*
     * Main
     */
    @FXML private Button startScriptButton;
    @FXML private Button saveProfileField;
    @FXML private Button loadProfileFIeld;
    @FXML private Button viewPatchButton;
    @FXML private Button viewThreadButton;

    @FXML
    public void startScript() {
        Log.info("Script started via GUI!");

        // set monster
        Vars.get().setMonster(ClueMonster.fromName(monsterBox.getValue()));

        // set equipment
        Set<String> equip = new HashSet<>();
        if (!weaponField.getText().equals("")) equip.add(weaponField.getText());
        if (!shieldField.getText().equals("")) equip.add(shieldField.getText());
        if (!chestField.getText().equals("")) equip.add(chestField.getText());
        if (!legsField.getText().equals("")) equip.add(legsField.getText());
        if (!helmetField.getText().equals("")) equip.add(helmetField.getText());
        if (!capeField.getText().equals("")) equip.add(capeField.getText());
        if (!gloveField.getText().equals("")) equip.add(gloveField.getText());
        if (!bootsField.getText().equals("")) equip.add(bootsField.getText());
        if (!amuletField.getText().equals("")) equip.add(amuletField.getText());
        if (!ringField.getText().equals("")) equip.add(ringField.getText());
        Vars.get().setEquipment(equip);

        // set restocking charlie
        Vars.get().setRestockCharlie(enableRestockCheck.isSelected());
        if (enableRestockCheck.isSelected()) {
            try {
                int restockAmount = Integer.valueOf(amountRestock.getText());
                Vars.get().setCharlieRestockAmount(restockAmount);

                int pricePlus = Integer.valueOf(pricePlusRestock.getText());
                Vars.get().setBuyPlusTicks(pricePlus);
            } catch (NumberFormatException e) {
                Log.error("Error parsing restock amounts.");
                return;
            }
        }

        // set buy emote items + spade
        Vars.get().setBuyEmoteItems(enableEmoteBuyCheck.isSelected());
        if (enableEmoteBuyCheck.isSelected()) {
            try {
                int pricePlus = Integer.valueOf(pricePlusEmote.getText());
                Vars.get().setBuyEmotePlusTicks(pricePlus);
            } catch (NumberFormatException e) {
                Log.error("Error parsing restock amounts.");
                return;
            }
        }

        this.getGUI().close();
    }

    @FXML
    public void saveProfile() {
        Log.info("save");
    }

    @FXML
    public void loadProfile() {
        Log.info("load");
    }

    @FXML
    public void viewPatchNotes() {
        try {
            Desktop.getDesktop().browse(URI.create("www.google.com"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void viewThread() {
        try {
            Desktop.getDesktop().browse(URI.create("www.google.com"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * Combat
     */

    @FXML private ComboBox<String> monsterBox;
    @FXML private ComboBox<String> locationBox;

    @FXML
    public void updateMonster() {
        Log.info("monster change");

        // narrow location based on monster
    }

    @FXML
    public void updateLocation() {
        Log.info("location change");

        // narrow monster choices
    }

    /*
     * Equipment
     */
    @FXML private Button currEquipButton;
    @FXML private TextField weaponField;
    @FXML private TextField shieldField;
    @FXML private TextField chestField;
    @FXML private TextField legsField;
    @FXML private TextField helmetField;
    @FXML private TextField capeField;
    @FXML private TextField gloveField;
    @FXML private TextField bootsField;
    @FXML private TextField amuletField;
    @FXML private TextField ringField;

    @FXML
    public void uiLoadCurrentEquipment() {
        if (Login.isLoggedIn()) {
            String weapon = getEquipmentInSlot(Equipment.Slot.WEAPON);
            weaponField.setText(weapon != null ? weapon : "");

            String shield = getEquipmentInSlot(Equipment.Slot.SHIELD);
            shieldField.setText(shield != null ? shield : "");

            String chest = getEquipmentInSlot(Equipment.Slot.BODY);
            chestField.setText(chest != null ? chest : "");

            String legs = getEquipmentInSlot(Equipment.Slot.LEGS);
            legsField.setText(legs != null ? legs : "");

            String helmet = getEquipmentInSlot(Equipment.Slot.HEAD);
            helmetField.setText(helmet != null ? helmet : "");

            String cape = getEquipmentInSlot(Equipment.Slot.CAPE);
            capeField.setText(cape != null ? cape : "");

            String gloves = getEquipmentInSlot(Equipment.Slot.HANDS);
            gloveField.setText(gloves != null ? gloves : "");

            String boots = getEquipmentInSlot(Equipment.Slot.FEET);
            bootsField.setText(boots != null ? boots : "");

            String amulet = getEquipmentInSlot(Equipment.Slot.NECK);
            amuletField.setText(amulet != null ? amulet : "");

            String ring = getEquipmentInSlot(Equipment.Slot.RING);
            ringField.setText(ring != null ? ring : "");
        } else {
            Log.warn("Please login before attempting to grab current equipment.");
        }
    }

    private String getEquipmentInSlot(Equipment.Slot slot) {
        return Query.equipment()
                .slotEquals(slot)
                .stream()
                .findFirst()
                .map(s -> s.getName())
                .orElse(null);
    }

    /*
     * Restocking
     */

    @FXML private CheckBox enableRestockCheck;
    @FXML private CheckBox enableEmoteBuyCheck;
    @FXML private TextField pricePlusRestock;
    @FXML private TextField amountRestock;
    @FXML private TextField pricePlusEmote;

    @FXML
    public void enableRestock() {
        // disable price fields
    }

    @FXML
    public void enableEmoteBuy() {
        // disable price fields
    }
}