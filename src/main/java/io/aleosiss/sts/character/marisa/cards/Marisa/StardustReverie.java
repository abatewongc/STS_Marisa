package io.aleosiss.sts.character.marisa.cards.Marisa;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import io.aleosiss.sts.character.marisa.abstracts.MarisaCard;
import io.aleosiss.sts.character.marisa.action.StarDustReverieAction;
import io.aleosiss.sts.character.marisa.data.Identifiers;
import io.aleosiss.sts.character.marisa.patches.AbstractCardEnum;

public class StardustReverie extends MarisaCard {

	public static final String ID = Identifiers.Cards.STARDUST_REVERIE;
	public static final String IMG_PATH = "marisa/img/cards/StarDustReverie.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 0;


	public StardustReverie() {
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
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
				new StarDustReverieAction(this.upgraded)
		);
	}

	public AbstractCard makeCopy() {
		return new StardustReverie();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			//this.exhaust = false;
			this.rawDescription = DESCRIPTION_UPG;
			initializeDescription();
		}
	}
}