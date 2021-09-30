package io.aleosiss.sts.character.marisa.cards.Marisa;

import static io.aleosiss.sts.character.marisa.patches.CardTagEnum.SPARK;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import io.aleosiss.sts.character.marisa.data.Identifiers;
import io.aleosiss.sts.character.marisa.patches.AbstractCardEnum;
import io.aleosiss.sts.character.marisa.abstracts.MarisaCard;

public class Strike_MRS extends MarisaCard {

	public static final String ID = Identifiers.Cards.STRIKE;
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String IMG_PATH = "marisa/img/cards/SimpleSpark.png";
	private static final int COST = 1;
	private static final int ATTACK_DMG = 6;
	private static final int UPGRADE_PLUS_DMG = 3;

	public Strike_MRS() {
		super(
				ID,
				NAME,
				IMG_PATH,
				COST,
				DESCRIPTION,
				CardType.ATTACK,
				AbstractCardEnum.MARISA_COLOR,
				CardRarity.BASIC,
				CardTarget.ENEMY
		);
		//this.tags.add(BaseModCardTags.BASIC_STRIKE);
		this.tags.add(AbstractCard.CardTags.STARTER_STRIKE);
		this.tags.add(SPARK);
		this.baseDamage = ATTACK_DMG;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
				new DamageAction(
						m,
						new DamageInfo(p, this.damage, this.damageTypeForTurn),
						AbstractGameAction.AttackEffect.SLASH_DIAGONAL
				)
		);
	}

	public AbstractCard makeCopy() {
		return new Strike_MRS();
	}

	@Override
	public boolean isStrike() {
		return true;
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeDamage(UPGRADE_PLUS_DMG);
		}
	}
}