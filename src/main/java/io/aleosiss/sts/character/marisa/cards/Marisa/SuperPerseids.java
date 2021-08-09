package io.aleosiss.sts.character.marisa.cards.Marisa;

import io.aleosiss.sts.character.marisa.MarisaModHandler;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import io.aleosiss.sts.character.marisa.data.Identifiers;
import io.aleosiss.sts.character.marisa.patches.AbstractCardEnum;
import io.aleosiss.sts.character.marisa.abstracts.MarisaModCard;

public class SuperPerseids extends MarisaModCard {

	public static final String ID = Identifiers.Cards.SUPER_PERSEIDS;
	public static final String IMG_PATH = "marisa/img/cards/SuperPerseids.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = -2;
	private static final int DMG = 16;
	private static final int UPG_DMG = 8;
	private static final int STACK = 2;
	private static final int UPG_STACK = 1;

	public SuperPerseids() {
		super(
				ID,
				NAME,
				IMG_PATH,
				COST,
				DESCRIPTION,
				AbstractCard.CardType.SKILL,
				AbstractCardEnum.MARISA_COLOR,
				AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.SELF
		);

		this.damage = this.baseDamage = DMG;
		this.magicNumber = this.baseMagicNumber = STACK;
		this.damageType = DamageType.THORNS;
		this.damageTypeForTurn = DamageType.THORNS;
	}

	public void triggerWhenDrawn() {
		this.applyPowers();
		AbstractDungeon.actionManager.addToBottom(
				new GainEnergyAction(1)
		);
	}

	@Override
	public boolean canUse(AbstractPlayer p, AbstractMonster m) {
		return false;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
	}

	public void triggerOnExhaust() {
		this.applyPowers();
		MarisaModHandler.logger.info("SuperPerseids : triggerOnExhaust : Damaging Random Enemy :"
				+ "; upgraded : " + this.upgraded
				+ "; damage : " + this.damage
		);
		AbstractDungeon.actionManager.addToBottom(
				new DamageRandomEnemyAction(
						new DamageInfo(
								AbstractDungeon.player,
								this.damage,
								DamageType.THORNS
						),
						AttackEffect.FIRE
				)
		);
	}

	public AbstractCard makeCopy() {
		return new SuperPerseids();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeDamage(UPG_DMG);
			upgradeMagicNumber(UPG_STACK);
		}
	}
}