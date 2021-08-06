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
import io.aleosiss.sts.character.marisa.powers.Marisa.SatelliteIllusionPower;
import basemod.abstracts.CustomCard;

public class SatelliteIllusion extends CustomCard {

	public static final String ID = Identifiers.Cards.SATELLITE_ILLUSION;
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION;
	public static final String IMG_PATH = "marisa/img/cards/SatelliteIllusion.png";
	private static final int COST = 2;

	public SatelliteIllusion() {
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
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
				new ApplyPowerAction(
						p,
						p,
						new SatelliteIllusionPower(p, 1),
						1)
		);
	}

	public AbstractCard makeCopy() {
		return new SatelliteIllusion();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.isInnate = true;
			this.rawDescription = DESCRIPTION_UPG;
			initializeDescription();
		}
	}
}