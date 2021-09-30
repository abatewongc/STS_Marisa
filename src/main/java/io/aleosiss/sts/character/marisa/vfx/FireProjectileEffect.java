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

	private ProjectileData projectile;
	private float waitTimer;

	//floorY = AbstractDungeon.floorY + MathUtils.random(-200.0F, 50.0F) * Settings.scale;
	private float floorY;
	private float monsterX;

	public static FireProjectileEffect CollectingQuirkProjectile(Texture img, int relicCount, boolean flipped, float monsterX) {
		FireProjectileEffect effect = new FireProjectileEffect();
		effect.projectile = ProjectileData.CollectingQuirkProjectile(img, relicCount, flipped, effect.scale);
		effect.monsterX = monsterX * Settings.scale;
		effect.waitTimer = MathUtils.random(0.0F, 0.5F);

		effect.renderBehind = MathUtils.randomBoolean();
		effect.monsterX += MathUtils.random(-100.0F, 100.0F) * Settings.scale;

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
		boolean hittingWall = projectile.x > this.monsterX;
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
		CardCrawlGame.sound.playA("ORB_FROST_EVOKE", pitch);

		for(int i = 0; i < 4; ++i) {
			AbstractDungeon.effectsQueue.add(new IceShatterEffect(projectile.x, projectile.y));
		}
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		if (this.waitTimer < 0.0F) {
			spriteBatch.setBlendFunction(770, 1);
			spriteBatch.setColor(projectile.color);
			spriteBatch.draw(projectile.texture, projectile.x, projectile.y, 48.0F, 48.0F, projectile.width, projectile.height, projectile.scale, projectile.scale, projectile.rotation, 0, 0, 96, 96, false, false);
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


		public static ProjectileData CollectingQuirkProjectile(Texture img, int relicCount, boolean flipped, float scale) {
			ProjectileData data = new ProjectileData();
			data.texture = img;

			data.width = 60;
			data.height = 60;

			data.shouldAccelerate = true;
			data.accelerationXFactor = 5.0F;

			data.x = MathUtils.random(-300, 0F);
			data.vX = MathUtils.random(600.0F, 900.0F) - (float)relicCount * 5.0F;
			data.accelerationX = 300.0F;

			if (flipped) {
				flip(data);
			}

			float yRange = (float)Settings.HEIGHT / 3;
			data.y = ((float)Settings.HEIGHT / 2) + MathUtils.random(-yRange, yRange);
			data.vY = 0;

			data.duration = 2.0F;
			data.scale = MathUtils.random(1.0F, 1.5F) + (float)relicCount * 0.04F;
			scale(data);

			data.color = new Color(0.9F, 0.9F, 1.0F, MathUtils.random(0.9F, 1.0F));
			data.rotation = MathUtils.random(0, 360 -1);

			fastMode(data);
			return data;
		}

		//rotate((float) relicCount, flipped, data);
		private static void rotate(float relicCount, boolean flipped, ProjectileData data) {
			Vector2 rotation = new Vector2(data.vX, data.vY);
			if (flipped) {
				data.rotation = rotation.angle() + 225.0F - relicCount / 3.0F;
			} else {
				data.rotation = rotation.angle() - 45.0F + relicCount / 3.0F;
			}
		}

		private static void fastMode(ProjectileData data) {
			boolean fastMode = Settings.FAST_MODE;
			if(fastMode) {
				data.vX *= 2;
				data.vY *= 2;
			}
		}

		private static void scale(ProjectileData data) {
			data.scale *= Settings.scale;
			data.x = data.x * Settings.scale - 48.0F;
			data.vX *= data.scale;
			data.vX *= Settings.scale;
			data.vY *= Settings.scale;
		}

		private static void flip(ProjectileData data) {
			data.x = Settings.WIDTH - data.x;
			data.vX *= -1;
			data.accelerationX = Settings.WIDTH - data.accelerationX;
		}

	}
}
