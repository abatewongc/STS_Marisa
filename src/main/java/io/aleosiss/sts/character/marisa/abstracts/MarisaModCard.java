package io.aleosiss.sts.character.marisa.abstracts;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import io.aleosiss.sts.character.marisa.MarisaModHandler;
import io.aleosiss.sts.character.marisa.model.ScreenPosition;
import io.aleosiss.sts.character.marisa.utils.MarisaHelpers;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class MarisaModCard extends CustomCard {
	protected ArrayList<AbstractCard> additionalCardsToPreview = new ArrayList<>();

	public MarisaModCard(String id, String name, String imgUrl, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
		super(id, name, imgUrl, cost, rawDescription, type, color, rarity, target);
		assert additionalCardsToPreview.size() < 5;
	}

	@Override
	public void renderCardPreviewInSingleView(SpriteBatch sb) {
		super.renderCardPreviewInSingleView(sb);
		renderGeneratedCardPreview(sb);
	}


	private void renderGeneratedCardPreview(SpriteBatch sb) {
		//Removes the preview when the player is manipulating the card or if the card is locked or we have nothing to preview
		if (MarisaHelpers.cardIsBeingManipulated(this) || additionalCardsToPreview.isEmpty()) {
			return;
		}

		float drawScale = 0.5f;

		ArrayList<ScreenPosition> positions = generateScreenPositions();
		if(positions.size() < additionalCardsToPreview.size()) {
			MarisaModHandler.logger.warn("Cannot preview all cards for card: " + this.cardID);
		}

		for(int i = 0; i < positions.size() - 1; i++) {
			if(additionalCardsToPreview.size() <= i) {
				break;
			}

			ScreenPosition position = positions.get(i);
			AbstractCard card = additionalCardsToPreview.get(i);
			AbstractCard render = card.makeStatEquivalentCopy();
			if(render != null) {
				render.drawScale = drawScale;
				render.current_x = position.getX();
				render.current_y = position.getY();
				render.render(sb);
			}
		}
	}

	private ArrayList<ScreenPosition> generateScreenPositions() {
		float yPosition1 = this.current_y + this.hb.height * 0.75f;
		float yPosition2 = this.current_y + this.hb.height * 0.25f;
		float yPosition3 = this.current_y - this.hb.height * 0.25f;

		//changes the preview to render below the Arcana in the shop so it doesn't clip out of the screen
		if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.SHOP) {
			yPosition1 = this.current_y - this.hb.height * 0.75f;
			yPosition2 = this.current_y - this.hb.height * 0.25f;
			yPosition3 = this.current_y + this.hb.height * 0.25f;
		}

		float xPosition1;
		float xPosition2;
		float xPosition3;
		float xOffset1 = -this.hb.width * 0.75f;
		float xOffset2 = -this.hb.width * 0.25f;
		float xOffset3 = this.hb.width * 0.25f;

		//inverts the x position if the card is a certain amount to the right to prevent clipping issues
		if (this.current_x > Settings.WIDTH * 0.75F) {
			xOffset1 = -xOffset1;
			xOffset2 = -xOffset2;
			xOffset3 = -xOffset3;
		}

		xPosition1 = this.current_x + xOffset1;
		xPosition2 = this.current_x + xOffset2;
		xPosition3 = this.current_x + xOffset3;

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
