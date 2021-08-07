package io.aleosiss.sts.character.marisa.cards.derivations;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import io.aleosiss.sts.character.marisa.abstracts.MarisaModCard;
import io.aleosiss.sts.character.marisa.data.Identifiers;

public class Exhaustion extends MarisaModCard {

  public static final String ID = Identifiers.Cards.EXHAUSTION;
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;
  private static final int COST = -2;

  public Exhaustion() {
    super(
            ID,
            NAME,
            "marisa/img/cards/exhaustion.png",
            COST,
            DESCRIPTION,
            CardType.STATUS,
            CardColor.COLORLESS,
            CardRarity.SPECIAL,
            CardTarget.NONE
    );
    this.exhaust = true;
  }

  public void use(AbstractPlayer p, AbstractMonster m) {
    if (p.hasRelic("Medical Kit")) {
      useMedicalKit(p);
    } else {
      AbstractDungeon.actionManager.addToBottom(
              new UseCardAction(this)
      );
    }
  }

  public AbstractCard makeCopy() {
    return new Exhaustion();
  }

  public void upgrade() {
  }
}
