package io.aleosiss.sts.character.marisa.abstracts;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import io.aleosiss.sts.character.marisa.model.ScreenPosition;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class MarisaCard extends CustomCard {
	// These sentinel values are defined by the base game, we're just giving them more readable names.
	// Copied from https://github.com/dbjorge/jorbs-spire-mod/blob/master/src/main/java/stsjorbsmod/cards/CustomJorbsModCard.java
	public static final int COST_X = -1;
	public static final int COST_UNPLAYABLE = -2;

	protected ArrayList<AbstractCard> additionalCardsToPreview = new ArrayList<>();
	private static final int WIDTH_SPACING = 10;
	private static final int HEIGHT_SPACING = 7;

	public MarisaCard(String id,
	                  String name,
	                  String imgUrl,
	                  int cost,
	                  String rawDescription,
	                  CardType type,
	                  CardColor color,
	                  CardRarity rarity,
	                  CardTarget target) {
		super(id, name, imgUrl, cost, rawDescription, type, color, rarity, target);
	}


	@Override
	public void renderCardTip(SpriteBatch sb) {
		super.renderCardTip(sb);
		boolean renderTip = (boolean) ReflectionHacks.getPrivate(this, AbstractCard.class, "renderTip");

		int count = 0;
		if (!Settings.hideCards && renderTip) {
			if (AbstractDungeon.player == null || !AbstractDungeon.player.isDraggingCard) {
				if(!additionalCardsToPreview.isEmpty()) {
					this.renderCardPreview(sb);
				};
			}
		}
	}

	@Override
	public void renderCardPreview(SpriteBatch sb) {
		if (AbstractDungeon.player == null || !AbstractDungeon.player.isDraggingCard) {
			int index = 0;
			float dx = (AbstractCard.IMG_WIDTH * 0.9f - 5f) * drawScale + WIDTH_SPACING;
			float dy = (AbstractCard.IMG_HEIGHT * 0.4f - 5f) * drawScale + HEIGHT_SPACING;

			float curX = current_x;
			float curY = current_y;

			for (AbstractCard c : additionalCardsToPreview) {
				if (current_x > Settings.WIDTH * 0.75f) {
					curX = current_x + dx;
				} else {
					curX = current_x - dx;
				}
				c.current_x = curX;

				if (index != 0) {
					curY += (2 * dy);
				} else {
					curY -= (dy / 4);
				}

				c.current_y = curY;
				c.drawScale = drawScale * 0.8f;
				c.render(sb);
				index++;
			}
		}
	}

	@Override
	public void renderCardPreviewInSingleView(SpriteBatch sb) {
		int index = 0;
		for (AbstractCard c : additionalCardsToPreview) {
			c.current_x = 485.0F * Settings.scale;
			c.current_y = (795.0F - 510.0F * index) * Settings.scale;
			c.drawScale = 0.8f;
			c.render(sb);
			index++;
		}
	}

	private ArrayList<ScreenPosition> generateScreenPositions() {
		float yOffset1 = this.hb.height * 1.15f;
		float yOffset2 = this.hb.height * 0.25f;
		float yOffset3 = this.hb.height * -0.65f;

		//changes the preview to render below the Arcana in the shop so it doesn't clip out of the screen
		if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.SHOP) {
			yOffset1 = -yOffset1;
			yOffset2 = -yOffset2;
			yOffset3 = -yOffset3;
		}

		float yPosition1 = this.current_y + yOffset1;
		float yPosition2 = this.current_y + yOffset2;
		float yPosition3 = this.current_y + yOffset3;

		float xOffset1 = -this.hb.width * 0.75f;
		float xOffset2 = -this.hb.width * 0.25f;
		float xOffset3 = this.hb.width * 0.25f;

		//inverts the x position if the card is a certain amount to the right to prevent clipping issues
		if (this.current_x > Settings.WIDTH * 0.75F) {
			xOffset1 = -xOffset1;
			xOffset2 = -xOffset2;
			xOffset3 = -xOffset3;
		}

		float xPosition1 = this.current_x + xOffset1;
		float xPosition2 = this.current_x + xOffset2;
		float xPosition3 = this.current_x + xOffset3;

		ScreenPosition position1 = ScreenPosition.of(xPosition1, yPosition1);
		ScreenPosition position2 = ScreenPosition.of(xPosition1, yPosition2);
		ScreenPosition position3 = ScreenPosition.of(xPosition1, yPosition3);
		ScreenPosition position4 = ScreenPosition.of(xPosition2, yPosition1);
		ScreenPosition position5 = ScreenPosition.of(xPosition2, yPosition2);
		ScreenPosition position6 = ScreenPosition.of(xPosition2, yPosition3);
		ScreenPosition position7 = ScreenPosition.of(xPosition3, yPosition1);
		ScreenPosition position8 = ScreenPosition.of(xPosition3, yPosition2);
		ScreenPosition position9 = ScreenPosition.of(xPosition3, yPosition3);

		return new ArrayList<>(Arrays.asList(position1, position2, position3, position4, position5, position6, position7, position8, position9));
	}
}
