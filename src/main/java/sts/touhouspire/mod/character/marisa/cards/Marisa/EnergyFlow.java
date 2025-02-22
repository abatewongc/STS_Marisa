package sts.touhouspire.mod.character.marisa.cards.Marisa;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sts.touhouspire.mod.character.marisa.abstracts.MarisaCard;
import sts.touhouspire.mod.character.marisa.data.Identifiers;
import sts.touhouspire.mod.character.marisa.patches.AbstractCardEnum;
import sts.touhouspire.mod.character.marisa.powers.Marisa.EnergyFlowPower;

public class EnergyFlow extends MarisaCard {

	public static final String ID = Identifiers.Cards.ENERGY_FLOW;
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String IMG_PATH = "marisa/img/cards/EneFlow.png";
	private static final int COST = 1;
	private static final int STC = 2;
	private static final int UPG_STC = 1;

	public EnergyFlow() {
		super(
				ID,
				NAME,
				IMG_PATH,
				COST,
				DESCRIPTION,
				AbstractCard.CardType.POWER,
				AbstractCardEnum.MARISA_COLOR,
				AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.SELF
		);

		this.magicNumber = this.baseMagicNumber = STC;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
				new ApplyPowerAction(
						p,
						p,
						new EnergyFlowPower(p, this.magicNumber),
						this.magicNumber
				)
		);
	}

	public AbstractCard makeCopy() {
		return new EnergyFlow();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeBaseCost(1);
			upgradeMagicNumber(UPG_STC);
		}
	}
}