package io.aleosiss.sts.character.marisa.cards.Marisa;

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
import io.aleosiss.sts.character.marisa.abstracts.MarisaModCard;

public class Surprise extends MarisaModCard {

	public static final String ID = Identifiers.Cards.SURPRISE;
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String IMG_PATH = "marisa/img/cards/Surprise.png";
	private static final int COST = 1;
	private static final int ATTACK_DMG = 8;
	private static final int UPGRADE_PLUS_DMG = 3;

	public Surprise() {
		super(
				ID,
				NAME,
				IMG_PATH,
				COST,
				DESCRIPTION,
				AbstractCard.CardType.ATTACK,
				AbstractCardEnum.MARISA_COLOR,
				AbstractCard.CardRarity.COMMON,
				AbstractCard.CardTarget.ENEMY
		);

		this.baseDamage = ATTACK_DMG;
		this.isInnate = true;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		if (p.discardPile.isEmpty()) {
			AbstractDungeon.actionManager.addToBottom(
					new DamageAction(
							m,
							new DamageInfo(p, this.damage * 2, this.damageTypeForTurn),
							AbstractGameAction.AttackEffect.SLASH_DIAGONAL
					)
			);
		} else {
			AbstractDungeon.actionManager.addToBottom(
					new DamageAction(
							m,
							new DamageInfo(p, this.damage, this.damageTypeForTurn),
							AbstractGameAction.AttackEffect.SLASH_DIAGONAL
					)
			);
		}
	}

	public AbstractCard makeCopy() {
		return new Surprise();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeDamage(UPGRADE_PLUS_DMG);
		}
	}
}