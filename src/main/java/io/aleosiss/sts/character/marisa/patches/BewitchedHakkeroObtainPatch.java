package io.aleosiss.sts.character.marisa.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import io.aleosiss.sts.character.marisa.data.Identifiers;

public class BewitchedHakkeroObtainPatch {
  @SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "returnRandomRelicKey")
  public static class ReturnRelicPatch {
    @SpirePostfixPatch
    public static String Postfix(String retVal, RelicTier tier) {
      if ((retVal.equals(Identifiers.Relics.BEWITCHED_HAKKERO)) && (!AbstractDungeon.player.hasRelic(Identifiers.Relics.MINI_HAKKERO))) {
        return AbstractDungeon.returnRandomRelicKey(tier);
      }
      return retVal;
    }
  }

  @SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "returnEndRandomRelicKey")
  public static class ReturnEndRelicPatch {
    @SpirePostfixPatch
    public static String Postfix(String retVal, RelicTier tier) {
      if ((retVal.equals(Identifiers.Relics.BEWITCHED_HAKKERO)) && (!AbstractDungeon.player.hasRelic(Identifiers.Relics.MINI_HAKKERO))) {
        return AbstractDungeon.returnEndRandomRelicKey(tier);
      }
      return retVal;
    }
  }
}

