package scripts;

import org.tribot.script.sdk.Camera;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.ScriptListening;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.painting.Painting;
import org.tribot.script.sdk.painting.template.basic.BasicPaintTemplate;
import org.tribot.script.sdk.painting.template.basic.PaintLocation;
import org.tribot.script.sdk.painting.template.basic.PaintRows;
import org.tribot.script.sdk.painting.template.basic.PaintTextRow;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.script.TribotScript;
import org.tribot.script.sdk.script.TribotScriptManifest;
import scripts.data.Constants;
import scripts.data.Vars;
import scripts.form.GUI;
import scripts.paint.CustomMousePaint;
import scripts.tasks.anagram.*;
import scripts.tasks.charlie.Tramp;
import scripts.tasks.cluemanagement.ClueCheck;
import scripts.tasks.cluemanagement.NewClue;
import scripts.tasks.cryptic.BarbVillageTower;
import scripts.tasks.cryptic.CookTalk;
import scripts.tasks.cryptic.HansTalk;
import scripts.tasks.cryptic.Shantay;
import scripts.tasks.emote.*;
import scripts.tasks.hotcold.HotCold;
import scripts.tasks.map.*;
import scripts.tasks.restock.Restocker;

import java.awt.*;

@TribotScriptManifest(
		name = "Beginner Clues",
		author = "levunit",
		category = "Mini-games",
		description = "Does beginners"
)
public class BeginnerClues implements TribotScript {

	private GUI gui;

	@Override
	public void execute(final String args) {
		ScriptListening.addEndingListener(() -> {
			if (gui != null && gui.isOpen())
				gui.close();
		});

		gui = new GUI(UI);
		gui.show();
		while (gui.isOpen())
			Waiting.wait(500);

		TaskSet tasks = new TaskSet(
				// clue management
				new NewClue(),
				new ClueCheck(),
				// map clues
				new ChampionsGuild(),
				new FaladorStones(),
				new VarrockMine(),
				new DraynorBank(),
				new WizardsTower(),
				// emote clues
				new SpinMaceShop(),
				new VarrockClothingShop(),
				new PanicMine(),
				new BrugsenBow(),
				new ArisRaspberry(),
				new BobClap(),
				// cryptic clues
				new BarbVillageTower(),
				new CookTalk(),
				new HansTalk(),
				new Shantay(),
				// anagram clues
				new Apothecary(),
				new PortSarimAxeShop(),
				new Doric(),
				new Veronica(),
				new Ranael(),
				new Archmage(),
				new Fortunato(),
				new Gertrude(),
				new Hairdresser(),
				// hot/cold clues
				new HotCold(),
				// tramp clues
				new Tramp(),
				// restocking
				new Restocker()
		);

		// get casket count
		updateCasketCount();

		// create paint
		mkPaint();

		Camera.setZoomPercent(15);

		while (Vars.get().isRunning()) {
			Task task = tasks.getValidTask();

			if (task != null) {
				Vars.get().setStatus(task.toString());
				task.execute();
			}

			// check for new casket
			calcCasketsGained();

			Waiting.waitUniform(20, 40);
		}
	}

	public void updateCasketCount() {
		int casketCount = Query.inventory()
				.nameEquals(Constants.CASKET)
				.sumStacks();
		Log.info("Starting with casket count: " + casketCount);
		Vars.get().setInitialCasketCount(casketCount);
	}

	public void calcCasketsGained() {
		boolean casketGained = Query.inventory()
				.nameEquals(Constants.CASKET)
				.sumStacks() > (Vars.get().getCasketCount() + Vars.get().getInitialCasketCount());

		if (casketGained) {
			int c = Query.inventory().nameEquals(Constants.CASKET).sumStacks();
			Vars.get().setCasketCount(c - Vars.get().getInitialCasketCount());
		}
	}

	private void mkPaint() {
		PaintTextRow runningPaintTemplate =
				PaintTextRow.builder()
						.background(new Color(120, 123, 128, 255))
						.font(new Font("Verdana", Font.BOLD, 14))
						.build();
		BasicPaintTemplate paint = BasicPaintTemplate.builder()
				.row(PaintRows.scriptName(runningPaintTemplate.toBuilder()))
				.row(PaintRows.runtime(runningPaintTemplate.toBuilder()))
				.row(runningPaintTemplate.toBuilder().label("Status")
						.value(() -> Vars.get().getStatus()).build())
				.row(runningPaintTemplate.toBuilder().label("Caskets")
						.value(() -> Vars.get().getCasketCount()).build())
				.location(PaintLocation.BOTTOM_LEFT_VIEWPORT)
				.build();
		Painting.addPaint(paint::render);
		Painting.setMousePaint(new CustomMousePaint());
	}


