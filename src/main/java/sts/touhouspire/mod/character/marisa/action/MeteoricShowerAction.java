package sts.touhouspire.mod.character.marisa.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import sts.touhouspire.mod.character.marisa.vfx.MeteoricShowerEffect;

public class MeteoricShowerAction extends AbstractGameAction {

  private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
  public static final String[] TEXT = uiStrings.TEXT;
  private final AbstractPlayer player;
  private final int numCardsToSelect;
  private final int damage;
  private final boolean freeToPlay;

  public MeteoricShowerAction(int number, int damage, boolean freeToPlay) {
    this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
    this.player = AbstractDungeon.player;
    this.duration = Settings.ACTION_DUR_FAST;
    this.numCardsToSelect = number;
    this.damage = damage;
    this.freeToPlay = freeToPlay;
  }

  public void update() {
    if (this.duration == Settings.ACTION_DUR_FAST) {
      if (this.player.hand.isEmpty()) {
        this.isDone = true;
        return;
      }
      AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.numCardsToSelect, true, true);
      tickDuration();
      return;
    }

    if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
      int count = 0;
      for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
        count += 2;
        if ((card instanceof Burn)) {
          count++;
        }
        this.player.hand.moveToExhaustPile(card);
      }

      AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
      AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
      if (count > 0) {
        float duration = Settings.FAST_MODE ? 0.5f : 1.0f;
        MonsterGroup monsters = AbstractDungeon.getMonsters();
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new MeteoricShowerEffect(count, monsters.shouldFlipVfx(), (float) monsters.monsters.stream().mapToDouble(m -> m.drawX).average().orElse(Settings.WIDTH)), duration));
        AbstractDungeon.actionManager.addToBottom(new UnstableBombAction(AbstractDungeon.getMonsters().getRandomMonster(true), damage, damage, count));
      }
      AbstractDungeon.gridSelectScreen.selectedCards.clear();
      AbstractDungeon.player.hand.refreshHandLayout();
    }

    if (!this.freeToPlay) {
      player.energy.use(EnergyPanel.totalCount);
    }

    tickDuration();
  }
}
