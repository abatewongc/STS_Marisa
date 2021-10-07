package sts.touhouspire.mod.character.marisa.cards.Marisa;

import sts.touhouspire.mod.character.marisa.action.DamageUpAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sts.touhouspire.mod.character.marisa.abstracts.MarisaCard;
import sts.touhouspire.mod.character.marisa.data.Identifiers;
import sts.touhouspire.mod.character.marisa.patches.AbstractCardEnum;

public class Upgrade extends MarisaCard {

	public static final String ID = Identifiers.Cards.UPGRADE;
	public static final String IMG_PATH = "marisa/img/cards/Upgrade.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 0;
	private static final int STC = 2;
	private static final int UPG_STC = 1;

	public Upgrade() {
		super(
				ID,
				NAME,
				IMG_PATH,
				COST,
				DESCRIPTION,
				AbstractCard.CardType.SKILL,
				AbstractCardEnum.MARISA_COLOR,
				AbstractCard.CardRarity.COMMON,
				AbstractCard.CardTarget.SELF
		);

		this.baseMagicNumber = this.magicNumber = STC;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
				new DamageUpAction(this.magicNumber)
		);
	}

	public AbstractCard makeCopy() {
		return new Upgrade();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(UPG_STC);
		}
	}
}