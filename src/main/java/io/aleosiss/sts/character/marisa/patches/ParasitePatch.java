package io.aleosiss.sts.character.marisa.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import io.aleosiss.sts.character.marisa.data.Identifiers;
import io.aleosiss.sts.character.marisa.relics.BigShroomBag;
import io.aleosiss.sts.character.marisa.relics.intr.ShroomBagInterface;

public class ParasitePatch {
	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.curses.Parasite", method = "use")
	public static class ParasiteUse {
		@SuppressWarnings("rawtypes")
		@SpirePrefixPatch
		public static SpireReturn Prefix(
				AbstractCard _inst,
				AbstractPlayer player,
				AbstractMonster monster
		) {
			if ((AbstractDungeon.player.hasRelic(Identifiers.Relics.SHROOM_BAG)) || (AbstractDungeon.player.hasRelic(Identifiers.Relics.BIG_SHROOM_BAG))) {
				AbstractRelic shroomBagRelic;
				int parasiteHeal;
				int parasiteDraw;
				if (AbstractDungeon.player.hasRelic(Identifiers.Relics.BIG_SHROOM_BAG)) {
					shroomBagRelic = player.getRelic(Identifiers.Relics.BIG_SHROOM_BAG);
					parasiteHeal = ((ShroomBagInterface) shroomBagRelic).healingPerParasite();
					parasiteDraw = ((ShroomBagInterface) shroomBagRelic).drawPerParasite();
				} else {
					shroomBagRelic = player.getRelic(Identifiers.Relics.SHROOM_BAG);
					parasiteHeal = ((ShroomBagInterface) shroomBagRelic).healingPerParasite();
					parasiteDraw = ((ShroomBagInterface) shroomBagRelic).drawPerParasite();
				}

				shroomBagRelic.flash();
				AbstractDungeon.actionManager.addToBottom(
						new RelicAboveCreatureAction(player, shroomBagRelic)
				);
				_inst.exhaust = true;
				AbstractDungeon.actionManager.addToBottom(
						new HealAction(player, player, parasiteHeal)
				);
				AbstractDungeon.actionManager.addToBottom(
						new DrawCardAction(player, parasiteDraw)
				);
				return SpireReturn.Return(null);
			}
			return SpireReturn.Continue();
		}
	}


	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "canUse")
	public static class ParasiteCanUse {

		@SpirePrefixPatch
		public static SpireReturn<Boolean> Prefix(
				AbstractCard _inst,
				AbstractPlayer p,
				AbstractMonster m
		) {
			if ((_inst.cardID.equals("Parasite")) && ((p.hasRelic("ShroomBag")) || (p.hasRelic("BigShroomBag")))) {
				return SpireReturn.Return(true);
			}
			return SpireReturn.Continue();
		}
	}
}
