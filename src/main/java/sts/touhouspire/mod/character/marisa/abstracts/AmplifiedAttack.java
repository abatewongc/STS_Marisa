package sts.touhouspire.mod.character.marisa.abstracts;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sts.touhouspire.mod.character.marisa.data.Identifiers;

import java.util.ArrayList;

import static sts.touhouspire.mod.character.marisa.data.Identifiers.*;

public abstract class AmplifiedAttack extends AmplifyCard {
	private int ampDamage = -1;
	protected int ampNumber = 0;
	protected int[] multiAmpDamage;
	protected boolean isException = false;

	public AmplifiedAttack(String id, String name, String img, int cost, String rawDescription,
	                       AbstractCard.CardType type, AbstractCard.CardColor color, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target
	) {
		super(id, name, img, cost, rawDescription, type, color, rarity, target);
	}

	@Override
	public void applyPowers() {
		amplify();
	}

	private void amplify() {
		if (!this.isException) {
			this.damage = this.baseDamage;
			this.ampDamage = (this.baseDamage + this.ampNumber);
			this.block = (this.baseBlock = this.ampDamage);
		}

		this.isDamageModified = false;
		this.isBlockModified = false;

		float tmp = this.damage;
		float amp = this.block;

		tmp = calculate(tmp, null);
		amp = calculate(amp, null);

		modifyDamage(tmp, amp);

		if (this.isMultiDamage) {
			int monsters = AbstractDungeon.getCurrRoom().monsters.monsters.size();
			this.multiDamage = new int[monsters];
			for (int i = 0; i < monsters; i++) {
				this.multiDamage[i] = MathUtils.floor(tmp);
			}
			this.multiAmpDamage = new int[monsters];
			for (int i = 0; i < monsters; i++) {
				this.multiAmpDamage[i] = MathUtils.floor(amp);
			}
		}
	}

	private float calculate(float base, AbstractMonster target) {
		float temp = base;
		AbstractPlayer player = AbstractDungeon.player;
		if ((AbstractDungeon.player.hasRelic(Relics.WRIST_BLADE)) && (this.costForTurn == 0)) {
			temp += 3.0f;
		}
		for (AbstractPower power : player.powers) {
			temp = power.atDamageGive(temp, this.damageTypeForTurn);
		}
		if (target != null) {
			for (AbstractPower power : target.powers) {
				temp = power.atDamageReceive(temp, this.damageTypeForTurn);
			}
		}
		for (AbstractPower power : player.powers) {
			temp = power.atDamageFinalGive(temp, this.damageTypeForTurn);
		}
		if (target != null) {
			for (AbstractPower power : target.powers) {
				temp = power.atDamageFinalReceive(temp, this.damageTypeForTurn);
			}
		}
		if (temp < 0) {
			temp = 0;
		}
		return temp;
	}

	public void calculateDamageDisplay(AbstractMonster mo) {
		calculateCardDamage(mo);
	}

	public void calculateCardDamage(AbstractMonster mo) {
		if (!this.isException) {
			this.damage = this.baseDamage;
			this.ampDamage = (this.baseDamage + this.ampNumber);
			this.block = (this.baseBlock = this.ampDamage);
		}
		this.isDamageModified = false;
		this.isBlockModified = false;
		if ((!this.isMultiDamage) && (mo != null)) {
			float tmp = this.damage;
			float amp = this.block;
			tmp = calculate(tmp, mo);
			amp = calculate(amp, mo);
			modifyDamage(tmp, amp);
		} else {
			ArrayList<AbstractMonster> m = AbstractDungeon.getCurrRoom().monsters.monsters;
			float[] tmp = new float[m.size()];
			float[] amp = new float[m.size()];
			for (int i = 0; i < m.size(); i++) {
				tmp[i] = calculate(damage, m.get(i));
				amp[i] = calculate(block, m.get(i));
				if (this.baseDamage != (int) tmp[i]) {
					this.isDamageModified = true;
				}
				if (this.ampDamage != (int) amp[i]) {
					this.isBlockModified = true;
				}
			}
			this.multiDamage = new int[m.size()];
			for (int i = 0; i < tmp.length; i++) {
				this.multiDamage[i] = MathUtils.floor(tmp[i]);
			}
			this.multiAmpDamage = new int[m.size()];
			for (int i = 0; i < amp.length; i++) {
				this.multiAmpDamage[i] = MathUtils.floor(amp[i]);
			}
			this.damage = this.multiDamage[0];
			this.block = this.multiAmpDamage[0];
		}
	}

	private void modifyDamage(float tmp, float amp) {
		if (this.baseDamage != (int) tmp) {
			this.isDamageModified = true;
		}
		if (this.ampDamage != (int) amp) {
			this.isBlockModified = true;
		}
		this.damage = MathUtils.floor(tmp);
		this.block = MathUtils.floor(amp);
	}
}
