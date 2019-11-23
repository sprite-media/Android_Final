package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class HitEffect extends ActorBeta {
    //Player
    Character player;

    //Player EffectAnims
    String[] wood_Effect;
    String[] brick_Effect;
    String[] steel_Effect;
    String[] diamond_Effect;

    Animation<TextureRegion> anim_wood_Effect;
    Animation<TextureRegion> anim_brick_Effect;
    Animation<TextureRegion> anim_steel_Effect;
    Animation<TextureRegion> anim_diamond_Effect;

    //Timer
    float timer = 0.0f;
    float lifeTime = 2.0f;

    HitEffect(Character _player, Stage s)
    {
        //Player
        player = _player;

        //Set effects
        SetEffects();
        Vector2 size = new Vector2(6, 6);
        size.x = Gdx.graphics.getWidth()/size.x;
        size.y = Gdx.graphics.getWidth()/size.y;
        setSize(size.x, size.y);

        setPosition(_player.getX() - this.getWidth()/2, _player.getY());
        s.addActor(this);
    }

    private void SetEffects()
    {
        wood_Effect = new String[] {"Effect/Spark_Wood_0.png", "Effect/Spark_Wood_1.png"};
        anim_wood_Effect = this.loadAnimationFromFiles(wood_Effect, 1/10f, true);
        brick_Effect = new String[] {"Effect/Spark_Brick_0.png", "Effect/Spark_Brick_1.png"};
        anim_brick_Effect = this.loadAnimationFromFiles(brick_Effect, 1/10f, true);
        steel_Effect = new String[] {"Effect/Spark_Steel_0.png" , "Effect/Spark_Steel_1.png"};
        anim_steel_Effect = this.loadAnimationFromFiles(steel_Effect, 1/10f, true);
        diamond_Effect = new String[] {"Effect/Spark_Dia_0.png", "Effect/Spark_Dia_1.png"};
        anim_diamond_Effect = this.loadAnimationFromFiles(diamond_Effect, 1/10f, true);
    }

    public void SpawnHitEffect()
    {
        if(player.life >= 30)
        {
            setAnimation(anim_diamond_Effect);
        }
        else if(player.life >= 20)
        {
            setAnimation(anim_steel_Effect);
        }
        else if(player.life >= 10)
        {
            setAnimation(anim_brick_Effect);
        }
        else
        {
            setAnimation(anim_wood_Effect);
        }
    }

    @Override
    public void act(float dt)
    {
        if(this == null)
            return;

        timer += dt;
        if(timer >= lifeTime)
        {
            this.remove();
        }
    }
}
