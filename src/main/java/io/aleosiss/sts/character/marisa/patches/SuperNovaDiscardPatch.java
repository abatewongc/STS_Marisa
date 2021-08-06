package io.aleosiss.sts.character.marisa.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import io.aleosiss.sts.character.marisa.MarisaModHandler;

public class SuperNovaDiscardPatch {

	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.status.Burn", method = "triggerOnEndOfTurnForPlayingCard")
	public static class DisableBurn_PreFix {

		@SuppressWarnings("rawtypes")
		@SpirePrefixPatch
		public static SpireReturn Prefix(Object _obj_instance) {
			if (AbstractDungeon.player.hasPower("SuperNovaPower")) {
				MarisaModHandler.logger.info("SuperNovaPatch : Burn detected.");
				return SpireReturn.Return(null);
			}
			return SpireReturn.Continue();
		}
	}
}
