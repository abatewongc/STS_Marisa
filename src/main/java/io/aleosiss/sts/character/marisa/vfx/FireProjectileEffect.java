package io.aleosiss.sts.character.marisa.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.IceShatterEffect;

public class FireProjectileEffect extends AbstractGameEffect {

	public static final String PROJECTILE_IMPACT_SOUND_KEY = "ORB_FROST_EVOKE";
	private ProjectileData projectile;
	private float waitTimer;

	private float floorY = AbstractDungeon.floorY + MathUtils.random(-200.0F, 50.0F) * Settings.scale;
	private float monsterX;

	public static FireProjectileEffect CollectingQuirkProjectile(Texture img, int relicCount, boolean flipped, float monsterX) {
		FireProjectileEffect effect = new FireProjectileEffect();
		effect.projectile = ProjectileFactory.CollectingQuirkProjectile(img, relicCount, flipped, effect.scale);
		effect.monsterX = monsterX + MathUtils.random(-100.0F, 100.0F) * Settings.scale;
		effect.waitTimer = MathUtils.random(0.0F, 0.5F);

		effect.renderBehind = MathUtils.randomBoolean();

		return effect;
	}

	public static FireProjectileEffect MeteoricShowerProjectile(int numHits, boolean flipped, float monsterX) {
		FireProjectileEffect effect = new FireProjectileEffect();
		effect.projectile = ProjectileFactory.MeteoricShowerProjectile(numHits, flipped, effect.scale);
		effect.monsterX = (monsterX + MathUtils.random(-100.0F, 100.0F)) * Settings.scale;
		effect.waitTimer = MathUtils.random(0.0F, 0.5F);
		effect.renderBehind = MathUtils.randomBoolean();

		return effect;
	}

	@Override
	public void update() {
		this.waitTimer -= Gdx.graphics.getDeltaTime();
		if (!(this.waitTimer > 0.0F)) {
			projectile.x += projectile.vX * Gdx.graphics.getDeltaTime();
			projectile.y -= projectile.vY * Gdx.graphics.getDeltaTime();
			if(projectile.shouldImpactFloor) {
				checkFloorCollision();
			}

			if(projectile.shouldImpactWall) {
				checkWallCollision();
			}

			if(projectile.shouldAccelerate) {
				checkAccelerate();
			}

		}
	}

	private void checkWallCollision() {
		boolean hittingWall = projectile.x > monsterX;
		if(hittingWall) {
			impact();
			isDone = true;
		}
	}

	private void checkAccelerate() {
		if(projectile.accelerationXFactor > 1.0F && projectile.x > (projectile.accelerationX * Settings.scale)) {
			projectile.vX *= projectile.accelerationXFactor;
			projectile.shouldAccelerate = false;
		}
		if(projectile.accelerationYFactor > 1.0F && projectile.y < (projectile.accelerationY * Settings.scale)) {
			projectile.vY *= projectile.accelerationYFactor;
			projectile.shouldAccelerate = false;
		}
	}

	private void checkFloorCollision() {
		boolean hittingTheFloor = projectile.y < this.floorY;
		if (hittingTheFloor) {
			impact();
			isDone = true;
		}
	}

	private void impact() {
		float pitch = 0.8F;
		pitch += MathUtils.random(-0.2F, 0.2F);// 80
		CardCrawlGame.sound.playA(PROJECTILE_IMPACT_SOUND_KEY, pitch);

		for(int i = 0; i < 4; ++i) {
			AbstractDungeon.effectsQueue.add(new IceShatterEffect(projectile.x, projectile.y));
		}
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		if (this.waitTimer < 0.0F) {
			spriteBatch.setBlendFunction(770, 1);
			spriteBatch.setColor(projectile.color);
			//          draw(Texture texture, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY) {
			spriteBatch.draw(projectile.texture, projectile.x, projectile.y, 48.0F, 48.0F, projectile.width, projectile.height, projectile.scale, projectile.scale, projectile.rotation, 0, 0, projectile.texture.getWidth(), projectile.texture.getHeight(), false, false);
			spriteBatch.setBlendFunction(770, 771);
		}
	}

	@Override
	public void dispose() {}


	public static class ProjectileData {
		public Texture texture;
		public float x;
		public float y;
		public float vX;
		public float vY;
		public float width;
		public float height;
		public boolean shouldImpactFloor = false;
		public boolean shouldImpactWall = false;
		public boolean shouldAccelerate = false;
		public float accelerationX = -1.0F;
		public float accelerationY = Settings.HEIGHT * 2;
		public float accelerationXFactor = 1;
		public float accelerationYFactor = 1;
		public float scale;
		public float duration;
		public Color color;
		public float rotation;

		//rotate((float) relicCount, flipped, data);
		public void rotate(float relicCount, boolean flipped, ProjectileData data) {
			Vector2 rotation = new Vector2(data.vX, data.vY);
			if (flipped) {
				data.rotation = rotation.angle() + 225.0F - relicCount / 3.0F;
			} else {
				data.rotation = rotation.angle() - 45.0F + relicCount / 3.0F;
			}
		}

		public void fastMode() {
			boolean fastMode = Settings.FAST_MODE;
			if(fastMode) {
				vX *= 2;
				vY *= 2;
			}
		}

		public void scale() {
			width *= Settings.scale;
			height *= Settings.scale;
			scale *= Settings.scale;
			x = x * Settings.scale;
			vX *= scale;
			vX *= Settings.scale;
			vY *= Settings.scale;
		}

		public void flip() {
			x = Settings.WIDTH - x;
			vX *= -1;
			accelerationX = Settings.WIDTH - accelerationX;
		}
	}
}
