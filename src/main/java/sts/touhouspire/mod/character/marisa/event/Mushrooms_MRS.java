package sts.touhouspire.mod.character.marisa.event;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import sts.touhouspire.mod.character.marisa.data.Identifiers;
import sts.touhouspire.mod.character.marisa.relics.BigShroomBag;
import sts.touhouspire.mod.character.marisa.relics.ShroomBag;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Parasite;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.relics.OddMushroom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import org.apache.logging.log4j.LogManager;

public class Mushrooms_MRS extends AbstractEvent {

	private static final Logger logger = LogManager.getLogger(com.megacrit.cardcrawl.events.exordium.Mushrooms.class.getName());
	public static final String ID = Identifiers.Events.MUSHROOMS;
	private static final EventStrings eventStrings;
	public static final String NAME;
	public static final String[] DESCRIPTIONS;
	private static final String[] OPTIONS;
	private final Texture fgImg = ImageMaster.loadImage("images/events/fgShrooms.png");
	private final Texture bgImg = ImageMaster.loadImage("images/events/bgShrooms.png");
	private static final float HEAL_AMT = 0.25F;
	private static final String HEAL_MSG;
	private static final String FIGHT_MSG;
	private int screenNum = 0;
	private static final String ENC_NAME = "The Mushroom Lair";

	public Mushrooms_MRS() {
		this.roomEventText.clear();
		this.body = DESCRIPTIONS[2];
		this.roomEventText.addDialogOption(OPTIONS[0]);
		int temp = (int) ((float) AbstractDungeon.player.maxHealth * 0.25F);
		this.roomEventText.addDialogOption(OPTIONS[1] + temp + OPTIONS[2], CardLibrary.getCopy(Parasite.ID));
		AbstractDungeon.getCurrRoom().phase = RoomPhase.EVENT;
		this.hasDialog = true;
		this.hasFocus = true;
	}

	public void update() {
		super.update();
		if (!RoomEventDialog.waitForInput) {
			this.buttonEffect(this.roomEventText.getSelectedOption());
		}

	}

	protected void buttonEffect(int buttonPressed) {
		switch (buttonPressed) {
			case 0:
				if (this.screenNum == 0) {
					AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter(ENC_NAME);
					this.roomEventText.updateBodyText(FIGHT_MSG);
					this.roomEventText.updateDialogOption(0, OPTIONS[3]);
					this.roomEventText.removeDialogOption(1);
					AbstractEvent.logMetric(Identifiers.Events.MUSHROOMS, "Fought Mushrooms");
					this.screenNum += 2;
				} else if (this.screenNum == 1) {
					this.openMap();
				} else if (this.screenNum == 2) {
					if (Settings.isDailyRun) {
						AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(25));
					} else {
						AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(20, 30));
					}

					AbstractPlayer player = AbstractDungeon.player;
					AbstractRelic relicReward;
					if (player.hasRelic(Identifiers.Relics.BIG_SHROOM_BAG)) {
						relicReward = player.hasRelic(OddMushroom.ID) ? new Circlet() : new OddMushroom();
					} else if (player.hasRelic(Identifiers.Relics.SHROOM_BAG)) {
						relicReward = new BigShroomBag();
					} else {
						relicReward = new ShroomBag();
					}

					AbstractDungeon.getCurrRoom().addRelicToRewards(relicReward);
					this.enterCombat();
					AbstractDungeon.lastCombatMetricKey = ENC_NAME;
				}

				return;
			case 1:
				AbstractCard curse = new Parasite();
				int healAmt = (int) ((float) AbstractDungeon.player.maxHealth * HEAL_AMT);
				AbstractEvent
						.logMetricObtainCardAndHeal(Identifiers.Events.MUSHROOMS, "Healed and dodged fight", curse, healAmt);
				AbstractDungeon.player.heal(healAmt);
				AbstractDungeon.effectList.add(
						new ShowCardAndObtainEffect(curse, (float) Settings.WIDTH / 2.0F,
								(float) Settings.HEIGHT / 2.0F));
				this.roomEventText.updateBodyText(HEAL_MSG);
				this.roomEventText.updateDialogOption(0, OPTIONS[4]);
				this.roomEventText.removeDialogOption(1);
				this.screenNum = 1;
				return;
			default:
				logger.info("ERROR: case " + buttonPressed + " should never be called");
		}
	}

	public void render(SpriteBatch sb) {
		sb.setColor(Color.WHITE);
		sb.draw(this.bgImg, 0.0F, -10.0F, (float) Settings.WIDTH, 1080.0F * Settings.scale);
		sb.draw(this.fgImg, 0.0F, -20.0F * Settings.scale, (float) Settings.WIDTH,
				1080.0F * Settings.scale);
	}

	static {
		eventStrings = CardCrawlGame.languagePack.getEventString(Identifiers.Events.MUSHROOMS);
		NAME = eventStrings.NAME;
		DESCRIPTIONS = eventStrings.DESCRIPTIONS;
		OPTIONS = eventStrings.OPTIONS;
		HEAL_MSG = DESCRIPTIONS[0];
		FIGHT_MSG = DESCRIPTIONS[1];
	}
}