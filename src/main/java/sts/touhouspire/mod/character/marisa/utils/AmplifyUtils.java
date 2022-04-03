package sts.touhouspire.mod.character.marisa.utils;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import sts.touhouspire.mod.character.marisa.MarisaModHandler;
import sts.touhouspire.mod.character.marisa.abstracts.OnAmplifySubscriber;
import sts.touhouspire.mod.character.marisa.data.Identifiers;
import sts.touhouspire.mod.character.marisa.powers.Marisa.GrandCrossPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class AmplifyUtils {
	public static final Logger logger = LogManager.getLogger(AmplifyUtils.class.getName());

	public static boolean Amplified(AbstractCard card, int amplifyCost) {
		logger.info("Attempting to amplify card: " + card.cardID + "; costForTurn: " + card.costForTurn);
		AbstractPlayer player = AbstractDungeon.player;

		AmplifyStatus amplifyStatus = canAmplify(card, amplifyCost, player);
		if (amplifyStatus.isAmplified) {
			amplify(card, amplifyStatus.isFree, amplifyCost, player);
		}

		logger.info("Amplifying Card: " + card.cardID + " ; Amplify : " + amplifyStatus.isAmplified + " ; costForTurn : " + card.costForTurn);
		return amplifyStatus.isAmplified;
	}

	private static class AmplifyStatus {
		protected boolean isAmplified;
		protected boolean isFree;

		private AmplifyStatus(boolean isAmplified, boolean isFree) {
			this.isAmplified = isAmplified;
			this.isFree = isFree;
		}

		private AmplifyStatus() {}
	}

	private static AmplifyStatus canAmplify(AbstractCard card, int amplifyCost, AbstractPlayer player) {
		AmplifyStatus status = new AmplifyStatus();
		if (amplifyBlocked(player)) {
			return status;
		}

		if (canAmplifyForFree(card, player)) {
			status.isAmplified = true;
			status.isFree = true;
		} else {
			if (EnergyPanel.totalCount >= (card.costForTurn + amplifyCost)) {
				status.isAmplified = true;
			}
		}
		return status;
	}

	public static boolean amplifyBlocked(AbstractPlayer player) {
		boolean blocked;
		if (player.hasPower(Identifiers.Powers.ONE_TIME_OFF)) {
			logger.info("OneTimeOff detected, blocking amplify.");
			return true;
		}

		return false;
	}

	private static void amplify(AbstractCard card, boolean amplifyIsFree, int amplifyCost, AbstractPlayer player) {
		if(!amplifyIsFree) {
			logger.info("Sufficient energy, adding and returning true;");
			card.costForTurn += amplifyCost;
		}

		if (card.costForTurn > 0) {
			logger.info("False instance of 0 cost card, decreasing typhoon counter.");
			MarisaModHandler.typhoonCounter--;
			logger.info("Current Typhoon counter : " + MarisaModHandler.typhoonCounter);
		}

		amplifyEventActivated(player);
	}

	private static void amplifyEventActivated(AbstractPlayer player) {
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(player, player, new GrandCrossPower(player)));

		checkAmplifySubscriptions(player.powers);
		checkAmplifySubscriptions(player.relics);
		checkAmplifySubscriptions(player.hand.group);
		checkAmplifySubscriptions(player.discardPile.group);
		checkAmplifySubscriptions(player.drawPile.group);
		checkAmplifySubscriptions(player.exhaustPile.group);
	}

	private static void checkAmplifySubscriptions(ArrayList<?> subscribers) {
		subscribers.stream()
				.filter(obj -> obj instanceof OnAmplifySubscriber)
				.map(obj -> (OnAmplifySubscriber)obj)
				.forEach(OnAmplifySubscriber::amplify);
	}

	private static boolean canAmplifyForFree(AbstractCard card, AbstractPlayer player) {
		return (player.hasPower(Identifiers.Powers.MILLISECOND_PULSARS))
				|| (player.hasPower(Identifiers.Powers.PULSE_MAGIC))
				|| (card.freeToPlayOnce)
				|| (card.purgeOnUse);
	}
}
