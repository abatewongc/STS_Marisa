package io.aleosiss.sts.character.marisa.relics;

import static io.aleosiss.sts.character.marisa.patches.CardTagEnum.SPARK;

import io.aleosiss.sts.character.marisa.MarisaModHandler;
import io.aleosiss.sts.character.marisa.data.Identifiers;
import io.aleosiss.sts.character.marisa.powers.Marisa.ChargeUpPower;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import io.aleosiss.sts.character.marisa.utils.ChargeUpUtils;

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
		if (card.hasTag(SPARK)) {
			chargeUpIncrease++;
		}
		AbstractDungeon.actionManager.addToTop(ChargeUpUtils.createChargeUpPowerAction(chargeUpIncrease));
		AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
	}

}