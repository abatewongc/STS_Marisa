package io.aleosiss.sts.character.marisa.cards.Marisa;

import io.aleosiss.sts.character.marisa.data.Identifiers;
import io.aleosiss.sts.character.marisa.powers.Marisa.OneTimeOffPlusPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import io.aleosiss.sts.character.marisa.abstracts.MarisaCard;
import io.aleosiss.sts.character.marisa.patches.AbstractCardEnum;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

public class OneTimeOff extends MarisaCard {

	public static final String ID = Identifiers.Cards.ONE_TIME_OFF;
	public static final String IMG_PATH = "marisa/img/cards/MoraleDelpletion.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 1;
	private static final int BLOCK_AMT = 5;
	private static final int UPGRADE_PLUS_BLOCK = 2;
	private static final int DRAW = 1;
	private static final int UPGRADE_PLUS_DRAW = 1;

	public OneTimeOff() {
		super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.MARISA_COLOR, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
		this.baseBlock = BLOCK_AMT;
		this.magicNumber = this.baseMagicNumber = DRAW;
	}

	public void use(AbstractPlayer player, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
				new GainBlockAction(player, player, this.block)
		);
		AbstractDungeon.actionManager.addToBottom(
				new ApplyPowerAction(
						player,
						player,
						new DrawCardNextTurnPower(player, this.magicNumber),
						this.magicNumber
				)
		);
		AbstractDungeon.actionManager.addToBottom(
				new ApplyPowerAction(
						player,
						player,
						new OneTimeOffPlusPower(player)
				)
		);
	}

	public AbstractCard makeCopy() {
		return new OneTimeOff();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeBlock(UPGRADE_PLUS_BLOCK);
			upgradeMagicNumber(UPGRADE_PLUS_DRAW);
			this.rawDescription = DESCRIPTION_UPG;
			initializeDescription();
		}
	}
}