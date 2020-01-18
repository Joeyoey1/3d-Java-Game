package com.barbarian.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.barbarian.game.Game;
import com.barbarian.game.components.PositionComponent;
import com.barbarian.game.components.VisualComponent;
import com.barbarian.game.network.ChatNetwork;

import java.util.List;

public class RenderSystem2 extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    Sprite text;
    Viewport viewport;
    BitmapFont font = new BitmapFont();

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VisualComponent> vm = ComponentMapper.getFor(VisualComponent.class);

    public RenderSystem2 () {
        batch = new SpriteBatch();
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, VisualComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {
        PositionComponent position_c;
        VisualComponent visual_c;

        camera.update();


        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        List<String> chatLog = ChatNetwork.chatLog;
        int end = chatLog.size();
        if(chatLog.size() > 4) end = 5;
        for(int i = 0; i < end; i++){
            String str = chatLog.get(chatLog.size() - i - 1);
            font.setColor(0,0,0, (((5-i) * 0.15f) + 0.1f));
            font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            font.getData().setScale(1.25f);
            font.draw(batch, str, -Gdx.graphics.getWidth()/2f + 15, -Gdx.graphics.getHeight()/2f + (25 * (i +1)));
        }
        batch.end();
    }
}
