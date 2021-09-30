package io.aleosiss.sts.character.marisa.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.PetalEffect;
import com.megacrit.cardcrawl.vfx.combat.FallingIceEffect;

import java.util.ArrayList;

public class CollectingQuirkEffect extends AbstractGameEffect {
	private static final String SOUND_EFFECT_KEY = "ORB_FROST_CHANNEL";
	private final boolean flipped;
	private final ArrayList<AbstractRelic> relics;

	private float timer = 0.1f;
	private float origDuration = Settings.FAST_MODE ? 2.0f : 0.5f;
	private float duration = origDuration;
	private int relicCounter = 0;
	private final float monsterX;

	public CollectingQuirkEffect(boolean flipped, float monsterX) {
		this.relics = AbstractDungeon.player.relics;
		this.flipped = flipped;
		this.monsterX = monsterX;
	}

	@Override
	public void update() {
		if(duration == origDuration) {
			CardCrawlGame.sound.playA(SOUND_EFFECT_KEY, -0.25F - (float) relics.size() / 200.0F);
			CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.HIGH, ScreenShake.ShakeDur.MED, true);
			AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(Color.SKY));
		}

		duration -= Gdx.graphics.getDeltaTime();
		timer -= Gdx.graphics.getDeltaTime();

		if (relicCounter < relics.size() && timer < 0.0F) {
			timer += 0.03F;
			AbstractDungeon.effectsQueue.add(FireProjectileEffect.CollectingQuirkProjectile(relics.get(relicCounter).img, relics.size(), flipped, monsterX));
			relicCounter += 1;
		}

		if (duration < 0.0F) {
			isDone = true;
		}
	}

	@Override
	public void render(SpriteBatch spriteBatch) {

	}

	@Override
	public void dispose() {

	}
}
