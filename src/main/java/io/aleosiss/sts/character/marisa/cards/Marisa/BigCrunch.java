package io.aleosiss.sts.character.marisa.cards.Marisa;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import io.aleosiss.sts.character.marisa.action.BigCruncAction;
import io.aleosiss.sts.character.marisa.data.Identifiers;
import io.aleosiss.sts.character.marisa.patches.AbstractCardEnum;
import io.aleosiss.sts.character.marisa.abstracts.MarisaCard;

public class BigCrunch extends MarisaCard {

	public static final String ID = Identifiers.Cards.BIG_CRUNCH;
	public static final String IMG_PATH = "marisa/img/cards/BigCrunch.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 0;
	private static final int DIV = 5;
	private static final int UPG_DIV = -1;

	public BigCrunch() {
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

		this.exhaust = true;
		this.magicNumber = this.baseMagicNumber = DIV;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
				new BigCruncAction(this.upgraded)
		);
	}

	public AbstractCard makeCopy() {
		return new BigCrunch();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(UPG_DIV);
		}
	}
}