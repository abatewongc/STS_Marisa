package sts.touhouspire.mod.character.marisa.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import sts.touhouspire.mod.character.marisa.cards.derivations.BlackFlareStar;
import sts.touhouspire.mod.character.marisa.cards.derivations.WhiteDwarfStar;

public class BinaryStarsAction extends AbstractGameAction {
	private final AbstractPlayer player;
	private final boolean upgrade;

	public BinaryStarsAction(boolean upgraded) {
		this.player = AbstractDungeon.player;
		setValues(this.player, AbstractDungeon.player, 1);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.upgrade = upgraded;
	}

	public void update() {
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED) {
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			AbstractCard card = new BlackFlareStar();
			if (this.upgrade)
				card.upgrade();
			tmp.addToTop(card);
			card = new WhiteDwarfStar();
			if (this.upgrade)
				card.upgrade();
			tmp.addToTop(card);
			AbstractDungeon.gridSelectScreen.open(tmp, 1, "Choose", false);
			tickDuration();
			return;
		}
		if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
			for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
				c.unhover();
				if (this.player.hand.size() == 10) {
					this.player.createHandIsFullDialog();
					this.player.discardPile.addToTop(c);
				} else {
					this.player.hand.addToTop(c);
				}
				this.player.hand.refreshHandLayout();
				this.player.hand.applyPowers();
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.player.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
