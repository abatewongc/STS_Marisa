package io.aleosiss.sts.character.marisa.utils;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import io.aleosiss.sts.character.marisa.MarisaModHandler;
import io.aleosiss.sts.character.marisa.data.Identifiers;
import io.aleosiss.sts.character.marisa.powers.Marisa.GrandCrossPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AmplifyUtils {
	public static final Logger logger = LogManager.getLogger(AmplifyUtils.class.getName());

	public static boolean Amplified(AbstractCard card, int amplifyCost) {
		logger.info("Marisa::Amplified: card to check : " + card.cardID + " ; costForTurn : " + card.costForTurn);
		AbstractPlayer player = AbstractDungeon.player;
		if ((player.hasPower(Identifiers.Powers.ONE_TIME_OFF))) {
			logger.info("Marisa::Amplified: OneTimeOff detected, returning false.");
			return false;
		}

		boolean amplifyActivated = false;
		if ((player.hasPower(Identifiers.Powers.MILLISECOND_PULSARS))
				|| (player.hasPower(Identifiers.Powers.PULSE_MAGIC))
				|| (card.freeToPlayOnce)
				|| (card.purgeOnUse)) {
			MarisaModHandler.logger.info(
					"Marisa::Amplified: Free Amplify tag detected, returning true : Milli :"
							+ (player.hasPower(Identifiers.Powers.MILLISECOND_PULSARS))
							+ " ; Pulse :"
							+ (player.hasPower(Identifiers.Powers.PULSE_MAGIC))
							+ " ; Free2Play :"
							+ card.freeToPlayOnce
							+ " ; purge on use :"
							+ card.purgeOnUse
			);
			amplifyActivated = true;
		} else {
			if (EnergyPanel.totalCount >= (card.costForTurn + amplifyCost)) {
				MarisaModHandler.logger.info("Marisa::Amplified: Sufficient energy, adding and returning true;");
				card.costForTurn += amplifyCost;
				amplifyActivated = true;
				if (card.costForTurn > 0) {
					MarisaModHandler.logger.info("Marisa::Amplified: False instance of 0 cost card, decreasing typhoon counter.");
					MarisaModHandler.typhoonCounter--;
					MarisaModHandler.logger.info("current Typhoon Counter : " + MarisaModHandler.typhoonCounter);
				}
			}
		}

		if (amplifyActivated) {
			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(player, player, new GrandCrossPower(player)));
			if (player.hasPower(Identifiers.Powers.EVENT_HORIZON)) {
				player.getPower(Identifiers.Powers.EVENT_HORIZON).onSpecificTrigger();
			}
			if (player.hasRelic(Identifiers.Relics.AMPLIFY_WAND)) {
				AbstractRelic amplifyWand = player.getRelic(Identifiers.Relics.AMPLIFY_WAND);
				amplifyWand.onTrigger();
			}
		}
		MarisaModHandler.logger.info("Marisa::Amplified: card : " + card.cardID + " ; Amplify : " + amplifyActivated + " ; costForTurn : " + card.costForTurn);
		return amplifyActivated;
	}
}
