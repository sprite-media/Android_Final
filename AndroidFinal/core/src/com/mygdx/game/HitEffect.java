package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class HitEffect extends ActorBeta {
    //Player
    Character player;

    Stage stage;

    //Player EffectAnims
    String[] wood_Effect;
    String[] brick_Effect;
    String[] steel_Effect;
    String[] diamond_Effect;

    Animation<TextureRegion> anim_wood_Effect;
    Animation<TextureRegion> anim_brick_Effect;
    Animation<TextureRegion> anim_steel_Effect;
    Animation<TextureRegion> anim_diamond_Effect;

    //Size
    Vector2 size;

    //Timer
    float timer = 0.0f;
    float lifeTime = 0.5f;

    HitEffect(Character _player, Stage s)
    {
        super();

        //Player
        player = _player;

        //stage
        stage = s;

        //Set effects
        SetEffects();
        SpawnHitEffect();
        setSize(player.getWidth()*1.7f, player.getHeight()*1.7f);
        setPosition(_player.getX() - (getWidth() - player.getWidth() )/2, player.getY());

        s.addActor(this);
    }

    private void SetEffects()
    {
        wood_Effect = new String[] {"Effect/Spark_Wood_0.png", "Effect/Spark_Wood_1.png"};
        anim_wood_Effect = this.loadAnimationFromFiles(wood_Effect, 0.07f, true);
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

        b_animating = true;
    }

    @Override
    public void act(float dt)
    {

        if(this == null)
            return;

        super.act(dt);

        timer += dt;
        if(timer >= lifeTime)
        {
            this.remove();
        }
    }
}
