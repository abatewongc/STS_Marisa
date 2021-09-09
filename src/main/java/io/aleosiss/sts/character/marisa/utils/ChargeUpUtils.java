package io.aleosiss.sts.character.marisa.utils;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import io.aleosiss.sts.character.marisa.powers.Marisa.ChargeUpPower;

public class ChargeUpUtils {
	public static void addChargeUp(int chargeUpIncrease, boolean addToTop) {
		ApplyPowerAction chargeUpPowerAction = createChargeUpPowerAction(chargeUpIncrease);
		if (addToTop) {
			AbstractDungeon.actionManager.addToTop(chargeUpPowerAction);
		} else {
			AbstractDungeon.actionManager.addToBottom(chargeUpPowerAction);
		}
	}

	public static ApplyPowerAction createChargeUpPowerAction(int chargeUpIncrease) {
		return new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ChargeUpPower(AbstractDungeon.player, chargeUpIncrease), chargeUpIncrease);
	}
}
