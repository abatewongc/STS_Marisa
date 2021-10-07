package sts.touhouspire.mod.character.marisa.cards.derivations;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.touhouspire.mod.character.marisa.action.BlackFlareStarAction;
import sts.touhouspire.mod.character.marisa.data.Identifiers;
import sts.touhouspire.mod.character.marisa.abstracts.MarisaCard;

public class BlackFlareStar extends MarisaCard {

	public static final String ID = Identifiers.Cards.BLACK_FLARE_STAR;
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	public static final String IMG_PATH = "marisa/img/cards/Marisa/BlackFlareStar.png";
	private static final int COST = 0;
	private static final int BLOCK_AMOUNT = 4;
	private static final int BLOCK_UPGRADE = 2;
	private static final int HAND_REQ = 4;

	public BlackFlareStar() {
		super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.SKILL, CardColor.COLORLESS, AbstractCard.CardRarity.SPECIAL, AbstractCard.CardTarget.SELF);
		this.baseBlock = BLOCK_AMOUNT;
		this.exhaust = true;
	}

	public boolean canUse(AbstractPlayer p, AbstractMonster m) {
		if (p.hand.size() >= HAND_REQ) {
			return true;
		} else {
			this.cantUseMessage = EXTENDED_DESCRIPTION[0];
			return false;
		}
	}

	public void use(AbstractPlayer p, AbstractMonster m) {

		AbstractDungeon.actionManager.addToBottom(
				new BlackFlareStarAction(this.block)
		);
	}

	public AbstractCard makeCopy() {
		return new BlackFlareStar();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeBlock(BLOCK_UPGRADE);
		}
	}
}