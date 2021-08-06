package io.aleosiss.sts.character.marisa.powers.Marisa;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import io.aleosiss.sts.character.marisa.cards.derivations.Spark;
import io.aleosiss.sts.character.marisa.data.Identifiers;

public class CasketOfStarPower extends AbstractPower {

	public static final String POWER_ID = Identifiers.Powers.CASKET_OF_STARS;
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	private final boolean upgraded;

	public CasketOfStarPower(AbstractCreature owner, int amount, boolean upgraded) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
		this.upgraded = upgraded;
		updateDescription();
		this.img = new Texture("marisa/img/powers/energyNext.png");
	}

	public void onGainedBlock(float blockAmount) {
		AbstractCard card = new Spark();
		if (this.upgraded) {
			card.upgrade();
		}
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card, this.amount));
	}

	public void updateDescription() {
		this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
	}
}