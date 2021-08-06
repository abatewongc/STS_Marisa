package io.aleosiss.sts.character.marisa.cards.Marisa;

import io.aleosiss.sts.character.marisa.action.BinaryStarsAction;
import io.aleosiss.sts.character.marisa.cards.derivations.BlackFlareStar;
import io.aleosiss.sts.character.marisa.cards.derivations.WhiteDwarf;
import io.aleosiss.sts.character.marisa.data.Identifiers;
import io.aleosiss.sts.character.marisa.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import io.aleosiss.sts.character.marisa.utils.AmplifyUtils;

public class BinaryStars extends CustomCard {

	public static final String ID = Identifiers.Cards.BINARY_STARS;
	public static final String IMG_PATH = "marisa/img/cards/binaryStar.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 1;
	private static final int AMP = 1;

	public BinaryStars() {
		super(
				ID,
				NAME,
				IMG_PATH,
				COST,
				DESCRIPTION,
				AbstractCard.CardType.SKILL,
				AbstractCardEnum.MARISA_COLOR,
				AbstractCard.CardRarity.RARE,
				AbstractCard.CardTarget.SELF
		);
		//this.exhaust = true;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		if (AmplifyUtils.Amplified(this, AMP)) {
			AbstractCard c = new BlackFlareStar();
			if (this.upgraded) {
				c.upgrade();
			}
			AbstractDungeon.actionManager.addToBottom(
					new MakeTempCardInHandAction(c, 1)
			);
			c = new WhiteDwarf();
			if (this.upgraded) {
				c.upgrade();
			}
			AbstractDungeon.actionManager.addToBottom(
					new MakeTempCardInHandAction(c, 1)
			);
		} else {
			AbstractDungeon.actionManager.addToBottom(
					new BinaryStarsAction(this.upgraded)
			);
		}

	}

	public AbstractCard makeCopy() {
		return new BinaryStars();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			initializeDescription();
		}
	}
}