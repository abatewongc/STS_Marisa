package io.aleosiss.sts.character.marisa.cards.Marisa;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import io.aleosiss.sts.character.marisa.action.PropBagAction;
import io.aleosiss.sts.character.marisa.data.Identifiers;
import io.aleosiss.sts.character.marisa.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;

public class PropBag extends CustomCard {

	public static final String ID = Identifiers.Cards.PROP_BAG;
	public static final String IMG_PATH = "marisa/img/cards/PropBag.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 0;
	private static final int PRODUCE = 1;
	private static final int PRODUCE_UPG = 1;

	public PropBag() {
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
		this.exhaust = true;
		//this.isInnate = true;
		this.magicNumber = this.baseMagicNumber = PRODUCE;

	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		for (int i = 0; i < this.magicNumber; i++) {
			AbstractDungeon.actionManager.addToBottom(
					new PropBagAction()
			);
		}
	}

	public AbstractCard makeCopy() {
		return new PropBag();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.isInnate = true;
			//this.exhaust = false;
			this.rawDescription = DESCRIPTION_UPG;
			//upgradeMagicNumber(PRODUCE_UPG);
			initializeDescription();
		}
	}
}