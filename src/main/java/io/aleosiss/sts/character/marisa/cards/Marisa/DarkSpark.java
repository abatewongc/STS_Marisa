package io.aleosiss.sts.character.marisa.cards.Marisa;

import static io.aleosiss.sts.character.marisa.patches.CardTagEnum.SPARK;

import io.aleosiss.sts.character.marisa.action.DarkSparkAction;
import io.aleosiss.sts.character.marisa.data.Identifiers;
import io.aleosiss.sts.character.marisa.patches.AbstractCardEnum;
import io.aleosiss.sts.character.marisa.abstracts.MarisaCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DarkSpark extends MarisaCard {

	public static final String ID = Identifiers.Cards.DARK_SPARK;
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String IMG_PATH = "marisa/img/cards/darkSpark.png";

	private static final int COST = 2;
	private static final int ATK_DMG = 7;
	//private static final int UPG_DMG = 3;
	private static final int EXHAUST_COUNT = 5;
	private static final int COUNT_UPG = 3;

	public DarkSpark() {
		super(
				ID,
				NAME,
				IMG_PATH,
				COST,
				DESCRIPTION,
				CardType.ATTACK,
				AbstractCardEnum.MARISA_COLOR,
				CardRarity.UNCOMMON,
				CardTarget.ALL_ENEMY
		);

		this.tags.add(SPARK);
		this.baseDamage = ATK_DMG;
		this.isMultiDamage = true;
		this.magicNumber = this.baseMagicNumber = EXHAUST_COUNT;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
    /*
    AbstractDungeon.actionManager.addToBottom(
        new ExhaustAllNonAttackAction()
    );
    */
    /*
    AbstractDungeon.actionManager.addToBottom(
        new DamageAllEnemiesAction(
            p,
            this.multiDamage,
            this.damageTypeForTurn,
            AbstractGameAction.AttackEffect.NONE
        )
    );
    AbstractDungeon.actionManager.addToBottom(
        new DamageAllEnemiesAction(
            p,
            this.multiDamage,
            this.damageTypeForTurn,
            AbstractGameAction.AttackEffect.NONE
        )
    );*/
		for (int i = 0; i < this.magicNumber; i++) {
			addToBot(new DarkSparkAction(this.multiDamage, this.damageType));
		}
	}

	public AbstractCard makeCopy() {
		return new DarkSpark();
	}

	public void upgrade() {
		//upgradeDamage(UPG_DMG);
		upgradeName();
		upgradeMagicNumber(COUNT_UPG);
		this.upgraded = true;
		initializeDescription();
	}
}