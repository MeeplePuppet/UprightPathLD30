package com.uprightpath.ld.thirty.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.uprightpath.ld.thirty.Main;
import com.uprightpath.ld.thirty.logic.WorldGroup;
import com.uprightpath.ld.thirty.story.Answer;
import com.uprightpath.ld.thirty.story.Dialog;
import com.uprightpath.ld.thirty.story.Story;
import com.uprightpath.ld.thirty.story.TapAnswer;

/**
 * Created by Geo on 8/22/2014.
 */
public class WorldScreen extends GameScreen {
    private WorldGroup worldGroup;
    private float tick = 1f / 60f;
    private float accum = 0f;
    private Table mainTable;
    private Table dialogTable;
    private Label lblWorldName;
    private ImageButton btnSound;
    private ImageButton btnOption;
    private DialogWidget dialogWidget;

    public WorldScreen(Main main) {
        super(main);
        mainTable = new Table();
        mainTable.setFillParent(true);

        Table topTable = new Table(main.getSkin());
        topTable.setBackground(main.getSkin().getDrawable("default-round-large"));
        lblWorldName = new Label("World Test", main.getSkin());
        topTable.add(lblWorldName).fillX().expandX().align(Align.top);

        btnSound = new ImageButton(main.getSkin().getDrawable("sound-on"));
        topTable.add(btnSound);

        btnOption = new ImageButton(main.getSkin().getDrawable("options-button"));
        topTable.add(btnOption);

        mainTable.add(topTable).fillX().row();

        dialogTable = new Table();
        mainTable.add(dialogTable).pad(20).fill().expand().expand();
        stage.addActor(mainTable);
    }

    public void setWorldGroup(WorldGroup worldGroup) {
        this.worldGroup = worldGroup;
    }

    @Override
    protected void renderImplement(float delta) {
        accum += delta;
        if (worldGroup.isTellingStory()) {
            Story story = worldGroup.getStory();
            if (dialogWidget != null) {
                if (dialogWidget.dialog != story.getDialog()) {
                    dialogTable.removeActor(dialogWidget);
                    dialogWidget = new DialogWidget(story.getDialog());
                    dialogTable.add(dialogWidget).expandX().fillX();
                } else {
                    dialogWidget.update();
                }
            } else {
                dialogWidget = new DialogWidget(story.getDialog());
                dialogTable.add(dialogWidget).expandX().fillX();
            }
        } else {
            if (dialogWidget != null) {
                dialogTable.removeActor(dialogWidget);
                dialogWidget = null;
            }
            while (accum > tick) {
                accum -= tick;
                worldGroup.update();
            }
        }
        worldGroup.render(delta);
        this.mainTable.setDebug(true, true);
    }

    public class DialogWidget extends Table {
        private Dialog dialog;
        private Window dialogWindow;
        private Table answerTable;
        private Button btnContinue;
        private Array<Button> buttons = new Array<Button>();
        private int currentSelected = 0;

        public DialogWidget(Dialog dialog) {
            Label label;
            this.dialog = dialog;
            dialogWindow = new Window(dialog.getInteractable().getName(), main.getSkin());
            this.add(dialogWindow).expand(true, false).fillX().row();
            dialogWindow.add();
            label = new Label(dialog.getDialog(), main.getSkin());
            dialogWindow.add(label).expand(true, true);
            answerTable = new Table(main.getSkin());
            if (dialog.getAnswers().size == 1 && dialog.getAnswers().get(0) instanceof TapAnswer) {
                btnContinue = new ImageButton(main.getSkin().getDrawable("continue-dialog"));
                btnContinue.addListener(new AnswerClickListener(0));
                dialogWindow.add(btnContinue).align(Align.bottom);
            } else {
                Answer answer;
                Button button;
                dialogWindow.add(new Image(main.getSkin().getDrawable("answer-dialog")));
                for (int i = 0; i < dialog.getAnswers().size; i++) {
                    answer = dialog.getAnswers().get(i);
                    button = new TextButton(answer.getText(), main.getSkin());
                    button.addListener(new AnswerClickListener(i));
                    button.addListener(new AnswerMouseListener(i));
                    buttons.add(button);
                    answerTable.add(button).fill(true, false).row();
                }
                this.add(answerTable).align(Align.right);
            }
            updateSelected(0);
        }

        private void update() {
            for (int i = 0; i < buttons.size; i++) {
                buttons.get(i).setDisabled(!WorldScreen.this.worldGroup.getStory().canPerform(i));
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                updateSelected((buttons.size + currentSelected - 1) % buttons.size);
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                updateSelected((buttons.size + currentSelected + 1) % buttons.size);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
                if (buttons.size == 0) {
                    btnContinue.toggle();
                } else {
                    if (!buttons.get(currentSelected).isDisabled()) {
                        buttons.get(currentSelected).toggle();
                    }
                }
            }
        }

        public void updateSelected(int id) {
            if (buttons.size > 0) {
                buttons.get(currentSelected).setStyle(main.getSkin().get("default", TextButton.TextButtonStyle.class));
                currentSelected = id;
                if (!buttons.get(currentSelected).isDisabled()) {
                    buttons.get(currentSelected).setStyle(main.getSkin().get("selected", TextButton.TextButtonStyle.class));
                }
            }
        }

        private void clicked(int i) {
            WorldScreen.this.worldGroup.getStory().perform(i);
        }

        private class AnswerMouseListener extends ClickListener {
            private  int id;

            public AnswerMouseListener(int id) {
                this.id = id;
            }

            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                DialogWidget.this.updateSelected(id);
            }
        }

        private class AnswerClickListener extends ChangeListener {

            private  int id;

            public AnswerClickListener(int id) {
                this.id = id;
            }

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                DialogWidget.this.clicked(id);
            }
        }
    }

    public void show() {
        super.show();
        worldGroup.createDisplay();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        worldGroup.createDisplay();
    }

}
