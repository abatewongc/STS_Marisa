package io.aleosiss.sts.character.marisa.vfx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;

import static io.aleosiss.sts.character.marisa.vfx.FireProjectileEffect.*;

// Have some enterprise Java
public class ProjectileFactory {
	public static ProjectileData CollectingQuirkProjectile(Texture img, int relicCount, boolean flipped, float scale) {
		ProjectileData data = new ProjectileData();
		data.texture = img;

		data.width = 128;
		data.height = 128;

		data.shouldAccelerate = true;
		data.accelerationXFactor = 5.0F;

		data.shouldImpactWall = false;

		data.x = MathUtils.random(-300, 0F);
		data.vX = MathUtils.random(600.0F, 900.0F) - (float)relicCount * 5.0F;
		data.accelerationX = 300.0F;

		if (flipped) {
			data.flip();
		}

		float yRange = (float) Settings.HEIGHT / 3;
		data.y = ((float)Settings.HEIGHT / 2) + MathUtils.random(-yRange, yRange);
		data.vY = 0;

		data.duration = 2.0F;
		data.scale = MathUtils.random(1.25F, 1.75F) + (float)relicCount * 0.04F;
		data.scale();

		data.color = new Color(0.9F, 0.9F, 1.0F, MathUtils.random(0.9F, 1.0F));
		data.rotation = MathUtils.random(0, 360 -1);

		data.fastMode();
		return data;
	}

	public static ProjectileData MeteoricShowerProjectile(int numHits, boolean flipped, float scale) {
		ProjectileData data = new ProjectileData();
		data.texture = MeteoricShowerEffect.METEORIC_SHOWER_PROJECTILE;

		data.shouldImpactFloor = true;

		data.width = 64;
		data.height = 64;

		data.x = MathUtils.random(500.0F, 1500.0F);
		data.vX = MathUtils.random(600.0F, 900.0F) - (float)numHits * 5.0F;

		if (flipped) {
			data.flip();
		}

		data.y = (float)Settings.HEIGHT + MathUtils.random(100.0F, 300.0F) - 48.0F;
		data.vY = MathUtils.random(2500.0F, 4000.0F) - (float)numHits * 10.0F;

		data.duration = 2.0F;
		data.scale = MathUtils.random(1.25F, 2.0F) + (float)numHits * 0.04F;
		data.scale();

		data.color = new Color(0.9F, 0.9F, 1.0F, MathUtils.random(0.9F, 1.0F));
		data.rotation = MathUtils.random(0, 360 -1);

		data.fastMode();
		return data;
	}
}
