package com.uprightpath.ld.thirty.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.uprightpath.ld.thirty.Main;

/**
 * Created by Geo on 8/24/2014.
 */
public class KeyAssignWindow extends Window implements InputProcessor {
    private TextButton cancel;
    private TextButton left;
    private TextButton right;
    private TextButton up;
    private TextButton down;
    private TextButton jump;
    private TextButton interact;
    private TextButton swap;
    private TextButton items;
    private TextButton menu;
    private TextButton accept;
    private ButtonClickListener buttonClickListener;
    private InputProcessor inputProcessor;

    public KeyAssignWindow(final OptionMenuListener optionMenuListener, Skin skin) {
        super("Keys", skin);
        left = new TextButton(Input.Keys.toString(Main.preferences.getInteger("left-button", Input.Keys.LEFT)), skin);
        left.addListener(new ButtonClickListener(left, "left-button"));
        right = new TextButton(Input.Keys.toString(Main.preferences.getInteger("right-button", Input.Keys.RIGHT)), skin);
        right.addListener(new ButtonClickListener(right, "right-button"));
        up = new TextButton(Input.Keys.toString(Main.preferences.getInteger("up-button", Input.Keys.UP)), skin);
        up.addListener(new ButtonClickListener(up, "up-button"));
        down = new TextButton(Input.Keys.toString(Main.preferences.getInteger("down-button", Input.Keys.DOWN)), skin);
        down.addListener(new ButtonClickListener(down, "down-button"));
        jump = new TextButton(Input.Keys.toString(Main.preferences.getInteger("jump-button", Input.Keys.X)), skin);
        jump.addListener(new ButtonClickListener(jump, "jump-button"));
        interact = new TextButton(Input.Keys.toString(Main.preferences.getInteger("interact-button", Input.Keys.Z)), skin);
        interact.addListener(new ButtonClickListener(interact, "interact-button"));
        swap = new TextButton(Input.Keys.toString(Main.preferences.getInteger("swap-button", Input.Keys.A)), skin);
        swap.addListener(new ButtonClickListener(swap, "swap-button"));
        items = new TextButton(Input.Keys.toString(Main.preferences.getInteger("items-button", Input.Keys.S)), skin);
        items.addListener(new ButtonClickListener(items, "items-button"));
        menu = new TextButton(Input.Keys.toString(Main.preferences.getInteger("menu-button", Input.Keys.A)), skin);
        menu.addListener(new ButtonClickListener(menu, "menu-button"));
        accept = new TextButton(Input.Keys.toString(Main.preferences.getInteger("accept-button", Input.Keys.X)), skin);
        accept.addListener(new ButtonClickListener(accept, "accept-button"));
        cancel = new TextButton(Input.Keys.toString(Main.preferences.getInteger("cancel-button", Input.Keys.Z)), skin);
        cancel.addListener(new ButtonClickListener(accept, "cancel-button"));

        this.add("Platformer").colspan(2).padLeft(10);
        this.add("Dialog/Menus").colspan(2).row();
        this.add("Up");
        this.add(up);
        this.add("Items");
        this.add(items).row();
        this.add("Left");
        this.add(left);
        this.add("Menu");
        this.add(menu).row();
        this.add("Down");
        this.add(down);
        this.add("Accept");
        this.add(accept).row();
        this.add("Right");
        this.add(right);
        this.add("Cancel");
        this.add(cancel).row();
        this.add("Jump");
        this.add(jump).row();
        this.add("Interact");
        this.add(interact).row();
        this.add("Swap");
        this.add(swap).row();
        this.add().pad(10);
        TextButton btnClose = new TextButton("Close", skin);
        btnClose.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                optionMenuListener.setKeyAssignMenuVisible(false);
            }
        });
        this.add(btnClose).colspan(4);

    }

    @Override
    public boolean keyDown(int keycode) {
        buttonClickListener.update(keycode);
        Gdx.input.setInputProcessor(inputProcessor);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public class ButtonClickListener extends ChangeListener {
        private final String id;
        private final TextButton textButton;

        ButtonClickListener(TextButton textButton, String id) {
            this.textButton = textButton;
            this.id = id;
        }

        @Override
        public void changed(ChangeListener.ChangeEvent event, Actor actor) {
            KeyAssignWindow.this.clicked(this);
        }

        public void update(int keycode) {
            this.textButton.setText(Input.Keys.toString(keycode));
            Main.preferences.putInteger(id, keycode);
        }
    }

    private void clicked(ButtonClickListener buttonClickListener) {
        this.buttonClickListener = buttonClickListener;
        this.inputProcessor = Gdx.input.getInputProcessor();
        Gdx.input.setInputProcessor(this);
    }


}
