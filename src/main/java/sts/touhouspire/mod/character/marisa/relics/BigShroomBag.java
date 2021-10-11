package sts.touhouspire.mod.character.marisa.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.curses.Parasite;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import sts.touhouspire.mod.character.marisa.data.Identifiers;
import sts.touhouspire.mod.character.marisa.relics.intr.ShroomBagInterface;

public class BigShroomBag extends CustomRelic implements ShroomBagInterface {

	public static final String ID = Identifiers.Relics.BIG_SHROOM_BAG;
	private static final String IMG = "marisa/img/relics/BigShroomBag.png";
	private static final String IMG_OTL = "marisa/img/relics/outline/BigShroomBag.png";

	private int healingPerParasite = 3;
	private int drawPerParasite = 2;
	private int numOfParasitesToAddOnEquip = 1;

	public BigShroomBag(int healingPerParasite, int drawPerParasite, int numOfParasitesToAddOnEquip) {
		super(ID, new Texture(IMG), new Texture(IMG_OTL), RelicTier.SPECIAL, LandingSound.FLAT);
		this.healingPerParasite = healingPerParasite;
		this.drawPerParasite = drawPerParasite;
		this.numOfParasitesToAddOnEquip = numOfParasitesToAddOnEquip;
	}

	public BigShroomBag() {
		super(ID, new Texture(IMG), new Texture(IMG_OTL), RelicTier.SPECIAL, LandingSound.FLAT);
	}

	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	public AbstractRelic makeCopy() {
		return new BigShroomBag();
	}

	public void onEquip() {
		AbstractDungeon.player.loseRelic(Identifiers.Relics.SHROOM_BAG);

		for (int i = 0; i < this.numOfParasitesToAddOnEquip; i++) {
			AbstractDungeon.effectList.add(
					new ShowCardAndObtainEffect(new Parasite(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F)
			);
		}
	}

	@Override
	public int healingPerParasite() {
		return healingPerParasite;
	}

	@Override
	public int drawPerParasite() {
		return drawPerParasite;
	}

	@Override
	public int numOfParasitesToAddOnEquip() {
		return numOfParasitesToAddOnEquip;
	}
}