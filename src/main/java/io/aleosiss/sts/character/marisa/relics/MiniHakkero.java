package io.aleosiss.sts.character.marisa.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import io.aleosiss.sts.character.marisa.MarisaModHandler;
import io.aleosiss.sts.character.marisa.data.Identifiers;
import io.aleosiss.sts.character.marisa.powers.Marisa.ChargeUpPower;
import basemod.abstracts.CustomRelic;
import io.aleosiss.sts.character.marisa.utils.ChargeUpUtils;

public class MiniHakkero extends CustomRelic {

	public static final String ID = Identifiers.Relics.MINI_HAKKERO;
	private static final String IMG = "marisa/img/relics/Hakkero_s.png";
	private static final String IMG_OTL = "marisa/img/relics/outline/Hakkero_s.png";

	public MiniHakkero() {
		super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.STARTER, LandingSound.MAGICAL);
	}

	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	public AbstractRelic makeCopy() {
		return new MiniHakkero();
	}

	public void onUseCard(AbstractCard card, UseCardAction action) {
		flash();
		ChargeUpUtils.addChargeUp(1, true);
		AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
	}
}