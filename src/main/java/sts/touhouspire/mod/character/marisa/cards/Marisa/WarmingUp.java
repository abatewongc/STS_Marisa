package sts.touhouspire.mod.character.marisa.cards.Marisa;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.touhouspire.mod.character.marisa.cards.derivations.Spark;
import sts.touhouspire.mod.character.marisa.data.Identifiers;
import sts.touhouspire.mod.character.marisa.patches.AbstractCardEnum;
import sts.touhouspire.mod.character.marisa.abstracts.MarisaCard;

public class WarmingUp extends MarisaCard {

	public static final String ID = Identifiers.Cards.WARMING_UP;
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION;
	public static final String IMG_PATH = "marisa/img/cards/WarmingUp.png";

	private static final int COST = 2;
	private static final int ATK_DMG = 1;
	//private static final int UPG_DMG = 2;

	public WarmingUp() {
		super(
				ID,
				NAME,
				IMG_PATH,
				COST,
				DESCRIPTION,
				AbstractCard.CardType.ATTACK,
				AbstractCardEnum.MARISA_COLOR,
				AbstractCard.CardRarity.RARE,
				AbstractCard.CardTarget.ENEMY
		);

		this.baseDamage = ATK_DMG;

	}

	public void use(AbstractPlayer p, AbstractMonster m) {

		AbstractDungeon.actionManager.addToBottom(
				new DamageAction(
						m,
						new DamageInfo(p, this.damage, this.damageTypeForTurn),
						AbstractGameAction.AttackEffect.SLASH_DIAGONAL
				)
		);

		AbstractCard c = new Upsweep();
		if (this.upgraded) {
			c.upgrade();
		}
		AbstractDungeon.actionManager.addToBottom(
				new MakeTempCardInHandAction(c, 1)
		);

		c = new Spark();
		if (this.upgraded) {
			c.upgrade();
		}
		AbstractDungeon.actionManager.addToBottom(
				new MakeTempCardInHandAction(c, 1)
		);

		c = new WitchLeyline();
		if (this.upgraded) {
			c.upgrade();
		}
		AbstractDungeon.actionManager.addToBottom(
				new MakeTempCardInHandAction(c, 1)
		);
	}

	public AbstractCard makeCopy() {
		return new WarmingUp();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			//upgradeDamage(UPG_DMG);
			this.rawDescription = DESCRIPTION_UPG;
			initializeDescription();
		}
	}
}