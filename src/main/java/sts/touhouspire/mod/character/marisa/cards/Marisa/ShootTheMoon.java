package sts.touhouspire.mod.character.marisa.cards.Marisa;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import sts.touhouspire.mod.character.marisa.abstracts.AmplifiedAttack;
import sts.touhouspire.mod.character.marisa.data.Identifiers;
import sts.touhouspire.mod.character.marisa.patches.AbstractCardEnum;
import sts.touhouspire.mod.character.marisa.utils.AmplifyUtils;

public class ShootTheMoon extends AmplifiedAttack {

	public static final String ID = Identifiers.Cards.SHOOT_THE_MOON;
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String IMG_PATH = "marisa/img/cards/ShootTheMoon_v1.png";

	private static final int COST = 1;
	private static final int ATK_DMG = 8;
	private static final int UPG_DMG = 3;
	private static final int AMP_DMG = 5;
	private static final int UPG_AMP = 2;
	private static final int AMP = 1;

	public ShootTheMoon() {
		super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnum.MARISA_COLOR, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
		this.baseDamage = ATK_DMG;
		this.ampNumber = AMP_DMG;
		this.baseBlock = this.baseDamage + this.ampNumber;
	}

	public void use(AbstractPlayer player, AbstractMonster monster) {
		boolean targetingBoss = (monster.type == AbstractMonster.EnemyType.BOSS);

		if (AmplifyUtils.Amplified(this, AMP)) {
			amplifiedUse(player, monster, targetingBoss);
		} else {
			use(player, monster, targetingBoss);
		}
	}

	private void use(AbstractPlayer player, AbstractMonster monster, boolean targetingBoss) {
		if ((!monster.powers.isEmpty()) && (!targetingBoss)) {
			ArrayList<AbstractPower> powers = new ArrayList<>();
			for (AbstractPower pow : monster.powers) {
				if (pow.type == AbstractPower.PowerType.BUFF) {
					powers.add(pow);
				}
			}
			if (!powers.isEmpty()) {
				AbstractPower power = powers.get((int) (Math.random() * powers.size()));
				AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(monster, player, power));
			}
		}

		AbstractDungeon.actionManager.addToBottom(
				new DamageAction(monster, new DamageInfo(player, this.damage, this.damageTypeForTurn),
						AbstractGameAction.AttackEffect.SLASH_DIAGONAL
				)
		);
	}

	private void amplifiedUse(AbstractPlayer player, AbstractMonster monster, boolean targetingBoss) {
		if (!targetingBoss) {
			for (AbstractPower power : monster.powers) {
				if (power.type == AbstractPower.PowerType.BUFF) {
					AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(monster, player, power));
				}
			}
		}

		AbstractDungeon.actionManager.addToBottom(
				new DamageAction(monster, new DamageInfo(player, this.block, this.damageTypeForTurn),
						AbstractGameAction.AttackEffect.SLASH_DIAGONAL
				)
		);
	}

	public AbstractCard makeCopy() {
		return new ShootTheMoon();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeDamage(UPG_DMG);
			this.ampNumber += UPG_AMP;
			this.block = this.baseBlock = this.baseDamage + this.ampNumber;
			this.isBlockModified = true;
		}
	}
}