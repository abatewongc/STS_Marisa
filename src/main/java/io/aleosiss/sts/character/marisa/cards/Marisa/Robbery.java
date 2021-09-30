package io.aleosiss.sts.character.marisa.cards.Marisa;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import io.aleosiss.sts.character.marisa.action.RobberyDamageAction;
import io.aleosiss.sts.character.marisa.data.Identifiers;
import io.aleosiss.sts.character.marisa.patches.AbstractCardEnum;
import io.aleosiss.sts.character.marisa.abstracts.MarisaCard;
import io.aleosiss.sts.character.marisa.utils.AmplifyUtils;

public class Robbery extends MarisaCard {

	public static final String ID = Identifiers.Cards.ROBBERY;
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String IMG_PATH = "marisa/img/cards/rob.png";
	private static final int COST = 1;
	private static final int ATTACK_DMG = 7;
	private static final int UPGRADE_PLUS_DMG = 3;
	private static final int AMP = 1;

	public Robbery() {
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

		this.baseDamage = ATTACK_DMG;
		this.exhaust = true;
		this.tags.add(AbstractCard.CardTags.HEALING);
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
				new RobberyDamageAction(
						m,
						new DamageInfo(p, this.damage, this.damageTypeForTurn),
						AmplifyUtils.Amplified(this, AMP)
				)
		);
	}

	public AbstractCard makeCopy() {
		return new Robbery();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeDamage(UPGRADE_PLUS_DMG);
		}
	}
}