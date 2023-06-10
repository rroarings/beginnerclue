package scripts.form;

import javafx.fxml.Initializable;

/**
 * @author Laniax
 */

public abstract class AbstractGUIController implements Initializable {

    private GUI gui = null;

    public abstract boolean getEnableNotifications();

    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    public GUI getGUI() {
        return this.gui;
    }
}