package sts.touhouspire.mod.character.marisa.powers.Marisa;

import sts.touhouspire.mod.character.marisa.MarisaModHandler;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import sts.touhouspire.mod.character.marisa.data.Identifiers;

public class SupernovaPower extends AbstractPower {

	public static final String POWER_ID = Identifiers.Powers.SUPERNOVA;
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	private AbstractPlayer player;

	public SupernovaPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
		updateDescription();
		this.img = new Texture("marisa/img/powers/impulse.png");
		this.player = AbstractDungeon.player;
	}

	public void atEndOfTurn(boolean isPlayer) {
		if (!this.player.hand.isEmpty()) {
			flash();
			for (AbstractCard c : this.player.hand.group) {
				AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, this.player.hand));
			}
		}
	}

	public void onExhaust(AbstractCard card) {
		boolean apply = (card instanceof Burn);
		if (apply) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new StrengthPower(player, this.amount), this.amount));
		}
	}

	@Override
	public void onDrawOrDiscard() {
		ExhaustDiscard();
	}

	@Override
	public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
		ExhaustDiscard();
	}

	@Override
	public void onInitialApplication() {
		ExhaustDiscard();
	}

	private void ExhaustDiscard() {
		for (AbstractCard c : AbstractDungeon.player.hand.group) {
			if (discardCheck(c)) {
				c.exhaust = true;
				c.isEthereal = true;
			}
		}
	}

	private boolean discardCheck(AbstractCard card) {
		if ((card.type == CardType.CURSE) || (card.type == CardType.STATUS)) {
			MarisaModHandler.logger.info("SuperNovaPower : discardCheck : " + card.cardID + " detected.");
			return true;
		}
		return false;
	}

	public void updateDescription() {
		this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]);
	}

}