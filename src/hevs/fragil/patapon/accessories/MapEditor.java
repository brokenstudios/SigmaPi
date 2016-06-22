package hevs.fragil.patapon.accessories;

import ch.hevs.gdx2d.desktop.PortableApplication;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.utils.Logger;
import hevs.fragil.patapon.mechanics.Param;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MapEditor extends PortableApplication {
	Skin skin;
	Stage stage;
	TextButton newGameButton, quitGameButton;
	TextField textArea;
	float stateTime;

	public static void main(String[] args) {
		new MapEditor();
	}

	@Override
	public void onInit() {
		int buttonWidth = 100;
		int buttonHeight = 30;

		setTitle("SIGMAPI - 2016 - Broken Studios - Loïc Fracheboud, Loïc Gillioz, S2d - MAP EDITOR");

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);// Make the stage consume events

		// Load the default skin (which can be configured in the JSON file)
		skin = new Skin(Gdx.files.internal("data/ui/uiskin.json"));

		newGameButton = new TextButton("Place", skin); // Use the initialized skin
		newGameButton.setWidth(buttonWidth);
		newGameButton.setHeight(buttonHeight);

		quitGameButton = new TextButton("Useless button", skin); // Use the initialized skin
		quitGameButton.setWidth(buttonWidth);
		quitGameButton.setHeight(buttonHeight);

		newGameButton.setPosition(Gdx.graphics.getWidth() / 2 - buttonWidth / 2, (int) (Gdx.graphics.getHeight() * .6));
		quitGameButton.setPosition(Gdx.graphics.getWidth() / 2 - buttonWidth / 2, (int) (Gdx.graphics.getHeight() * .7));

		textArea = new TextField("Enter some text...", skin);
		textArea.setWidth(buttonWidth);
		textArea.setPosition(Gdx.graphics.getWidth() / 2 - buttonWidth / 2, (int) (Gdx.graphics.getHeight() * .4));

		textArea.setTextFieldListener(new TextFieldListener() {
			public void keyTyped(TextField textField, char key) {
				textArea.setSelection(0, 0);

				// When you press 'enter', do something
				if (key == 13)
					Logger.log("You have typed " + textArea.getText());
			}
		});

		/**
		 * Adds the buttons to the stage
		 */
		stage.addActor(newGameButton);
		stage.addActor(quitGameButton);
		stage.addActor(textArea);

		/**
		 * Register listener
		 */
		newGameButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);

				if (newGameButton.isChecked())
					Logger.log("Button is checked");
				else
					Logger.log("Button is not checked");
			}
		});
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		g.clear(Param.BACKGROUND);

		// This is required for having the GUI work properly
		stage.act();
		stage.draw();

		stateTime += Gdx.graphics.getDeltaTime();
		g.drawStringCentered(870, "MAP EDITOR", Param.medium);
	}

	@Override
	public void onDispose() {
		super.onDispose();
		stage.dispose();
		skin.dispose();
	}
}
