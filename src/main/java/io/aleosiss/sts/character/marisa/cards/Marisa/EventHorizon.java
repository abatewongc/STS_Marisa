package io.aleosiss.sts.character.marisa.cards.Marisa;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import io.aleosiss.sts.character.marisa.data.Identifiers;
import io.aleosiss.sts.character.marisa.patches.AbstractCardEnum;
import io.aleosiss.sts.character.marisa.powers.Marisa.EventHorizonPower;
import io.aleosiss.sts.character.marisa.abstracts.MarisaModCard;

public class EventHorizon extends MarisaModCard {
	public static final String ID = Identifiers.Cards.EVENT_HORIZON;
	public static final String IMG_PATH = "marisa/img/cards/EventHorizon.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int STC_GAIN = 1;
	private static final int UPG_STC = 1;

	public EventHorizon() {
		super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.POWER,
				AbstractCardEnum.MARISA_COLOR, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);

		this.magicNumber = this.baseMagicNumber = STC_GAIN;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {

		AbstractDungeon.actionManager.addToBottom(
				new ApplyPowerAction(p, p, new EventHorizonPower(p, this.magicNumber), this.magicNumber));
	}

	public AbstractCard makeCopy() {
		return new EventHorizon();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(UPG_STC);
		}
	}
}