package br.ufrrj.game.game.ui.screen;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import br.ufrrj.game.CommonGame;
import br.ufrrj.game.CommonConst;
import br.ufrrj.game.game.ui.screen.screens.GameScreen;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Screen implements ScreenComponent {

    private GameScreen gameScreen;

    private int width = CommonConst.GAME_WIDTH;
    private int height = CommonConst.GAME_HEIGHT;

    private List<ScreenComponent> components = new ArrayList<>();

    public Screen(GameScreen gameScreen) {
        this(gameScreen, CommonConst.GAME_WIDTH, CommonConst.GAME_HEIGHT, new ArrayList<>());
    }

    public Screen(GameScreen gameScreen, ScreenComponent... components) {
        this(gameScreen, CommonConst.GAME_WIDTH, CommonConst.GAME_HEIGHT, List.of(components));
    }

    public void onComponentMount() {
        components.stream().filter(component -> CommonGame.getInstance().getEventManager().isEventListener(component))
                .forEach(component -> {
                    CommonGame.getInstance().getEventManager().registerEvent(component);
                    component.onComponentMount();
                });
    }

    public void onComponentUnmount() {
        components.stream().filter(component -> CommonGame.getInstance().getEventManager().isEventListener(component))
                .forEach(component -> {
                    CommonGame.getInstance().getEventManager().unregisterEvent(component);
                    component.onComponentUnmount();
                });
    }

    public boolean isCurrentScreen() {
        if (gameScreen == null) return false;
        return gameScreen.equals(CommonGame.getInstance().getCurrentScreen());
    }

    @Override
    public boolean isInside(MouseEvent event) {
        return isCurrentScreen();
    }

    public void addComponent(ScreenComponent component) {
        components.add(component);
    }

    @Override
    public void render(Graphics graphics) {
        components.forEach(c -> c.render(graphics));
    }
}
