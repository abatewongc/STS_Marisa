package io.aleosiss.sts.character.marisa.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.SpotlightEffect;

public class MeteoricShowerEffect extends AbstractGameEffect {
	private static final String SOUND_EFFECT_KEY = "ORB_FROST_CHANNEL";
	public static final Texture METEORIC_SHOWER_PROJECTILE = ImageMaster.loadImage("marisa/img/vfx/star_128.png");
	private final boolean flipped;
	private final int numHits;

	private float timer = 0.1f;
	private float origDuration = Settings.FAST_MODE ? 2.0f : 0.5f;
	private float duration = origDuration;
	private int starCounter = 0;
	private final float monsterX;


	public MeteoricShowerEffect(int numHits, boolean shouldFlipVfx, float monsterX) {
		this.numHits = numHits;
		this.flipped = shouldFlipVfx;
		this.monsterX = monsterX;
	}

	@Override
	public void update() {
		if(duration == origDuration) {
			CardCrawlGame.sound.playA(SOUND_EFFECT_KEY, -0.25F - (float) numHits / 200.0F);
			CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.HIGH, ScreenShake.ShakeDur.MED, true);
			AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(Color.SKY));
		}

		duration -= Gdx.graphics.getDeltaTime();
		timer -= Gdx.graphics.getDeltaTime();

		if (starCounter < Math.min(numHits, 50) && timer < 0.0F) {
			timer += 0.03F;
			AbstractDungeon.effectsQueue.add(FireProjectileEffect.MeteoricShowerProjectile(numHits, flipped, monsterX));
			starCounter += 1;
		}

		if (duration < 0.0F) {
			isDone = true;
		}
	}

	@Override
	public void render(SpriteBatch spriteBatch) {

	}

	@Override
	public void dispose() {}
}
