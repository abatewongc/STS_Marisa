package sts.touhouspire.mod.character.marisa.utils;

import basemod.BaseMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import sts.touhouspire.mod.character.marisa.MarisaModHandler;
import sts.touhouspire.mod.character.marisa.abstracts.MarisaCard;
import sts.touhouspire.mod.character.marisa.cards.derivations.GuidingStar;
import sts.touhouspire.mod.character.marisa.patches.AbstractCardEnum;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;

public class MarisaHelpers {
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

	public static AbstractMonster getRandomMonster() {
		return AbstractDungeon.getMonsters().getRandomMonster(null, true, cardRandomRng);
	}

	public static void addRelic(AbstractRelic relic) {
		BaseMod.addRelicToCustomPool(relic, AbstractCardEnum.MARISA_COLOR);
	}

	// this adds "ModName:" before the ID of any card/relic/power etc.
	// in order to avoid conflicts if any other mod uses the same ID.
	public static String makeID(String idText) {
		return MarisaModHandler.MOD_ID + ":" + idText;
	}

	public static String makeID(Class idClass) {
		return makeID(idClass.getSimpleName());
	}

	public static boolean cardIsBeingManipulated(MarisaCard marisaCard) {
		//Removes the preview when the player is manipulating the card or if the card is locked
		return (marisaCard.isLocked || (AbstractDungeon.player != null && (AbstractDungeon.player.isDraggingCard || AbstractDungeon.player.inSingleTargetMode)));
	}
}
