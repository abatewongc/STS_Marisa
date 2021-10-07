package sts.touhouspire.mod.character.marisa.characters;

import com.megacrit.cardcrawl.localization.CharacterStrings;
import sts.touhouspire.mod.character.marisa.MarisaModHandler;
import sts.touhouspire.mod.character.marisa.cards.Marisa.MasterSpark;
import sts.touhouspire.mod.character.marisa.data.Constants;
import sts.touhouspire.mod.character.marisa.data.Identifiers;
import sts.touhouspire.mod.character.marisa.data.Identifiers.Cards;
import sts.touhouspire.mod.character.marisa.patches.AbstractCardEnum;
import sts.touhouspire.mod.character.marisa.patches.MarisaModClassEnum;
import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;

import sts.touhouspire.mod.character.marisa.utils.MarisaHelpers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MarisaCharacter extends CustomPlayer {

	private static final int ENERGY_PER_TURN = 3; // how much energy you get every turn

	private static final int STARTING_HP = 75;
	private static final int MAX_HP = 75;
	private static final int STARTING_GOLD = 99;
	private static final int HAND_SIZE = 5;
	private static final int ASCENSION_MAX_HP_LOSS = 5;

	public ArrayList<String> getStartingDeck() { // starting deck 'nuff said
		ArrayList<String> deck = new ArrayList<>();
		deck.add(Cards.STRIKE);
		deck.add(Cards.STRIKE);
		deck.add(Cards.STRIKE);
		deck.add(Cards.STRIKE);
		deck.add(Cards.DEFEND);
		deck.add(Cards.DEFEND);
		deck.add(Cards.DEFEND);
		deck.add(Cards.DEFEND);
		deck.add(Cards.MASTER_SPARK);
		deck.add(Cards.UPSWEEP);
		return deck;
	}

	public ArrayList<String> getStartingRelics() {
		ArrayList<String> relics = new ArrayList<>();
		relics.add(Identifiers.Relics.MINI_HAKKERO);
		UnlockTracker.markRelicAsSeen(Identifiers.Relics.MINI_HAKKERO);
		return relics;
	}

	public AbstractCard getStartCardForEvent() {
		return new MasterSpark();
	}

	@Override
	public void applyPreCombatLogic() {
		super.applyPreCombatLogic();
		MarisaModHandler.typhoonCounter = 0;
		MarisaModHandler.logger.info("applyPreCombatLogic: Reset typhoon counter. It is currently at " + MarisaModHandler.typhoonCounter);
	}

	private static final String CHARACTER_ID = MarisaHelpers.makeID("MarisaCharacter");
	private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(CHARACTER_ID);

	private static final String MARISA_SHOULDER_2 = "marisa/img/char/Marisa/shoulder2.png"; // shoulder2 / shoulder_1
	private static final String MARISA_SHOULDER_1 = "marisa/img/char/Marisa/shoulder1.png"; // shoulder1 / shoulder_2
	private static final String MARISA_CORPSE = "marisa/img/char/Marisa/fallen.png"; // dead corpse
	public static final Logger logger = LogManager.getLogger(MarisaModHandler.class.getName());

	private static final String MARISA_SKELETON_ATLAS = "marisa/img/char/Marisa/MarisaModelv3.atlas";// Marisa_v0 / MarisaModel_v02 /MarisaModelv3
	private static final String MARISA_SKELETON_JSON = "marisa/img/char/Marisa/MarisaModelv3.json";
	private static final String MARISA_ANIMATION = "Idle";// Sprite / Idle
	private static final String[] ORB_TEXTURES = {
			"marisa/img/UI/EPanel/layer5.png",
			"marisa/img/UI/EPanel/layer4.png",
			"marisa/img/UI/EPanel/layer3.png",
			"marisa/img/UI/EPanel/layer2.png",
			"marisa/img/UI/EPanel/layer1.png",
			"marisa/img/UI/EPanel/layer0.png",
			"marisa/img/UI/EPanel/layer5d.png",
			"marisa/img/UI/EPanel/layer4d.png",
			"marisa/img/UI/EPanel/layer3d.png",
			"marisa/img/UI/EPanel/layer2d.png",
			"marisa/img/UI/EPanel/layer1d.png"
	};
	private static final String ORB_VFX = "marisa/img/UI/energyBlueVFX.png";
	private static final float[] LAYER_SPEED = {-40.0F, -32.0F, 20.0F, -20.0F, 0.0F, -10.0F, -8.0F, 5.0F, -5.0F, 0.0F};

	public MarisaCharacter(String name) {
		super(name, MarisaModClassEnum.MARISA, ORB_TEXTURES, ORB_VFX, LAYER_SPEED, null, null);

		this.dialogX = (this.drawX + 0.0F * Settings.scale);
		this.dialogY = (this.drawY + 220.0F * Settings.scale);

		logger.info("init Marisa");

		initializeClass(null, MARISA_SHOULDER_2, MARISA_SHOULDER_1, MARISA_CORPSE,
				getLoadout(),
				20.0F, -10.0F, 220.0F, 290.0F,
				new EnergyManager(ENERGY_PER_TURN)
		);

		loadAnimation(MARISA_SKELETON_ATLAS, MARISA_SKELETON_JSON, 2.0F);
		AnimationState.TrackEntry e = this.state.setAnimation(0, MARISA_ANIMATION, true);
		e.setTime(e.getEndTime() * MathUtils.random());
		this.stateData.setMix("Hit", "Idle", 0.1F);
		e.setTimeScale(1.0F);
		logger.info("init finish");
	}

	@Override
	public String getPortraitImageName() {
		return null;
	}

	public CharSelectInfo getLoadout() { // the rest of the character loadout so includes your character select screen info plus hp and starting gold
		return new CharSelectInfo(characterStrings.TEXT[0], characterStrings.TEXT[1],
				STARTING_HP, MAX_HP,
				0,
				STARTING_GOLD, HAND_SIZE,
				this,
				getStartingRelics(), getStartingDeck(),
				false
		);
	}

	public AbstractCard.CardColor getCardColor() {
		return AbstractCardEnum.MARISA_COLOR;
	}

	public String getTitle(PlayerClass playerClass) {
		return characterStrings.TEXT[0];
	}

	public Color getCardTrailColor() {
		return Constants.STARLIGHT;
	}

	public int getAscensionMaxHPLoss() {
		return ASCENSION_MAX_HP_LOSS;
	}

	public BitmapFont getEnergyNumFont() {
		return FontHelper.energyNumFontBlue;
	}

	public void doCharSelectScreenSelectEffect() {
		CardCrawlGame.sound.playA("SELECT_MRS", MathUtils.random(-0.1F, 0.1F));
		CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
	}

	public String getCustomModeCharacterButtonSoundKey() {
		return "SELECT_MRS";
	}

	public String getLocalizedCharacterName() {
		String char_name;
		if ((Settings.language == Settings.GameLanguage.JPN) || (Settings.language
				== Settings.GameLanguage.ZHS) || (Settings.language == Settings.GameLanguage.ZHT)) {
			char_name = "\u9b54\u7406\u6c99";
		} else if (Settings.language == Settings.GameLanguage.KOR) {
			char_name = "\ub9c8\ub9ac\uc0ac";
		} else {
			char_name = "Marisa";
		}
		return char_name;
	}

	public AbstractPlayer newInstance() {
		return new MarisaCharacter(this.name);
	}

	@Override
	public String getVampireText() {
		return com.megacrit.cardcrawl.events.city.Vampires.DESCRIPTIONS[1];
	}

	public Color getCardRenderColor() {
		return Constants.STARLIGHT;
	}

	public void updateOrb(int orbCount) {
		this.energyOrb.updateOrb(orbCount);
	}

	public TextureAtlas.AtlasRegion getOrb() {
		return new TextureAtlas.AtlasRegion(ImageMaster.loadImage(Constants.CARD_ENERGY_ORB), 0, 0, 24, 24);
	}

	public Color getSlashAttackColor() {
		return Constants.STARLIGHT;
	}

	public AttackEffect[] getSpireHeartSlashEffect() {
		return new AttackEffect[]{
				AttackEffect.SLASH_HEAVY,
				AttackEffect.FIRE,
				AttackEffect.SLASH_DIAGONAL,
				AttackEffect.SLASH_HEAVY,
				AttackEffect.FIRE,
				AttackEffect.SLASH_DIAGONAL
		};
	}

	public String getSpireHeartText() {
		return com.megacrit.cardcrawl.events.beyond.SpireHeart.DESCRIPTIONS[10];
	}

	public void damage(DamageInfo info) {
		if ((info.owner != null) && (info.type != DamageInfo.DamageType.THORNS) && (
				info.output - this.currentBlock > 0)) {
			AnimationState.TrackEntry e =
					this.state.setAnimation(0, "Hit", false);
			this.state.addAnimation(0, "Idle", true, 0.0F);
			e.setTimeScale(1.0F);
		}
		super.damage(info);
	}

}
