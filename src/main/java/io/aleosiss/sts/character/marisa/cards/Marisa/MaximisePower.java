package io.aleosiss.sts.character.marisa.cards.Marisa;

import io.aleosiss.sts.character.marisa.cards.derivations.Exhaustion;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import io.aleosiss.sts.character.marisa.data.Identifiers;
import io.aleosiss.sts.character.marisa.patches.AbstractCardEnum;
import io.aleosiss.sts.character.marisa.powers.Marisa.MaximizePowerPower;
import io.aleosiss.sts.character.marisa.abstracts.MarisaCard;

public class MaximisePower extends MarisaCard {

	public static final String ID = Identifiers.Cards.MAXIMIZE_POWER;
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION;
	public static final String IMG_PATH = "marisa/img/cards/maxPower.png";
	private static final int COST = 3;

	public MaximisePower() {
		super(
				ID,
				NAME,
				IMG_PATH,
				COST,
				DESCRIPTION,
				AbstractCard.CardType.SKILL,
				AbstractCardEnum.MARISA_COLOR,
				AbstractCard.CardRarity.RARE,
				AbstractCard.CardTarget.SELF
		);
		this.magicNumber = this.baseMagicNumber = 2;
		this.exhaust = true;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		if (p.hasPower(Identifiers.Powers.CHARGE_UP)) {
			if (p.getPower(Identifiers.Powers.CHARGE_UP).amount > 0) {
				AbstractDungeon.actionManager.addToBottom(
						new GainEnergyAction(p.getPower(Identifiers.Powers.CHARGE_UP).amount)
				);
				p.getPower(Identifiers.Powers.CHARGE_UP).amount = 0;
			}
		}
		AbstractDungeon.actionManager.addToBottom(
				new ApplyPowerAction(
						p,
						p,
						new MaximizePowerPower(p, 1),
						1
				)
		);
		AbstractDungeon.actionManager.addToBottom(
				new MakeTempCardInHandAction(
						new Exhaustion(),
						1
				)
		);
	}

	public AbstractCard makeCopy() {
		return new MaximisePower();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			updateCost(-1);
			//upgradeMagicNumber(1);
			//this.rawDescription = DESCRIPTION_UPG;
			//initializeDescription();
			//this.exhaust = false;
		}
	}
}