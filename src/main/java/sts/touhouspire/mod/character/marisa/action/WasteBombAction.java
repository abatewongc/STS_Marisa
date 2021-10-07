package sts.touhouspire.mod.character.marisa.action;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import sts.touhouspire.mod.character.marisa.MarisaModHandler;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class WasteBombAction extends AbstractGameAction {

	private final int damage;
	private int numberOfHits;
	private final int strengthDownValue;
	private final AbstractCreature target;
	private final DamageInfo damageInfo;

	public WasteBombAction(AbstractCreature target, int dmg, int numTimes, int strengthDownValue) {
		this.actionType = AbstractGameAction.ActionType.DAMAGE;
		this.duration = Settings.ACTION_DUR_FAST;
		this.damage = dmg;
		this.target = target;
		this.numberOfHits = numTimes;
		this.strengthDownValue = strengthDownValue;
		this.damageInfo = new DamageInfo(AbstractDungeon.player, this.damage, DamageType.NORMAL);
	}

	public void update() {
		if (target == null) {
			this.isDone = true;
			return;
		}

		if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
			AbstractDungeon.actionManager.clearPostCombatActions();
			this.isDone = true;
			return;
		}

		if (this.target == null) {
			MarisaModHandler.logger.info("WasteBombAction : error : target == null !");
			this.isDone = true;
			return;
		}

		if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
			AbstractDungeon.actionManager.clearPostCombatActions();
			this.isDone = true;
			return;
		}
		if (this.target.currentHealth > 0) {
			AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));

			float tmp = damageInfo.output;
			for (AbstractPower p : target.powers) {
				tmp = p.atDamageReceive(tmp, damageInfo.type);
				if (damageInfo.base != (int) tmp) {
					damageInfo.isModified = true;
				}
			}
			for (AbstractPower p : target.powers) {
				tmp = p.atDamageFinalReceive(tmp, damageInfo.type);
				if (damageInfo.base != (int) tmp) {
					damageInfo.isModified = true;
				}
			}
			damageInfo.output = MathUtils.floor(tmp);
			if (damageInfo.output < 0) {
				damageInfo.output = 0;
			}

			this.target.damage(this.damageInfo);
			AbstractPlayer player = AbstractDungeon.player;

			if ((!this.target.isDeadOrEscaped()) && (!this.target.isDying)) {
				this.addToBot(new ApplyPowerAction(target, player, new StrengthPower(target, -this.strengthDownValue), -this.strengthDownValue));
				if (!target.hasPower("Artifact")) {
					this.addToBot(new ApplyPowerAction(target, player, new GainStrengthPower(target, this.strengthDownValue), this.strengthDownValue));
				}
			}

			if ((this.numberOfHits > 1) && (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())) {
				this.numberOfHits--;
				AbstractDungeon.actionManager.addToTop(
						new WasteBombAction(AbstractDungeon.getMonsters().getRandomMonster(true),
								this.damage, this.numberOfHits, this.strengthDownValue
						)
				);
			}
		}

		AbstractDungeon.actionManager.addToTop(new WaitAction(0.2F));
		this.isDone = true;
	}
}