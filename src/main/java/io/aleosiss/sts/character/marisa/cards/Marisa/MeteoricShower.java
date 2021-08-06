package io.aleosiss.sts.character.marisa.cards.Marisa;

import io.aleosiss.sts.character.marisa.action.MeteoricShowerAction;
import io.aleosiss.sts.character.marisa.data.Identifiers;
import io.aleosiss.sts.character.marisa.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class MeteoricShower extends CustomCard {

	public static final String ID = Identifiers.Cards.METEORIC_SHOWER;
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String IMG_PATH = "marisa/img/cards/meteonic.png";

	private static final int COST = -1;
	private static final int ATK_DMG = 3;
	private static final int UPG_DMG = 1;


	public MeteoricShower() {
		super(
				ID,
				NAME,
				IMG_PATH,
				COST,
				DESCRIPTION,
				AbstractCard.CardType.ATTACK,
				AbstractCardEnum.MARISA_COLOR,
				AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.ALL_ENEMY
		);

		this.baseDamage = ATK_DMG;
	}

	@Override
	public void calculateCardDamage(AbstractMonster mo) {
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		int cnt = EnergyPanel.totalCount + 1;
		if (p.hasRelic("Chemical X")) {
			cnt += 2;
		}

		AbstractDungeon.actionManager.addToBottom(
				new MeteoricShowerAction(cnt, this.damage, this.freeToPlayOnce)
		);
/*
    if (!this.freeToPlayOnce) {
      p.energy.use(EnergyPanel.totalCount);
    }
    */
	}

	public AbstractCard makeCopy() {
		return new MeteoricShower();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeDamage(UPG_DMG);
		}
	}
}