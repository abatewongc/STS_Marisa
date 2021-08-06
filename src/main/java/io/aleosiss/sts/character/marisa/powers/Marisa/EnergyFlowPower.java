package io.aleosiss.sts.character.marisa.powers.Marisa;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.localization.PowerStrings;
import io.aleosiss.sts.character.marisa.data.Identifiers;
import io.aleosiss.sts.character.marisa.utils.ChargeUpUtils;

public class EnergyFlowPower
		extends AbstractPower {

	public static final String POWER_ID = Identifiers.Powers.ENERGY_FLOW;
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public EnergyFlowPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = AbstractPower.PowerType.BUFF;
		this.amount = amount;
		updateDescription();
		this.img = new Texture("marisa/img/powers/electricField.png");
	}

	public void atEndOfTurn(boolean isPlayer) {
		flash();
		ChargeUpUtils.addChargeUp(this.amount);
	}

	public void updateDescription() {
		this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
	}
}