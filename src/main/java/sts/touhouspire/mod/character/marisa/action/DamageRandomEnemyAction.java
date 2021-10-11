package sts.touhouspire.mod.character.marisa.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sts.touhouspire.mod.character.marisa.utils.MarisaHelpers;

public class DamageRandomEnemyAction extends AbstractGameAction {

  private DamageInfo info;

  public DamageRandomEnemyAction(DamageInfo info, AttackEffect effect) {
    this.info = info;
    setValues(MarisaHelpers.getRandomMonster(), info);
    this.actionType = AbstractGameAction.ActionType.DAMAGE;
    this.attackEffect = effect;
    this.duration = 0.1F;
  }

  @Override
  public void update() {
    if (target == null) {
      this.isDone = true;
      return;
    }
    AbstractDungeon.actionManager.addToBottom(
		    new DamageAction(target, info, attackEffect)
    );
    this.isDone = true;
  }
}
