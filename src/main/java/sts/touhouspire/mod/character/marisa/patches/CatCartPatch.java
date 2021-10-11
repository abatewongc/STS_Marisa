package sts.touhouspire.mod.character.marisa.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import sts.touhouspire.mod.character.marisa.data.Identifiers;

public class CatCartPatch {

	@SpirePatch(clz = AbstractPlayer.class, method = "damage", paramtypez = {DamageInfo.class})
    public static class CatCartResurrect {
    @SpireInsertPatch(
		    //locator = Locator.class
		    rloc = 149
    )
    public static SpireReturn insert(AbstractPlayer _inst, DamageInfo _info) {
      if ((_inst.hasRelic(Identifiers.Relics.CAT_CART)) && (!_inst.hasRelic(Identifiers.Relics.MARK_OF_THE_BLOOM))) {
        if ((_inst.getRelic(Identifiers.Relics.CAT_CART).counter > 0) && (!_inst.hasRelic(Identifiers.Relics.MARK_OF_THE_BLOOM))) {
          _inst.currentHealth = 0;
          _inst.getRelic(Identifiers.Relics.CAT_CART).onTrigger();
          return SpireReturn.Return(null);
        }
      }
      return SpireReturn.Continue();
    }
  }
}
