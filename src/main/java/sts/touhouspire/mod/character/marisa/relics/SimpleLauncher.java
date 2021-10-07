package sts.touhouspire.mod.character.marisa.relics;

import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import sts.touhouspire.mod.character.marisa.data.Identifiers;

public class SimpleLauncher extends CustomRelic {

	public static final String ID = Identifiers.Relics.SIMPLE_LAUNCHER;
	private static final String IMG = "marisa/img/relics/FlashLight.png";
	private static final String IMG_OTL = "marisa/img/relics/outline/FlashLight.png";
	private static final int PRICE = 300;
	private static final int STACKS_PER_CHARGE_MODIFIER = -2;

	public SimpleLauncher() {
		super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.SHOP, LandingSound.HEAVY);
	}

	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	@Override
	public int getPrice() {
		return PRICE;
	}

	public int getStacksPerChargeModifier() {
		return STACKS_PER_CHARGE_MODIFIER;
	}

	public AbstractRelic makeCopy() {
		return new SimpleLauncher();
	}

}