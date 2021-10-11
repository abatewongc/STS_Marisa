package io.aleosiss.sts.character.marisa.action.deprecated;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;

import io.aleosiss.sts.character.marisa.ThMod;

@Deprecated
public class OpenUniverseAction
    extends AbstractGameAction {

  private boolean upgraded;
  private boolean amplified;

  public OpenUniverseAction(boolean upg, boolean amp) {
    this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
    this.duration = Settings.ACTION_DUR_FAST;
    this.upgraded = upg;
    this.amplified = amp;
  }

  public void update() {
    this.isDone = false;
    AbstractPlayer p = AbstractDungeon.player;

    io.aleosiss.sts.character.marisa.logger.info("OpenUniverseAction : generationg cards");

    ArrayList<AbstractCard> cl = new ArrayList<AbstractCard>();

    for (int i = 0; i < 5; i++) {
      AbstractCard card = AbstractDungeon.returnTrulyRandomCard();
      io.aleosiss.sts.character.marisa.logger.info("OpenUniverse : generationg : " + card.cardID);
      if (this.upgraded) {
        card.upgrade();
      }

    }
    if (this.amplified) {
    }
    for (int i = 0; i < 5; i++) {
      AbstractDungeon.effectList.add(
          new ShowCardAndAddToDrawPileEffect(
              cl.get(i), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, true
          )
      );
    }

    io.aleosiss.sts.character.marisa.logger.info("OpenUniverse : shuffling");

    p.drawPile.shuffle();
    for (AbstractRelic r : p.relics) {
      r.onShuffle();
    }

    io.aleosiss.sts.character.marisa.logger.info("OpenUniverse : done");

    this.isDone = true;
  }
}