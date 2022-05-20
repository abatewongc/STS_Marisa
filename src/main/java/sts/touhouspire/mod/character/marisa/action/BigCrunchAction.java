package sts.touhouspire.mod.character.marisa.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import sts.touhouspire.mod.character.marisa.MarisaModHandler;

public class BigCrunchAction extends AbstractGameAction {
	private boolean upg = false;

	public BigCrunchAction(boolean upgraded) {
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_FAST;
		this.upg = upgraded;
	}

	public void update() {
		this.isDone = false;

		AbstractPlayer player = AbstractDungeon.player;
		int div = 5;
		if (this.upg) {
			div--;
		}
		int cnt = player.discardPile.size() / 2;
		int total = cnt;
		player.discardPile.shuffle();

		MarisaModHandler.logger.info(
				"BigCrunchAction : Discard size : " + player.discardPile.size()
						+ " ; counter : " + cnt
		);

		if (cnt > 0) {
			for (int i = 0; i < cnt; i++) {
				player.discardPile.moveToExhaustPile(player.discardPile.getTopCard());
			}
		}

		player.drawPile.shuffle();

		cnt = player.drawPile.size() / 2;

		MarisaModHandler.logger.info(
				"BigCrunchAction : Draw size : " + player.drawPile.size()
						+ " ; counter : " + cnt
		);

		total += cnt;

		if (cnt > 0) {
			for (int i = 0; i < cnt; i++) {
				player.drawPile.moveToExhaustPile(player.drawPile.getTopCard());
			}
		}

		int res = total / div;

		MarisaModHandler.logger.info(
				"BigCrunchAction : total :" + total
						+ " ; res : " + res
		);

		if (res > 0) {
			AbstractDungeon.actionManager.addToBottom(
					new GainEnergyAction(res)
			);
			AbstractDungeon.actionManager.addToBottom(
					new DrawCardAction(source, res)
			);
		}

		this.isDone = true;
	}
}