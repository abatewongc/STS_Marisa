package sts.touhouspire.mod.character.marisa.action;


import static sts.touhouspire.mod.character.marisa.MarisaModHandler.logger;

import sts.touhouspire.mod.character.marisa.data.Identifiers;
import sts.touhouspire.mod.character.marisa.monsters.ZombieFairy;
import sts.touhouspire.mod.character.marisa.powers.monsters.LimboContactPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FlightPower;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.powers.SlowPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import sts.touhouspire.mod.character.marisa.utils.CompatibilityUtils;

public class SummonFairyAction
		extends AbstractGameAction {

	private static final float pos0X = 210.0F;
	private static final float pos0Y = 10.0F;
	private static final float pos1X = -220.0F;
	private static final float pos1Y = 50.0F;
	private static final float pos2X = 180.0F;
	private static final float pos2Y = 320.0F;
	private static final float pos3X = -250.0F;
	private static final float pos3Y = 310.0F;
	private static final float COORDINATE[][] = {
			{pos0X, pos0Y},
			{pos1X, pos1Y},
			{pos2X, pos2Y},
			{pos3X, pos3Y}
	};

	public SummonFairyAction(AbstractMonster monster) {
		this.source = monster;
		this.duration = Settings.ACTION_DUR_XFAST;
	}

	public void update() {
		int count = 0;
		for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
			if ((monster != this.source) && (monster instanceof ZombieFairy)) {
				if (monster.isDying) {
					logger.info("SummonFairyAction : reviving Fairy;");
					((ZombieFairy) monster).turnNum = 0;
					AbstractDungeon.actionManager.addToTop(
							new ReviveFairyAction(monster, this.source)
					);
					AbstractDungeon.actionManager.addToTop(
							new ApplyPowerAction(monster, monster, new MinionPower(monster))
					);
					AbstractDungeon.actionManager.addToTop(
							new ApplyPowerAction(monster, monster, new LimboContactPower(monster))
					);
					AbstractDungeon.actionManager.addToTop(
							new ApplyPowerAction(monster, monster, new FlightPower(monster, 99))
					);
					CompatibilityUtils.addPhiloStoneToMonster(monster);
					CompatibilityUtils.addModPowersToMonster(monster);
					logger.info("SummonFairyAction : done reviving Fairy;");
					this.isDone = true;
					return;
				}
				logger.info("SummonFairyAction : Alive Fairy found, increasing counter;");
				count++;
				logger.info("SummonFairyAction : counter increased: " + count);
			}
		}
		if (count < 4) {
			logger.info("SummonFairyAction : spawning Fairy;");
			AbstractDungeon.actionManager.addToTop(
					new SpawnFairyAction(COORDINATE[count][0], COORDINATE[count][1])
			);
		}
		this.isDone = true;
	}
}
