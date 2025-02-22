package sts.touhouspire.mod.character.marisa.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import sts.touhouspire.mod.character.marisa.data.Identifiers;

public class SproutingBranch extends CustomRelic {

	public static final String ID = Identifiers.Relics.SPROUTING_BRANCH;
	private static final String IMG = "marisa/img/relics/sproutingBranch.png";
	private static final String IMG_OTL = "marisa/img/relics/outline/sproutingBranch.png";

	private static final int REGENERATION_AMOUNT = 5;

	public SproutingBranch() {
		super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.SPECIAL, LandingSound.FLAT);
	}

	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	public AbstractRelic makeCopy() {
		return new SproutingBranch();
	}

	// TODO: Investigate
	@Override
	public void onEquip() {
		AbstractDungeon.rareRelicPool.remove("Dead Branch");
	}

	public void atBattleStart() {
		AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
				new RegenPower(AbstractDungeon.player, REGENERATION_AMOUNT), REGENERATION_AMOUNT)
		);
	}
}