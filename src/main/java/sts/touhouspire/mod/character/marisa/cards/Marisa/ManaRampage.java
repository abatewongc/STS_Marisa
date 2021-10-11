package sts.touhouspire.mod.character.marisa.cards.Marisa;

import sts.touhouspire.mod.character.marisa.action.ManaRampageAction;
import sts.touhouspire.mod.character.marisa.data.Identifiers;
import sts.touhouspire.mod.character.marisa.patches.AbstractCardEnum;
import sts.touhouspire.mod.character.marisa.abstracts.MarisaCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class ManaRampage extends MarisaCard {

	public static final String ID = Identifiers.Cards.MANA_RAMPAGE;
	public static final String IMG_PATH = "marisa/img/cards/ManaRampage.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = -1;
	private static final int DMG_UP = 2;
	private static final int DMG_UP_PLUS = 1;

	public ManaRampage() {
		super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.MARISA_COLOR, CardRarity.RARE, CardTarget.ALL_ENEMY);
		this.magicNumber = this.baseMagicNumber = DMG_UP;
	}

	public void use(AbstractPlayer power, AbstractMonster m) {
		int cnt = EnergyPanel.totalCount;
		if (power.hasRelic(Identifiers.Relics.CHEMICAL_X)) {
			cnt += 2;
		}

		if (cnt > 0) {
			AbstractDungeon.actionManager.addToBottom(
					new ManaRampageAction(cnt, this.upgraded, this.freeToPlayOnce)
			);
		}
	/*
    if (!this.freeToPlayOnce) {
      p.energy.use(EnergyPanel.totalCount);
    }
    */
	}

	public AbstractCard makeCopy() {
		return new ManaRampage();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(DMG_UP_PLUS);
			this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			initializeDescription();
		}
	}
}