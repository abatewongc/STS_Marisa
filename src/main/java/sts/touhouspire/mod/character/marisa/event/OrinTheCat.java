package sts.touhouspire.mod.character.marisa.event;

import sts.touhouspire.mod.character.marisa.cards.derivations.Wraith;
import sts.touhouspire.mod.character.marisa.data.Identifiers;
import sts.touhouspire.mod.character.marisa.relics.CatCart;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import sts.touhouspire.mod.character.marisa.MarisaModHandler;

public class OrinTheCat extends AbstractEvent {

  public static final String ID = Identifiers.Events.ORIN_THE_CAT;
  private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
  public static final String NAME = eventStrings.NAME;
  private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
  private static final String[] OPTIONS = eventStrings.OPTIONS;
  private static final String INTRO_MSG = DESCRIPTIONS[0];
  private CurScreen screen = CurScreen.INTRO;
  private AbstractMonster orin;
  private boolean satori;

  private enum CurScreen {
    INTRO,
    PRE_COMBAT,
    END;

    CurScreen() {
    }
  }

  public OrinTheCat() {
    this.roomEventText.clear();
    this.body = INTRO_MSG;
    satori = AbstractDungeon.player.name.equals("Komeiji");
    if (satori) {
      this.roomEventText.addDialogOption(OPTIONS[4]);
    } else {
      this.roomEventText.addDialogOption(OPTIONS[0], CardLibrary.getCopy(Identifiers.Cards.WRAITH));
    }
    this.roomEventText.addDialogOption(OPTIONS[1]);

    this.hasDialog = true;
    this.hasFocus = true;
    AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter(Identifiers.Monsters.ORIN);
    orin = AbstractDungeon.getCurrRoom().monsters.monsters.get(0);
    if (orin != null) {
      MarisaModHandler.logger.info("OrinTheCat : orin get : " + orin.name);
    } else {
      MarisaModHandler.logger.info("OrinTheCat : error : null orin");
    }
  }

  @Override
  public void update() {
    super.update();
    if (!RoomEventDialog.waitForInput) {
      buttonEffect(this.roomEventText.getSelectedOption());
    }
  }

  @Override
  public void onEnterRoom() {
    MarisaModHandler.logger.info("OrinTheCat : OnEnterRoom");
    AbstractDungeon.getCurrRoom().rewards.clear();
  }

  @Override
  protected void buttonEffect(int buttonPressed) {
    switch (this.screen) {
      case INTRO:
        switch (buttonPressed) {
          case 0:
            if (satori) {
              this.screen = CurScreen.END;
              AbstractDungeon.getCurrRoom().spawnRelicAndObtain(
                      (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), new CatCart()
              );
              this.roomEventText.updateBodyText(DESCRIPTIONS[3]);
              this.roomEventText.updateDialogOption(0, OPTIONS[3]);
              this.roomEventText.clearRemainingOptions();
              logMetric(ID, "Special");
              return;
            } else {
              MarisaModHandler.logger.info("OrinTheCat : INTRO : Skipping fight!");
              this.screen = CurScreen.END;
              /*
              if (orin != null) {
                AbstractDungeon.actionManager.addToBottom(new EscapeAction(orin));
              }
              */
              this.roomEventText.updateBodyText(DESCRIPTIONS[2]);
              this.roomEventText.updateDialogOption(0, OPTIONS[3]);
              this.roomEventText.clearRemainingOptions();
              AbstractDungeon.effectList.add(
                      new ShowCardAndObtainEffect(
                              new Wraith(),
                              Settings.WIDTH / 2.0F,
                              Settings.HEIGHT / 2.0F
                      )
              );
              logMetricIgnored(ID);
              return;
            }
          case 1:
            if (satori) {
              this.screen = CurScreen.END;
              /*
              if (orin != null) {
                AbstractDungeon.actionManager.addToBottom(new EscapeAction(orin));
              }
              */
              this.roomEventText.updateBodyText(DESCRIPTIONS[4]);
              this.roomEventText.updateDialogOption(0, OPTIONS[3]);
              this.roomEventText.clearRemainingOptions();
              logMetricIgnored(ID);
              return;
            } else {
              MarisaModHandler.logger.info("OrinTheCat : INTRO : Fight!");
              this.screen = CurScreen.PRE_COMBAT;
              this.roomEventText.updateBodyText(DESCRIPTIONS[1]);
              this.roomEventText.updateDialogOption(0, OPTIONS[2]);
              this.roomEventText.clearRemainingOptions();
              logMetric(ID, "Fight");
              return;
            }
            /*
          default:
            logger.info(
                "OrinTheCat event : error : key number "
                    + buttonPressed
                    + " should never be pressed."
            );
            */
        }
        break;
      case PRE_COMBAT:
        MarisaModHandler.logger.info("OrinTheCat : PreCombat : Adding Reward");
        AbstractRoom currRoom = AbstractDungeon.getCurrRoom();
        currRoom.rewards.clear();
        if (!AbstractDungeon.player.hasRelic(Identifiers.Relics.CAT_CART)) {
          if (Settings.isDailyRun) {
            currRoom.addGoldToRewards(AbstractDungeon.miscRng.random(50));
          } else {
            currRoom.addGoldToRewards(AbstractDungeon.miscRng.random(45, 55));
          }
          currRoom.addRelicToRewards(new CatCart());
        }

        AbstractDungeon.getCurrRoom().eliteTrigger = true;
        //this.img = ImageMaster.loadImage("images/events/sphereOpen.png");

        MarisaModHandler.logger.info("OrinTheCat : PreCombat : Entering combat");
        enterCombat();
        AbstractDungeon.lastCombatMetricKey = Identifiers.Encounters.ORIN_ENCOUNTER;
        break;
      case END:
        MarisaModHandler.logger.info("OrinTheCat : end : Opening Map");
        openMap();
    }
  }
}

//public class OrinTheCat \
