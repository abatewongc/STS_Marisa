package io.aleosiss.sts.character.marisa.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.JudgementAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FairyDestrucCullingAction extends AbstractGameAction {

  private int threshold;

  public FairyDestrucCullingAction(int threshold) {
    this.threshold = threshold;
  }

  @Override
  public void update() {
    // TODO Auto-generated method stub
    this.isDone = false;
    if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
      this.isDone = true;
      return;
    }
    for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
      addToBot(new JudgementAction(m, this.threshold));
    }
    if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
      AbstractDungeon.actionManager.clearPostCombatActions();
    }
    this.isDone = true;
  }
}
