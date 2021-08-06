package io.aleosiss.sts.character.marisa.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import io.aleosiss.sts.character.marisa.data.Identifiers;

public class CatCart extends CustomRelic {

	public static final String ID = Identifiers.Relics.CAT_CART;
	private static final String IMG = "marisa/img/relics/CatCart.png";
	private static final String IMG_OTL = "marisa/img/relics/outline/CatCart.png";
	private static final int HEAL_PER_CHARGE = 4;

	public CatCart() {
		super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.SPECIAL, LandingSound.FLAT);
		this.counter = 0;
	}

	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	public void onEnterRoom(AbstractRoom room) {
		flash();
		this.counter++;
	}

	public void onTrigger() {
		if (this.counter > 0) {
			flash();
			AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
			int healAmount = this.counter * HEAL_PER_CHARGE;
			AbstractDungeon.player.heal(healAmount, true);
			this.counter = 0;
		}
	}

	public AbstractRelic makeCopy() {
		return new CatCart();
	}
}
