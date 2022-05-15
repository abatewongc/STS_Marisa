package sts.touhouspire.mod.character.marisa.relics;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import sts.touhouspire.mod.character.marisa.abstracts.OnAmplifySubscriber;
import sts.touhouspire.mod.character.marisa.data.Identifiers;

public class AmplifyWand extends CustomRelic implements OnAmplifySubscriber {

	public static final String ID = Identifiers.Relics.AMPLIFY_WAND;
	private static final String IMG = "marisa/img/relics/AmplifyWand_s.png";
	private static final String IMG_OTL = "marisa/img/relics/outline/AmplifyWand_s.png";
	private static final int BLOCK_AMOUNT = 4;

	public AmplifyWand() {
		super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.UNCOMMON, LandingSound.FLAT);
	}

	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	public AbstractRelic makeCopy() {
		return new AmplifyWand();
	}

	public void onTrigger() {
		this.flash();
		AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, BLOCK_AMOUNT));
	}

	@Override
	public void onAmplify() {
		this.onTrigger();
	}
}