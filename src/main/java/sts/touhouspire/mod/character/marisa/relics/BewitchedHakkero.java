package sts.touhouspire.mod.character.marisa.relics;

import sts.touhouspire.mod.character.marisa.MarisaModHandler;
import sts.touhouspire.mod.character.marisa.data.Identifiers;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import sts.touhouspire.mod.character.marisa.utils.ChargeUpUtils;
import sts.touhouspire.mod.character.marisa.patches.CardTagEnum;

public class BewitchedHakkero extends CustomRelic {

	public static final String ID = Identifiers.Relics.BEWITCHED_HAKKERO;
	private static final String IMG = "marisa/img/relics/Hakkero_1_s.png";
	private static final String IMG_OTL = "marisa/img/relics/outline/Hakkero_1_s.png";

	public BewitchedHakkero() {
		super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.BOSS, LandingSound.MAGICAL);
	}

	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	public AbstractRelic makeCopy() {
		return new BewitchedHakkero();
	}

	public void obtain() {
		if (AbstractDungeon.player.hasRelic(Identifiers.Relics.MINI_HAKKERO)) {
			instantObtain(AbstractDungeon.player, 0, false);
		} else {
			super.obtain();
		}
	}

	public void onUseCard(AbstractCard card, UseCardAction action) {
		flash();
		MarisaModHandler.logger.info("BewitchedHakkero : Applying ChargeUpPower for using card : " + card.cardID);
		int chargeUpIncrease = 1;
		if (card.hasTag(CardTagEnum.SPARK)) {
			chargeUpIncrease++;
		}
		AbstractDungeon.actionManager.addToTop(ChargeUpUtils.createChargeUpPowerAction(chargeUpIncrease));
		AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
	}

}