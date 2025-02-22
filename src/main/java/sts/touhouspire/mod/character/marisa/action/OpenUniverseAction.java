package sts.touhouspire.mod.character.marisa.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import sts.touhouspire.mod.character.marisa.MarisaModHandler;
import sts.touhouspire.mod.character.marisa.utils.MarisaHelpers;

public class OpenUniverseAction
		extends AbstractGameAction {

	private AbstractPlayer p;
	private boolean upgraded = false;
	private int draw;

	public OpenUniverseAction(int draw, boolean upgraded) {
		this.duration = Settings.ACTION_DUR_FAST;
		this.upgraded = upgraded;
		this.p = AbstractDungeon.player;
		this.draw = draw;
	}

	public void update() {
		this.isDone = false;
		MarisaModHandler.logger.info("OpenUniverseAction : generating cards");

		for (int i = 0; i < 5; i++) {
			AbstractCard card = MarisaHelpers.getRandomMarisaCard();

			int rand = 20;
			if (this.upgraded) {
				rand += 10;
			}
			int res = AbstractDungeon.miscRng.random(0, 99);
			MarisaModHandler.logger.info("OpenUniverseAction : random res : " + res);
			if (res < rand) {
				MarisaModHandler.logger.info("OpenUniverseAction : Upgrading card ");
				card.upgrade();
			}
			MarisaModHandler.logger.info("OpenUniverseAction : adding : " + card.cardID);

			AbstractDungeon.actionManager.addToBottom(
					new MakeTempCardInDrawPileAction(card, 1, true, true)
			);
		}

		MarisaModHandler.logger.info("OpenUniverseAction : shuffling");

		p.drawPile.shuffle();
		for (AbstractRelic r : p.relics) {
			r.onShuffle();
		}

		MarisaModHandler.logger.info("OpenUniverseAction : drawing");

		AbstractDungeon.actionManager.addToBottom(
				new DrawCardAction(p, draw)
		);

		MarisaModHandler.logger.info("OpenUniverseAction : done");

		this.isDone = true;
	}
}