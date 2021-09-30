package io.aleosiss.sts.character.marisa.cards.Marisa;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import io.aleosiss.sts.character.marisa.data.Identifiers;
import io.aleosiss.sts.character.marisa.patches.AbstractCardEnum;
import io.aleosiss.sts.character.marisa.powers.Marisa.SupernovaPower;
import io.aleosiss.sts.character.marisa.abstracts.MarisaCard;

public class Supernova extends MarisaCard {

	public static final String ID = Identifiers.Cards.SUPERNOVA;
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION;
	public static final String IMG_PATH = "marisa/img/cards/SuperNova.png";
	private static final int COST = 2;
	private static final int STACK = 1;
	private static final int STACK_UPG = 1;


	public Supernova() {
		super(
				ID,
				NAME,
				IMG_PATH,
				COST,
				DESCRIPTION,
				AbstractCard.CardType.POWER,
				AbstractCardEnum.MARISA_COLOR,
				AbstractCard.CardRarity.RARE,
				AbstractCard.CardTarget.SELF
		);
		//this.tags.add(BaseModCardTags.FORM);
		this.magicNumber = this.baseMagicNumber = STACK;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
    /*
    if ((this.upgraded) && (p.hasPower("SuperNovaPower"))) {
      SuperNovaPower po = (SuperNovaPower) p.getPower("SuperNovaPower");
      po.upgraded = true;
    }
    */
		AbstractDungeon.actionManager.addToBottom(
				new ApplyPowerAction(
						p,
						p,
						//new SuperNovaPower(p, 1, this.upgraded),
						new SupernovaPower(p, this.magicNumber),
						this.magicNumber
				)
		);
	}

	public AbstractCard makeCopy() {
		return new Supernova();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			//upgradeBaseCost(2);
			upgradeMagicNumber(STACK_UPG);
			//this.isInnate = true;
			//this.rawDescription = DESCRIPTION_UPG;
			initializeDescription();
		}
	}
}