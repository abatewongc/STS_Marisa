package sts.touhouspire.mod.character.marisa.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import sts.touhouspire.mod.character.marisa.data.Identifiers;
import sts.touhouspire.mod.character.marisa.utils.MarisaHelpers;

public class MagicBroom extends CustomRelic {

	public static final String ID = Identifiers.Relics.MAGIC_BROOM;
	private static final String IMG = "marisa/img/relics/Broom_s.png";
	private static final String IMG_OTL = "marisa/img/relics/outline/Broom_s.png";

	private static final int NUMBER_OF_ZERO_COST_CARDS_REQUIRED_TO_BE_PLAYED = 3;

	public MagicBroom() {
		super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, LandingSound.FLAT);
	}

	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	public AbstractRelic makeCopy() {
		return new MagicBroom();
	}

	public void atBattleStart() {
		this.counter = 0;
	}

	public void onUseCard(AbstractCard card, UseCardAction action) {
		if (MarisaHelpers.wasZeroCost(card)) {
			this.counter += 1;
			if (this.counter >= NUMBER_OF_ZERO_COST_CARDS_REQUIRED_TO_BE_PLAYED) {
				this.counter = 0;
				activate();
			}
		}
	}

	private void activate() {
		flash();
		AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
	}

	public void onVictory() {
		this.counter = -1;
	}
}