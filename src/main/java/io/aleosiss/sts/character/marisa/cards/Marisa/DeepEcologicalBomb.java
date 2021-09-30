package io.aleosiss.sts.character.marisa.cards.Marisa;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import io.aleosiss.sts.character.marisa.action.WasteBombAction;
import io.aleosiss.sts.character.marisa.data.Identifiers;
import io.aleosiss.sts.character.marisa.patches.AbstractCardEnum;
import io.aleosiss.sts.character.marisa.abstracts.MarisaCard;
import io.aleosiss.sts.character.marisa.utils.AmplifyUtils;

public class DeepEcologicalBomb extends MarisaCard {

	public static final String ID = Identifiers.Cards.DEEP_ECOLOGICAL_BOMB;
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String IMG_PATH = "marisa/img/cards/DeepEcoBomb.png";
	private static final int TEMPORARY_STRENGTH_DOWN = 2;
	private static final int UPGRADE_STRENGTH_DOWN = 1;
	private static final int COST = 1;
	private static final int BASE_DAMAGE = 7;
	private static final int UPGRADE_DAMAGE = 2;
	private static final int AMPLIFY_COST = 1;

	public DeepEcologicalBomb() {
		super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnum.MARISA_COLOR, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ALL_ENEMY
		);

		this.baseDamage = BASE_DAMAGE;
		this.magicNumber = this.baseMagicNumber = TEMPORARY_STRENGTH_DOWN;
	}

	@Override
	public void calculateCardDamage(AbstractMonster mo) {
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		int num = 1;

		if (AmplifyUtils.Amplified(this, AMPLIFY_COST)) {
			num++;
		}
		AbstractDungeon.actionManager.addToBottom(
				new WasteBombAction(
						AbstractDungeon.getMonsters().getRandomMonster(true),
						this.damage, num, this.magicNumber
				)
		);
	}

	public AbstractCard makeCopy() {
		return new DeepEcologicalBomb();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeDamage(UPGRADE_DAMAGE);
			upgradeMagicNumber(UPGRADE_STRENGTH_DOWN);
		}
	}
}