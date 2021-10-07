package sts.touhouspire.mod.character.marisa.cards.Marisa;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.touhouspire.mod.character.marisa.abstracts.AmplifiedAttack;
import sts.touhouspire.mod.character.marisa.action.RefractionSparkAction;
import sts.touhouspire.mod.character.marisa.data.Identifiers;
import sts.touhouspire.mod.character.marisa.patches.AbstractCardEnum;
import sts.touhouspire.mod.character.marisa.utils.AmplifyUtils;
import sts.touhouspire.mod.character.marisa.patches.CardTagEnum;

public class RefractionSpark extends AmplifiedAttack {

	public static final String ID = Identifiers.Cards.REFRACTION_SPARK;
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String IMG_PATH = "marisa/img/cards/Refraction.png";

	private static final int COST = 1;
	private static final int ATK_DMG = 4;
	private static final int UPG_DMG = 1;
	private static final int AMP_DMG = 3;
	private static final int UPG_AMP = 1;
	private static final int AMP = 1;


	public RefractionSpark() {
		super(
				ID,
				NAME,
				IMG_PATH,
				COST,
				DESCRIPTION,
				AbstractCard.CardType.ATTACK,
				AbstractCardEnum.MARISA_COLOR,
				AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.ENEMY
		);

		this.baseDamage = ATK_DMG;
		this.ampNumber = AMP_DMG;
		this.baseBlock = this.baseDamage + this.ampNumber;
		this.tags.add(CardTagEnum.SPARK);
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		if (AmplifyUtils.Amplified(this, AMP)) {
			AbstractDungeon.actionManager.addToBottom(
					new RefractionSparkAction(
							m,
							new DamageInfo(p, this.block, this.damageTypeForTurn)
					)
			);
		} else {
			AbstractDungeon.actionManager.addToBottom(
					new RefractionSparkAction(
							m,
							new DamageInfo(p, this.damage, this.damageTypeForTurn)
					)
			);
		}
	}

	public AbstractCard makeCopy() {
		return new RefractionSpark();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeDamage(UPG_DMG);
			this.ampNumber += UPG_AMP;
			this.isBlockModified = true;
			this.block = this.baseDamage + this.ampNumber;
		}
	}
}