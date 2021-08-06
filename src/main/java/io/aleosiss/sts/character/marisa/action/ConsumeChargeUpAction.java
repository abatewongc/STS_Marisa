package io.aleosiss.sts.character.marisa.action;

import io.aleosiss.sts.character.marisa.MarisaModHandler;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import io.aleosiss.sts.character.marisa.data.Identifiers;

public class ConsumeChargeUpAction extends AbstractGameAction {

	private int amt;

	public ConsumeChargeUpAction(int amount) {
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_FAST;
		this.amt = amount;
	}

	public void update() {
		this.isDone = false;
		AbstractPlayer player = AbstractDungeon.player;

		if (!player.hasPower(Identifiers.Powers.CHARGE_UP)) {
			this.isDone = true;
			return;
		}

		AbstractPower chargeUpPower = player.getPower(Identifiers.Powers.CHARGE_UP);
		MarisaModHandler.logger.info(
				"ConsumeChargeUpAction :"
						+ " Consume amount : "
						+ this.amt
						+ " ; Charge-Up stacks : "
						+ chargeUpPower.amount
		);
		if ((this.amt <= 0) || (chargeUpPower.amount <= 0)) {
			this.isDone = true;
			return;
		}
		chargeUpPower.stackPower(-this.amt);
		if (player.hasPower(Identifiers.Powers.ORRERYS_SUN)) {
			player.getPower(Identifiers.Powers.ORRERYS_SUN).onSpecificTrigger();
		}
		chargeUpPower.updateDescription();
		this.isDone = true;
	}
}