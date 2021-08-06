package io.aleosiss.sts.character.marisa.relics;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.actions.unique.DiscoveryAction;

import io.aleosiss.sts.character.marisa.cards.derivations.Spark;
import basemod.abstracts.CustomRelic;
import io.aleosiss.sts.character.marisa.data.Identifiers;

public class ExperimentalFamiliar extends CustomRelic {

	public static final String ID = Identifiers.Relics.EXPERIMENTAL_FAMILIAR;
	private static final String IMG = "marisa/img/relics/ExpFamiliar.png";
	private static final String IMG_OTL = "marisa/img/relics/outline/ExpFamiliar.png";

	public ExperimentalFamiliar() {
		super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.BOSS, LandingSound.FLAT);
	}

	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	public AbstractRelic makeCopy() {
		return new ExperimentalFamiliar();
	}

	public void atTurnStartPostDraw() {
		AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Spark(), 1));
	}

	public void atBattleStart() {
		AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
		AbstractDungeon.actionManager.addToBottom(new DiscoveryAction());
	}
}