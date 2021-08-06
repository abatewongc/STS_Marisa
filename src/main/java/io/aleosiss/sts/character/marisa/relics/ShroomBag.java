package io.aleosiss.sts.character.marisa.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.curses.Parasite;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import basemod.abstracts.CustomRelic;
import io.aleosiss.sts.character.marisa.data.Identifiers;
import io.aleosiss.sts.character.marisa.relics.intr.ShroomBagInterface;

public class ShroomBag extends CustomRelic implements ShroomBagInterface {

	public static final String ID = Identifiers.Relics.SHROOM_BAG;
	private static final String IMG = "marisa/img/relics/ShroomBag.png";
	private static final String IMG_OTL = "marisa/img/relics/outline/ShroomBag.png";

	private int healingPerParasite = 2;
	private int drawPerParasite = 1;
	private int numOfParasitesToAddOnEquip = 2;

	public ShroomBag(int healingPerParasite, int drawPerParasite, int numOfParasitesToAddOnEquip) {
		super(ID, new Texture(IMG), new Texture(IMG_OTL), RelicTier.COMMON, LandingSound.FLAT);
		this.healingPerParasite = healingPerParasite;
		this.drawPerParasite = drawPerParasite;
		this.numOfParasitesToAddOnEquip = numOfParasitesToAddOnEquip;
	}

	public ShroomBag() {
		super(ID, new Texture(IMG), new Texture(IMG_OTL), RelicTier.COMMON, LandingSound.FLAT);
	}

	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	public AbstractRelic makeCopy() {
		if (AbstractDungeon.player != null) {
			if (AbstractDungeon.player.hasRelic(ID)) {
				return new BigShroomBag();
			}
		}
		return new ShroomBag();
	}

	public void onEquip() {
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