	private static final String UI = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
			"\n" +
			"<!--\n" +
			"  Copyright (c) 2015, 2019, Gluon and/or its affiliates.\n" +
			"  All rights reserved. Use is subject to license terms.\n" +
			"\n" +
			"  This file is available and licensed under the following license:\n" +
			"\n" +
			"  Redistribution and use in source and binary forms, with or without\n" +
			"  modification, are permitted provided that the following conditions\n" +
			"  are met:\n" +
			"\n" +
			"  - Redistributions of source code must retain the above copyright\n" +
			"    notice, this list of conditions and the following disclaimer.\n" +
			"  - Redistributions in binary form must reproduce the above copyright\n" +
			"    notice, this list of conditions and the following disclaimer in\n" +
			"    the documentation and/or other materials provided with the distribution.\n" +
			"  - Neither the name of Oracle Corporation nor the names of its\n" +
			"    contributors may be used to endorse or promote products derived\n" +
			"    from this software without specific prior written permission.\n" +
			"\n" +
			"  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS\n" +
			"  \"AS IS\" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT\n" +
			"  LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR\n" +
			"  A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT\n" +
			"  OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,\n" +
			"  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT\n" +
			"  LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,\n" +
			"  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY\n" +
			"  THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT\n" +
			"  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE\n" +
			"  OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.\n" +
			"-->\n" +
			"\n" +
			"<?import javafx.scene.control.Button?>\n" +
			"<?import javafx.scene.control.CheckBox?>\n" +
			"<?import javafx.scene.control.ComboBox?>\n" +
			"<?import javafx.scene.control.Label?>\n" +
			"<?import javafx.scene.control.Separator?>\n" +
			"<?import javafx.scene.control.Tab?>\n" +
			"<?import javafx.scene.control.TabPane?>\n" +
			"<?import javafx.scene.control.TextArea?>\n" +
			"<?import javafx.scene.control.TextField?>\n" +
			"<?import javafx.scene.control.TitledPane?>\n" +
			"<?import javafx.scene.layout.AnchorPane?>\n" +
			"<?import javafx.scene.layout.Pane?>\n" +
			"<?import javafx.scene.layout.VBox?>\n" +
			"<?import javafx.scene.text.Font?>\n" +
			"\n" +
			"<VBox prefHeight=\"382.0\" prefWidth=\"467.0\" xmlns=\"http://javafx.com/javafx/19\" xmlns:fx=\"http://javafx.com/fxml/1\" fx:controller=\"scripts.form.GUIController\">\n" +
			"  <children>\n" +
			"    <AnchorPane maxHeight=\"-1.0\" maxWidth=\"-1.0\" prefHeight=\"-1.0\" prefWidth=\"-1.0\" VBox.vgrow=\"ALWAYS\">\n" +
			"         <children>\n" +
			"            <TabPane prefHeight=\"375.0\" prefWidth=\"467.0\" tabClosingPolicy=\"UNAVAILABLE\">\n" +
			"              <tabs>\n" +
			"                <Tab text=\"Start\">\n" +
			"                     <content>\n" +
			"                        <Pane prefHeight=\"200.0\" prefWidth=\"200.0\">\n" +
			"                           <children>\n" +
			"                              <Button id=\"startButton\" fx:id=\"startScriptButton\" layoutX=\"295.0\" layoutY=\"292.0\" mnemonicParsing=\"false\" onAction=\"#startScript\" prefHeight=\"40.0\" prefWidth=\"162.0\" text=\"Start\">\n" +
			"                                 <font>\n" +
			"                                    <Font size=\"19.0\" />\n" +
			"                                 </font>\n" +
			"                              </Button>\n" +
			"                              <TextArea editable=\"false\" layoutX=\"14.0\" layoutY=\"82.0\" prefHeight=\"250.0\" prefWidth=\"267.0\" style=\"-fx-background-color: black;\" text=\"Completes beginner clue scrolls in F2P.&#10;Kills selected monster in selected location to loot and complete clues.&#10;Supports restocking of Charlie the Tramp items!&#10;Please fill out configuration in each tab before running!\" wrapText=\"true\">\n" +
			"                                 <font>\n" +
			"                                    <Font size=\"16.0\" />\n" +
			"                                 </font>\n" +
			"                              </TextArea>\n" +
			"                              <Label layoutX=\"14.0\" layoutY=\"14.0\" prefHeight=\"61.0\" prefWidth=\"266.0\" text=\"Beginner Clue Collector\">\n" +
			"                                 <font>\n" +
			"                                    <Font name=\"Carlito Bold\" size=\"27.0\" />\n" +
			"                                 </font>\n" +
			"                              </Label>\n" +
			"                              <Button fx:id=\"viewThreadButton\" layoutX=\"294.0\" layoutY=\"258.0\" mnemonicParsing=\"false\" onAction=\"#viewThread\" prefHeight=\"25.0\" prefWidth=\"164.0\" text=\"View Thread\" />\n" +
			"                              <Label layoutX=\"294.0\" layoutY=\"82.0\" prefHeight=\"26.0\" prefWidth=\"103.0\" text=\"Profile\">\n" +
			"                                 <font>\n" +
			"                                    <Font name=\"System Bold\" size=\"15.0\" />\n" +
			"                                 </font>\n" +
			"                              </Label>\n" +
			"                              <TextField fx:id=\"lastProfileField\" layoutX=\"295.0\" layoutY=\"115.0\" prefHeight=\"25.0\" prefWidth=\"161.0\" text=\"lastRun\" />\n" +
			"                              <Button fx:id=\"saveProfileField\" layoutX=\"295.0\" layoutY=\"148.0\" mnemonicParsing=\"false\" onAction=\"#saveProfile\" prefHeight=\"25.0\" prefWidth=\"78.0\" text=\"Save\" />\n" +
			"                              <Button fx:id=\"loadProfileFIeld\" layoutX=\"376.0\" layoutY=\"148.0\" mnemonicParsing=\"false\" onAction=\"#loadProfile\" prefHeight=\"25.0\" prefWidth=\"78.0\" text=\"Load\" />\n" +
			"                              <Separator layoutX=\"294.0\" layoutY=\"197.0\" prefHeight=\"0.0\" prefWidth=\"163.0\" />\n" +
			"                              <Button fx:id=\"viewPatchButton\" layoutX=\"294.0\" layoutY=\"223.0\" mnemonicParsing=\"false\" onAction=\"#viewPatchNotes\" prefHeight=\"25.0\" prefWidth=\"164.0\" text=\"Patch Notes\" />\n" +
			"                           </children>\n" +
			"                        </Pane>\n" +
			"                     </content>\n" +
			"                </Tab>\n" +
			"                <Tab text=\"Combat\">\n" +
			"                     <content>\n" +
			"                        <Pane prefHeight=\"200.0\" prefWidth=\"200.0\">\n" +
			"                           <children>\n" +
			"                              <TitledPane animated=\"false\" layoutX=\"14.0\" layoutY=\"14.0\" prefHeight=\"328.0\" prefWidth=\"211.0\" text=\"Monsters\">\n" +
			"                                 <content>\n" +
			"                                    <Pane prefHeight=\"200.0\" prefWidth=\"200.0\">\n" +
			"                                       <children>\n" +
			"                                          <ComboBox id=\"monsterCombo\" fx:id=\"monsterBox\" layoutX=\"14.0\" layoutY=\"44.0\" onAction=\"#updateMonster\" prefHeight=\"25.0\" prefWidth=\"180.0\" />\n" +
			"                                          <Label layoutX=\"14.0\" layoutY=\"14.0\" prefHeight=\"23.0\" prefWidth=\"101.0\" text=\"Monster\">\n" +
			"                                             <font>\n" +
			"                                                <Font name=\"System Bold\" size=\"12.0\" />\n" +
			"                                             </font>\n" +
			"                                          </Label>\n" +
			"                                          <Label layoutX=\"14.0\" layoutY=\"88.0\" prefHeight=\"23.0\" prefWidth=\"101.0\" text=\"Location\">\n" +
			"                                             <font>\n" +
			"                                                <Font name=\"System Bold\" size=\"12.0\" />\n" +
			"                                             </font>\n" +
			"                                          </Label>\n" +
			"                                          <ComboBox id=\"killLocationCombo\" fx:id=\"locationBox\" layoutX=\"15.0\" layoutY=\"119.0\" onAction=\"#updateLocation\" prefHeight=\"25.0\" prefWidth=\"180.0\" />\n" +
			"                                       </children>\n" +
			"                                    </Pane>\n" +
			"                                 </content>\n" +
			"                              </TitledPane>\n" +
			"                              <TitledPane layoutX=\"242.0\" layoutY=\"14.0\" prefHeight=\"328.0\" prefWidth=\"211.0\" text=\"Equipment\">\n" +
			"                                 <content>\n" +
			"                                    <Pane prefHeight=\"200.0\" prefWidth=\"200.0\">\n" +
			"                                       <children>\n" +
			"                                          <Button fx:id=\"currEquipButton\" layoutX=\"14.0\" layoutY=\"268.0\" mnemonicParsing=\"false\" onAction=\"#uiLoadCurrentEquipment\" prefHeight=\"25.0\" prefWidth=\"184.0\" text=\"Get Current Equipment\" />\n" +
			"                                          <Label layoutX=\"14.0\" layoutY=\"14.0\" prefHeight=\"17.0\" prefWidth=\"58.0\" text=\"Weapon\" />\n" +
			"                                          <TextField editable=\"false\" fx:id=\"weaponField\" layoutX=\"67.0\" layoutY=\"10.0\" prefHeight=\"25.0\" prefWidth=\"131.0\" />\n" +
			"                                          <Label layoutX=\"14.0\" layoutY=\"40.0\" prefHeight=\"17.0\" prefWidth=\"58.0\" text=\"Shield\" />\n" +
			"                                          <TextField editable=\"false\" fx:id=\"shieldField\" layoutX=\"67.0\" layoutY=\"35.0\" prefHeight=\"25.0\" prefWidth=\"131.0\" />\n" +
			"                                          <Label layoutX=\"13.0\" layoutY=\"65.0\" prefHeight=\"17.0\" prefWidth=\"58.0\" text=\"Chest\" />\n" +
			"                                          <Label layoutX=\"13.0\" layoutY=\"90.0\" prefHeight=\"17.0\" prefWidth=\"58.0\" text=\"Legs\" />\n" +
			"                                          <Label layoutX=\"13.0\" layoutY=\"115.0\" prefHeight=\"17.0\" prefWidth=\"58.0\" text=\"Helmet\" />\n" +
			"                                          <TextField editable=\"false\" fx:id=\"chestField\" layoutX=\"67.0\" layoutY=\"60.0\" prefHeight=\"25.0\" prefWidth=\"131.0\" />\n" +
			"                                          <TextField editable=\"false\" fx:id=\"legsField\" layoutX=\"67.0\" layoutY=\"85.0\" prefHeight=\"25.0\" prefWidth=\"131.0\" />\n" +
			"                                          <TextField editable=\"false\" fx:id=\"helmetField\" layoutX=\"67.0\" layoutY=\"110.0\" prefHeight=\"25.0\" prefWidth=\"131.0\" />\n" +
			"                                          <TextField editable=\"false\" fx:id=\"capeField\" layoutX=\"67.0\" layoutY=\"135.0\" prefHeight=\"25.0\" prefWidth=\"131.0\" />\n" +
			"                                          <TextField editable=\"false\" fx:id=\"gloveField\" layoutX=\"67.0\" layoutY=\"160.0\" prefHeight=\"25.0\" prefWidth=\"131.0\" />\n" +
			"                                          <TextField editable=\"false\" fx:id=\"bootsField\" layoutX=\"67.0\" layoutY=\"185.0\" prefHeight=\"25.0\" prefWidth=\"131.0\" />\n" +
			"                                          <Label layoutX=\"14.0\" layoutY=\"139.0\" prefHeight=\"17.0\" prefWidth=\"58.0\" text=\"Cape\" />\n" +
			"                                          <Label layoutX=\"13.0\" layoutY=\"164.0\" prefHeight=\"17.0\" prefWidth=\"58.0\" text=\"Gloves\" />\n" +
			"                                          <Label layoutX=\"14.0\" layoutY=\"189.0\" prefHeight=\"17.0\" prefWidth=\"58.0\" text=\"Boots\" />\n" +
			"                                          <TextField editable=\"false\" fx:id=\"amuletField\" layoutX=\"67.0\" layoutY=\"210.0\" prefHeight=\"25.0\" prefWidth=\"131.0\" />\n" +
			"                                          <TextField editable=\"false\" fx:id=\"ringField\" layoutX=\"67.0\" layoutY=\"235.0\" prefHeight=\"25.0\" prefWidth=\"131.0\" />\n" +
			"                                          <Label layoutX=\"13.0\" layoutY=\"214.0\" prefHeight=\"17.0\" prefWidth=\"58.0\" text=\"Amulet\" />\n" +
			"                                          <Label layoutX=\"14.0\" layoutY=\"239.0\" prefHeight=\"17.0\" prefWidth=\"58.0\" text=\"Ring\" />\n" +
			"                                       </children>\n" +
			"                                    </Pane>\n" +
			"                                 </content>\n" +
			"                              </TitledPane>\n" +
			"                           </children>\n" +
			"                        </Pane>\n" +
			"                     </content>\n" +
			"                </Tab>\n" +
			"                  <Tab text=\"Restocking\">\n" +
			"                     <content>\n" +
			"                        <Pane prefHeight=\"200.0\" prefWidth=\"200.0\">\n" +
			"                           <children>\n" +
			"                              <Pane layoutX=\"14.0\" layoutY=\"14.0\" prefHeight=\"325.0\" prefWidth=\"440.0\" style=\"-fx-border-color: black;\">\n" +
			"                                 <children>\n" +
			"                                    <CheckBox fx:id=\"enableRestockCheck\" layoutX=\"14.0\" layoutY=\"152.0\" mnemonicParsing=\"false\" onAction=\"#enableRestock\" prefHeight=\"21.0\" prefWidth=\"134.0\" selected=\"true\" text=\"Enable Restocking\" />\n" +
			"                                    <TextArea editable=\"false\" layoutX=\"8.0\" layoutY=\"14.0\" prefHeight=\"123.0\" prefWidth=\"207.0\" text=\"Restocks items at Grand Exchange.&#10;&#10;Items: Iron ore, Iron dagger, &#10;Raw herring, Raw trout, Trout, Pike, &#10;Leather body, Leather chaps.\" />\n" +
			"                                    <Label layoutX=\"14.0\" layoutY=\"187.0\" prefHeight=\"22.0\" prefWidth=\"163.0\" text=\"Click Price + Offer Times\" />\n" +
			"                                    <TextField fx:id=\"pricePlusRestock\" layoutX=\"14.0\" layoutY=\"212.0\" text=\"3\" />\n" +
			"                                    <Label layoutX=\"14.0\" layoutY=\"251.0\" prefHeight=\"22.0\" prefWidth=\"146.0\" text=\"Amount of Items to Buy\" />\n" +
			"                                    <TextField fx:id=\"amountRestock\" layoutX=\"14.0\" layoutY=\"278.0\" text=\"25\" />\n" +
			"                                    <TextArea editable=\"false\" layoutX=\"222.0\" layoutY=\"14.0\" prefHeight=\"123.0\" prefWidth=\"212.0\" text=\"Buy emote items at Grand Exchange.&#10;&#10;Items: Gold ring, Gold necklace,&#10;Chef's hat, Red cape,&#10;Bronze axe, Leather boots\" />\n" +
			"                                    <CheckBox fx:id=\"enableEmoteBuyCheck\" layoutX=\"222.0\" layoutY=\"152.0\" mnemonicParsing=\"false\" onAction=\"#enableEmoteBuy\" prefHeight=\"21.0\" prefWidth=\"173.0\" selected=\"true\" text=\"Enable Emote Item Buying\" />\n" +
			"                                    <Label layoutX=\"220.0\" layoutY=\"187.0\" prefHeight=\"22.0\" prefWidth=\"163.0\" text=\"Click Price + Offer Times\" />\n" +
			"                                    <TextField fx:id=\"pricePlusEmote\" layoutX=\"220.0\" layoutY=\"212.0\" text=\"3\" />\n" +
			"                                 </children>\n" +
			"                              </Pane>\n" +
			"                           </children>\n" +
			"                        </Pane>\n" +
			"                     </content>\n" +
			"                  </Tab>\n" +
			"              </tabs>\n" +
			"            </TabPane>\n" +
			"         </children>\n" +
			"    </AnchorPane>\n" +
			"  </children>\n" +
			"</VBox>\n";
}