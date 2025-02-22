package sts.touhouspire.mod.character.marisa.cards.Marisa;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.touhouspire.mod.character.marisa.data.Identifiers;
import sts.touhouspire.mod.character.marisa.patches.AbstractCardEnum;
import sts.touhouspire.mod.character.marisa.powers.Marisa.ChargeUpPower;
import sts.touhouspire.mod.character.marisa.abstracts.MarisaCard;

public class ChargingUp extends MarisaCard {

	public static final String ID = Identifiers.Cards.CHARGING_UP;
	public static final String IMG_PATH = "marisa/img/cards/ChargingUp.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 1;
	private static final int STC = 5;
	//private static final int AMP = 1;
	private static final int UPG_STC = 3;

	public ChargingUp() {
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

		this.baseMagicNumber = this.magicNumber = STC;
		this.exhaust = true;
	}

	public void use(AbstractPlayer player, AbstractMonster monster) {
		int stack = this.magicNumber;
		AbstractDungeon.actionManager.addToBottom(
				new ApplyPowerAction(
						player,
						player,
						new ChargeUpPower(player, stack),
						stack
				)
		);
	}

	public AbstractCard makeCopy() {
		return new ChargingUp();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(UPG_STC);
			//this.rawDescription = DESCRIPTION_UPG;
			//initializeDescription();
		}
	}
}