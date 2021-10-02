package io.aleosiss.sts.character.marisa.patches;

import static io.aleosiss.sts.character.marisa.MarisaModHandler.logger;

import com.megacrit.cardcrawl.random.Random;
import io.aleosiss.sts.character.marisa.MarisaModHandler;

import io.aleosiss.sts.character.marisa.characters.Marisa;
import io.aleosiss.sts.character.marisa.data.Identifiers;
import io.aleosiss.sts.character.marisa.event.Mushrooms_MRS;
import io.aleosiss.sts.character.marisa.event.OrinTheCat;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;

public class MarisaModEventPatch {

	@SpirePatch(clz = AbstractDungeon.class, method = "initializeCardPools")
	public static class EventPatch {
		@SpirePostfixPatch
		public static void EventListPatch(AbstractDungeon _exordium) {
			logger.info(
					"MarisaModEventPatch : EventListPatch :"
					      + " PlayerCharacter : "
					      + AbstractDungeon.player.title
			);
			debugEventList();

			if (AbstractDungeon.player instanceof Marisa) {
				logger.info("Marisa detected, removing standard Mushroom event.");
				AbstractDungeon.eventList.remove("Mushrooms");
			} else {
				logger.info("Marisa not detected, removing Marisa Mushroom event");
				AbstractDungeon.eventList.remove(Identifiers.Events.MUSHROOMS);
			}
			debugEventList();
		}
	}

	private static void debugEventList() {
		String events;
		events = "";
		for (String tempStr : AbstractDungeon.eventList) {
		  events += tempStr + " ; ";
		}
		logger.info("MarisaModEventPatch : current event list : " + events);
	}

	@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "getEvent")
	public static class GetEventPatch {
		@SpirePostfixPatch
		public static AbstractEvent GetEventPatch(AbstractEvent _retVal, Random rng) {
			logger.info(
				"MarisaModEventPatch : GetEventPatch :" +
					" PlayerCharacter  : " +
					AbstractDungeon.player.title +
					" ; retVal event : " +
					_retVal.toString()
			);
			/*
			if ((_retVal instanceof Mushrooms) && (AbstractDungeon.player instanceof Marisa)) {
				logger.info("Swapping mushroom event");
				return new Mushrooms_MRS();
			}
			return _retVal;
			*/
			if (((_retVal instanceof Mushrooms_MRS) && (AbstractDungeon.floorNum <= 6)) ||
			    ((_retVal instanceof OrinTheCat) && (AbstractDungeon.player.hasRelic(Identifiers.Relics.CAT_CART))) ||
			    ((_retVal instanceof OrinTheCat) && (!(AbstractDungeon.player instanceof Marisa)) && (!MarisaModHandler.isCatEventEnabled))
	        ) {
		        return AbstractDungeon.getEvent(AbstractDungeon.eventRng);
	        } else {
		        return _retVal;
	        }
        }
  }
}
