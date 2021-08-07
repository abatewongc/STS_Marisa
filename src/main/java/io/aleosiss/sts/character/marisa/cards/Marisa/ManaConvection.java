package io.aleosiss.sts.character.marisa.cards.Marisa;

import io.aleosiss.sts.character.marisa.action.ManaConvectionAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import io.aleosiss.sts.character.marisa.data.Identifiers;
import io.aleosiss.sts.character.marisa.patches.AbstractCardEnum;
import io.aleosiss.sts.character.marisa.abstracts.MarisaModCard;

public class ManaConvection extends MarisaModCard {

	public static final String ID = Identifiers.Cards.MANA_CONVECTION;
	public static final String IMG_PATH = "marisa/img/cards/ManaConvection.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int DRAW = 2;
	private static final int UPG_DRAW = 1;

	public ManaConvection() {
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
		this.magicNumber = this.baseMagicNumber = DRAW;
		this.exhaust = true;

	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
				new ManaConvectionAction(this.magicNumber)
		);
	}

	public AbstractCard makeCopy() {
		return new ManaConvection();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(UPG_DRAW);
		}
	}
}