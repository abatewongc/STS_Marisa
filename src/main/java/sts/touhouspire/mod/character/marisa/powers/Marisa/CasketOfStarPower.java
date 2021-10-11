package sts.touhouspire.mod.character.marisa.powers.Marisa;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import sts.touhouspire.mod.character.marisa.MarisaModHandler;
import sts.touhouspire.mod.character.marisa.cards.derivations.Spark;
import sts.touhouspire.mod.character.marisa.data.Identifiers;

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

	@Override
	public void onGainedBlock(float blockAmount) {
		AbstractCard card = new Spark();
		// if generate block while the player is ending turn, then our spark should stay in our hand.
		MarisaModHandler.logger.info("COS: endTurnQueued was: " + AbstractDungeon.player.endTurnQueued);
		MarisaModHandler.logger.info("COS: isEndingTurn was: " + AbstractDungeon.player.isEndingTurn);
		if (this.upgraded) {
			card.upgrade();
		}
		card.retain = AbstractDungeon.player.endTurnQueued;
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card, this.amount));
	}

	public void updateDescription() {
		this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
	}
}