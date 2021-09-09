package io.aleosiss.sts.character.marisa.action;

//public class SpawnFairyAction

import io.aleosiss.sts.character.marisa.monsters.ZombieFairy;
import io.aleosiss.sts.character.marisa.powers.monsters.LimboContactPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FlightPower;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import io.aleosiss.sts.character.marisa.utils.CompatibilityUtils;

public class SpawnFairyAction extends AbstractGameAction {

	private boolean used;
	private static final float DURATION = 0.1F;
	private AbstractMonster monster;
	private int targetSlot;

	public SpawnFairyAction(float x, float y) {
		this(x, y, -99);
	}

	public SpawnFairyAction(float x, float y, int slot) {
		this.used = false;
		this.actionType = ActionType.SPECIAL;
		this.duration = DURATION;
		this.monster = new ZombieFairy(x, y);
		this.targetSlot = slot;
		CompatibilityUtils.addPhiloStoneToMonster(this.monster);

	}

	public void update() {
		if (!this.used) {
			this.monster.init();
			this.monster.applyPowers();
			AbstractDungeon.getCurrRoom().monsters.addMonster(this.targetSlot, this.monster);
			this.monster.showHealthBar();

			CompatibilityUtils.addModPowersToMonster(this.monster);

			AbstractDungeon.actionManager.addToTop(
					new ApplyPowerAction(this.monster, this.monster, new MinionPower(this.monster))
			);
			AbstractDungeon.actionManager.addToTop(
					new ApplyPowerAction(this.monster, this.monster, new LimboContactPower(this.monster))
			);
			AbstractDungeon.actionManager.addToTop(
					new ApplyPowerAction(this.monster, this.monster, new FlightPower(this.monster, 99))
			);

			this.used = true;
		}

		this.tickDuration();
	}
}
