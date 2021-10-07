package sts.touhouspire.mod.character.marisa.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import sts.touhouspire.mod.character.marisa.data.Identifiers;

public class HandmadeGrimoire extends CustomRelic {

	public static final String ID = Identifiers.Relics.HANDMADE_GRIMOIRE;
	private static final String IMG = "marisa/img/relics/Grimoire.png";
	private static final String IMG_OTL = "marisa/img/relics/outline/Grimoire.png";

	private static final int NUMBER_OF_CARDS_IN_DECK_PER_ACTIVATION = 15;

	public HandmadeGrimoire() {
		super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.UNCOMMON, LandingSound.FLAT);
	}

	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	public AbstractRelic makeCopy() {
		return new HandmadeGrimoire();
	}

	public void atBattleStart() {
		int numActivations = AbstractDungeon.player.masterDeck.size() / NUMBER_OF_CARDS_IN_DECK_PER_ACTIVATION;
		flash();
		if (numActivations > 0) {
			AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
			AbstractDungeon.player.gainEnergy(numActivations);
			AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, numActivations));
		}
	}
}