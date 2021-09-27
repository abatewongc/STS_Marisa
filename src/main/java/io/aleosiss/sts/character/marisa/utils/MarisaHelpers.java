package io.aleosiss.sts.character.marisa.utils;

import basemod.BaseMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import io.aleosiss.sts.character.marisa.MarisaModHandler;
import io.aleosiss.sts.character.marisa.abstracts.MarisaModCard;
import io.aleosiss.sts.character.marisa.cards.derivations.GuidingStar;
import io.aleosiss.sts.character.marisa.relics.MiniHakkero;

import static io.aleosiss.sts.character.marisa.patches.AbstractCardEnum.MARISA_COLOR;

public class MarisaHelpers {
	// These sentinel values are defined by the base game, we're just giving them more readable names.
	// Copied from https://github.com/dbjorge/jorbs-spire-mod/blob/master/src/main/java/stsjorbsmod/cards/CustomJorbsModCard.java
	public static final int COST_X = -1;
	public static final int COST_UNPLAYABLE = -2;

	public static boolean wasZeroCost(AbstractCard card) {
		return (card.costForTurn == 0)
				|| (card.costForTurn <= -2)
				|| ((card.costForTurn == -1) && (AbstractDungeon.player.energy.energy <= 0));
	}

	public static AbstractCard getRandomMarisaCard() {
		AbstractCard card;
		int rng = AbstractDungeon.miscRng.random(0, 100);
		if (rng == 15) {
			card = new GuidingStar();
		} else {
			card = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
		}
		return card;
	}

	public static void addRelic(AbstractRelic relic) {
		BaseMod.addRelicToCustomPool(relic, MARISA_COLOR);
	}

	// this adds "ModName:" before the ID of any card/relic/power etc.
	// in order to avoid conflicts if any other mod uses the same ID.
	public static String makeID(String idText) {
		return MarisaModHandler.MOD_ID + ":" + idText;
	}

	public static String makeID(Class idClass) {
		return makeID(idClass.getSimpleName());
	}

	public static boolean cardIsBeingManipulated(MarisaModCard marisaModCard) {
		//Removes the preview when the player is manipulating the card or if the card is locked
		return (marisaModCard.isLocked || (AbstractDungeon.player != null && (AbstractDungeon.player.isDraggingCard || AbstractDungeon.player.inSingleTargetMode)));
	}
}
