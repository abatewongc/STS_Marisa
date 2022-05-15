package sts.touhouspire.mod.character.marisa.cards.Marisa;


import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.touhouspire.mod.character.marisa.action.FairyDestrucCullingAction;
import sts.touhouspire.mod.character.marisa.data.Identifiers;
import sts.touhouspire.mod.character.marisa.patches.AbstractCardEnum;
import sts.touhouspire.mod.character.marisa.abstracts.MarisaCard;
import sts.touhouspire.mod.character.marisa.utils.AmplifyUtils;

public class FairyDestructionRay extends MarisaCard {

	public static final String ID = Identifiers.Cards.FAIRY_DESTRUCTION_RAY;
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String IMG_PATH = "marisa/img/cards/FairysBane.png";
	private static final int COST = 0;
	private static final int AMP = 2;
	private static final int ATTACK_DMG = 5;
	private static final int UPGRADE_PLUS_DMG = 3;
	private static final int DIASPORA = 17;
	private static final int UPG_DIASPORA = 5;

	public FairyDestructionRay() {
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

		this.isMultiDamage = true;
		this.baseDamage = this.damage = ATTACK_DMG;
		this.magicNumber = this.baseMagicNumber = DIASPORA;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
				new DamageAllEnemiesAction(
						p,
						this.multiDamage,
						this.damageTypeForTurn,
						AttackEffect.SLASH_DIAGONAL
				)
		);

		if (!AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
			if (AmplifyUtils.Amplify(this, AMP)) {
				AbstractDungeon.actionManager.addToBottom(
						new FairyDestrucCullingAction(this.magicNumber)
				);
			}
		}
	}

	public AbstractCard makeCopy() {
		return new FairyDestructionRay();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeDamage(UPGRADE_PLUS_DMG);
			upgradeMagicNumber(UPG_DIASPORA);
		}
	}
}