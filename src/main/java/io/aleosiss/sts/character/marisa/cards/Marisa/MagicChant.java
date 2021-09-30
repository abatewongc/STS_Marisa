package io.aleosiss.sts.character.marisa.cards.Marisa;

import io.aleosiss.sts.character.marisa.action.MagicChantAction;
import io.aleosiss.sts.character.marisa.data.Identifiers;
import io.aleosiss.sts.character.marisa.patches.AbstractCardEnum;
import io.aleosiss.sts.character.marisa.abstracts.MarisaCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MagicChant extends MarisaCard {

	public static final String ID = Identifiers.Cards.MAGIC_CHANT;
	public static final String IMG_PATH = "marisa/img/cards/Chant.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final String DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 1;
	private static final int COST_UPG = 0;
	private static final int RTN = 2;
	//private static final int UPG_RTN = 1;

	public MagicChant() {
		super(
				ID, NAME, IMG_PATH,
				COST, DESCRIPTION,
				AbstractCard.CardType.SKILL,
				AbstractCardEnum.MARISA_COLOR,
				AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.SELF
		);

		this.magicNumber = this.baseMagicNumber = RTN;
		this.exhaust = true;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
				new MagicChantAction()
		);
	}

	public AbstractCard makeCopy() {
		return new MagicChant();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			//this.exhaust = false;
			upgradeBaseCost(COST_UPG);
			//upgradeMagicNumber(UPG_RTN);
			//this.rawDescription = DESCRIPTION_UPG;
			//initializeDescription();
		}
	}
}