package sts.touhouspire.mod.character.marisa.powers.Marisa;

import sts.touhouspire.mod.character.marisa.cards.derivations.Exhaustion;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import sts.touhouspire.mod.character.marisa.MarisaModHandler;
import sts.touhouspire.mod.character.marisa.action.ConsumeChargeUpAction;
import sts.touhouspire.mod.character.marisa.data.Identifiers;
import sts.touhouspire.mod.character.marisa.relics.SimpleLauncher;

import static sts.touhouspire.mod.character.marisa.data.Identifiers.Relics.SIMPLE_LAUNCHER;

public class ChargeUpPower extends AbstractPower {
	public static final String POWER_ID = Identifiers.Powers.CHARGE_UP;
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	private static final int DEFAULT_STACKS_PER_CHARGE = 8;
	private int numberOfCharges;
	private int stacksPerCharge; // amount of stacks of charge up considered to be a single 'charge'

	public ChargeUpPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = exhaustionCheck() ? 0 : amount;
		this.type = AbstractPower.PowerType.BUFF;
		updateDescription();
		this.img = new Texture("marisa/img/powers/generator.png");

		getDivider();
		this.numberOfCharges = this.amount / this.stacksPerCharge;
	}

	@Override
	public void stackPower(int stackAmount) {
		if (stackAmount > 0) {
			if (exhaustionCheck()) {
				return;
			}
		}
		this.fontScale = 8.0F;
		this.amount += stackAmount;
		if (this.amount <= 0) {
			this.amount = 0;
		}

		getDivider();
		this.numberOfCharges = this.amount / this.stacksPerCharge;
	}

	public void updateDescription() {
		if (this.numberOfCharges > 0) {
			this.description =
					(
							DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]
									+ ", " + DESCRIPTIONS[2] + (int) Math.pow(2, this.numberOfCharges) + DESCRIPTIONS[3]
					);
		} else {
			this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + ".");
		}
	}

	@Override
	public void onAfterCardPlayed(AbstractCard card) {
		if ((this.owner.hasPower(Identifiers.Powers.ONE_TIME_OFF)) || (exhaustionCheck())) {
			return;
		}
		if ((this.numberOfCharges > 0) && (card.type == CardType.ATTACK)) {
			MarisaModHandler.logger.info("ChargeUpPower : onPlayCard : consuming stacks for :" + card.cardID);

			flash();
			getDivider();
			AbstractDungeon.actionManager.addToTop(new ConsumeChargeUpAction(numberOfCharges * this.stacksPerCharge));
		}
	}

	@Override
	public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
		if ((this.owner.hasPower(Identifiers.Powers.ONE_TIME_OFF)) || (exhaustionCheck())) {
			return damage;
		}
		if (numberOfCharges > 0) {
			if ((type == DamageType.NORMAL) && (this.amount >= 1)) {
				return getFinalDamage(damage, this.numberOfCharges);
			}
		}
		return damage;
	}

	private float getFinalDamage(float damage, int numberOfCharges) {
		return (float) (damage * Math.pow(2, numberOfCharges));
	}

	private void getDivider() {
		this.stacksPerCharge = DEFAULT_STACKS_PER_CHARGE;
		if (AbstractDungeon.player.hasRelic(SIMPLE_LAUNCHER)) {
			SimpleLauncher relic = (SimpleLauncher) AbstractDungeon.player.getRelic(SIMPLE_LAUNCHER);
			this.stacksPerCharge += relic.getStacksPerChargeModifier();
		}
	}

	private boolean exhaustionCheck() {
		boolean isExhausted = false;
		for (AbstractCard card : AbstractDungeon.player.hand.group) {
			if (card instanceof Exhaustion) {
				isExhausted = true;
				break;
			}
		}
		return isExhausted;
	}
}