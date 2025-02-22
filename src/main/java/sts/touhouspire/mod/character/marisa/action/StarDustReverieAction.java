package sts.touhouspire.mod.character.marisa.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import sts.touhouspire.mod.character.marisa.MarisaModHandler;
import sts.touhouspire.mod.character.marisa.utils.MarisaHelpers;

public class StarDustReverieAction
		extends AbstractGameAction {

	private AbstractPlayer p;
	private boolean upgraded;

	public StarDustReverieAction(boolean upgraded) {
		this.duration = Settings.ACTION_DUR_FAST;
		this.upgraded = upgraded;
		this.p = AbstractDungeon.player;
	}

	public void update() {
		this.isDone = false;
		int cnt = 0;

		MarisaModHandler.logger.info("StarDustReverieAction : player hand size : " + p.hand.size());

		if (!p.hand.isEmpty()) {
			while (!p.hand.isEmpty()) {
				AbstractCard c = p.hand.getTopCard();
				MarisaModHandler.logger.info("StarDustReverieAction : moving " + c.cardID);
				p.hand.moveToDeck(c, true);
				cnt++;
				MarisaModHandler.logger.info("StarDustReverieAction : Counter : " + cnt);
			}
		} else {
			this.isDone = true;
			return;
		}

		p.drawPile.shuffle();

		for (AbstractRelic r : p.relics) {
			r.onShuffle();
		}

		for (int i = 0; i < cnt; i++) {

			AbstractCard c = MarisaHelpers.getRandomMarisaCard();

			MarisaModHandler.logger.info("StarDustReverieAction : adding " + c.cardID);

			if (this.upgraded) {
				c.upgrade();
			}
			MarisaModHandler.logger.info(
					"StarDustReverieAction : checking : Exhaust : " + c.exhaust +
							" ; Ethereal : " + c.isEthereal +
							" ; Upgraded : " + c.upgraded
			);
			AbstractDungeon.actionManager.addToBottom(
					new MakeTempCardInHandAction(c, 1)
			);
		}
		/*
	    AbstractDungeon.actionManager.addToBottom(
	    		new HandCheckAction(this.upgraded)
	    		);
	    */
		this.isDone = true;
	}
}