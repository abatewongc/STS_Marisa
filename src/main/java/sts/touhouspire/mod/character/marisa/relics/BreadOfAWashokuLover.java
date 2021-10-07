package sts.touhouspire.mod.character.marisa.relics;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import sts.touhouspire.mod.character.marisa.MarisaModHandler;
import basemod.abstracts.CustomRelic;
import sts.touhouspire.mod.character.marisa.data.Identifiers;

public class BreadOfAWashokuLover extends CustomRelic {

	public static final String ID = Identifiers.Relics.BREAD_OF_A_WASHOKU_LOVER;
	private static final String IMG = "marisa/img/relics/bread_s.png";
	private static final String IMG_OTL = "marisa/img/relics/outline/bread_s.png";
	private static final String USED_IMG = "marisa/img/relics/usedBread_s.png";

	private static final int NUM_USES = 13;
	private static final int HEALING_PER_USE = 1;
	private static final int HEALTH_INCREASE_WHEN_USED_UP = NUM_USES;


	public BreadOfAWashokuLover() {
		super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.UNCOMMON, LandingSound.FLAT);
		this.usedUp = false;
	}

	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	public AbstractRelic makeCopy() {
		return new BreadOfAWashokuLover();
	}

	public void onEquip() {
		this.counter = 0;
	}

	public void onExhaust(AbstractCard card) {
		MarisaModHandler.logger.info("BreadOfAWashokuLover : onExhaust : this.usedUp :" + this.usedUp + " ; this.counter : " + this.counter);
		if ((this.usedUp) || (this.counter < 0)) {
			return;
		}

		if ((card.type == CardType.CURSE) || (card.type == CardType.STATUS)) {
			this.counter++;
			this.flash();
			AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
			AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, HEALING_PER_USE));
		}

		if (counter >= NUM_USES) {
			MarisaModHandler.logger.info("BreadOfAWashokuLover : onExhaust : Using Up");
			this.flash();
			AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
			this.img = (ImageMaster.loadImage(USED_IMG));
			AbstractDungeon.player.increaseMaxHp(HEALTH_INCREASE_WHEN_USED_UP, true);
			usedUp();
			this.counter = -2;
		}
	}
}