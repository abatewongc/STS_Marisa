package io.aleosiss.sts.character.marisa.cards.Marisa;

import io.aleosiss.sts.character.marisa.MarisaModHandler;
import io.aleosiss.sts.character.marisa.action.BlazeAwayAction;
import io.aleosiss.sts.character.marisa.data.Identifiers;
import io.aleosiss.sts.character.marisa.patches.AbstractCardEnum;
import io.aleosiss.sts.character.marisa.abstracts.MarisaCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BlazeAway extends MarisaCard {

	public static final String ID = Identifiers.Cards.BLAZE_AWAY;
	public static final String IMG_PATH = "marisa/img/cards/blazeAway.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION;
	public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	private static final int COST = 1;
	private static final int NUM = 1;
	private static final int UPG_NUM = 1;
	//private static final int AMP = 1;
	private static AbstractCard lastAttack = null;

	public BlazeAway() {
		super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.MARISA_COLOR, CardRarity.UNCOMMON, CardTarget.SELF);
		this.baseMagicNumber = this.magicNumber = NUM;
	}

	@Override
	public void applyPowers() {
		lastAttack = null;
		for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
			if (c.type == CardType.ATTACK) {
				lastAttack = c;
			}
		}
		if (lastAttack == null) {
			this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[2];
			initializeDescription();
		} else {
			this.rawDescription =
					DESCRIPTION + EXTENDED_DESCRIPTION[0] + lastAttack.name + EXTENDED_DESCRIPTION[1];
			initializeDescription();
		}
	}

	public void onMoveToDiscard() {
		this.rawDescription = DESCRIPTION;
		initializeDescription();
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		if (lastAttack != null) {
			MarisaModHandler.logger.info("BlazeAway : last attack :" + lastAttack.cardID);
			AbstractCard card = lastAttack.makeStatEquivalentCopy();
			if (card.costForTurn >= 0) {
				card.setCostForTurn(0);
			}
			MarisaModHandler.logger.info(
					"BlazeAway : card :" + card.cardID
							+ " ; baseD :" + card.baseDamage
							+ " ; D : " + card.damage
							+ " ; baseB :" + card.baseBlock
							+ " ; B : " + card.block
							+ " ; baseM :" + card.baseMagicNumber
							+ " ; M : " + card.magicNumber
							+ " ; C : " + card.cost
							+ " ; CFT : " + card.costForTurn
			);
			for (int i = 0; i < this.magicNumber; i++) {
				AbstractDungeon.actionManager.addToBottom(new BlazeAwayAction(card));
			}

		} else {
			MarisaModHandler.logger.info("BlazeAway : error : last attack is null ");
		}

	}

	public AbstractCard makeCopy() {
		return new BlazeAway();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeMagicNumber(UPG_NUM);
			upgradeName();
			//upgradeBaseCost(0);
			//this.rawDescription = DESCRIPTION_UPG;
			//initializeDescription();
			//this.exhaust = false;
		}
	}
}