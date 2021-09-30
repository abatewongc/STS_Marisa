package io.aleosiss.sts.character.marisa.cards.Marisa;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import io.aleosiss.sts.character.marisa.action.OrbitalAction;
import io.aleosiss.sts.character.marisa.data.Identifiers;
import io.aleosiss.sts.character.marisa.patches.AbstractCardEnum;
import io.aleosiss.sts.character.marisa.abstracts.MarisaCard;

public class Orbital extends MarisaCard {

	public static final String ID = Identifiers.Cards.ORBITAL;
	public static final String IMG_PATH = "marisa/img/cards/Marisa/orbit.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = -2;
	private static final int UPG_DRAW = 1;
	private static final int DRAW = 1;

	public Orbital() {
		super(
				ID,
				NAME,
				IMG_PATH,
				COST,
				DESCRIPTION,
				AbstractCard.CardType.SKILL,
				AbstractCardEnum.MARISA_COLOR,
				AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.SELF
		);
		this.magicNumber = this.baseMagicNumber = DRAW;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(UPG_DRAW);
		}
	}

	public boolean canUse(AbstractPlayer p, AbstractMonster m) {
		return false;
	}

	public void triggerOnExhaust() {
		AbstractDungeon.actionManager.addToBottom(
				new OrbitalAction()
		);
		if (this.upgraded) {
			AbstractDungeon.actionManager.addToBottom(
					new OrbitalAction()
			);
		}
	}

	public void triggerWhenDrawn() {
		AbstractDungeon.actionManager.addToBottom(
				new DrawCardAction(AbstractDungeon.player, DRAW)
		);
	}

	@Override
	public void use(AbstractPlayer arg0, AbstractMonster arg1) {
	}
}
