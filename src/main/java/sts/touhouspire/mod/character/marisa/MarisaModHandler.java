package sts.touhouspire.mod.character.marisa;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sts.touhouspire.mod.character.marisa.action.SparkCostAction;
import sts.touhouspire.mod.character.marisa.cards.Marisa.*;
import sts.touhouspire.mod.character.marisa.cards.derivations.*;
import sts.touhouspire.mod.character.marisa.characters.MarisaCharacter;
import sts.touhouspire.mod.character.marisa.data.Constants;
import sts.touhouspire.mod.character.marisa.data.Identifiers;
import sts.touhouspire.mod.character.marisa.event.Mushrooms_MRS;
import sts.touhouspire.mod.character.marisa.event.OrinTheCat;
import sts.touhouspire.mod.character.marisa.monsters.Orin;
import sts.touhouspire.mod.character.marisa.monsters.ZombieFairy;
import sts.touhouspire.mod.character.marisa.patches.AbstractCardEnum;
import sts.touhouspire.mod.character.marisa.patches.CardTagEnum;
import sts.touhouspire.mod.character.marisa.patches.MarisaModClassEnum;
import sts.touhouspire.mod.character.marisa.potions.BottledSpark;
import sts.touhouspire.mod.character.marisa.potions.ShroomBrew;
import sts.touhouspire.mod.character.marisa.potions.StarNLove;
import sts.touhouspire.mod.character.marisa.relics.*;
import sts.touhouspire.mod.character.marisa.utils.MarisaHelpers;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import static sts.touhouspire.mod.character.marisa.utils.MarisaHelpers.addRelic;

@SpireInitializer
public class MarisaModHandler implements
		PostExhaustSubscriber,
		PostBattleSubscriber,
		PostDungeonInitializeSubscriber,
		EditCharactersSubscriber,
		PostInitializeSubscriber,
		EditRelicsSubscriber,
		EditCardsSubscriber,
		EditStringsSubscriber,
		OnCardUseSubscriber,
		EditKeywordsSubscriber,
		OnPowersModifiedSubscriber,
		PostDrawSubscriber,
		PostEnergyRechargeSubscriber {
	public static final String MOD_ID = "Marisa";
	public static final Logger logger = LogManager.getLogger(MarisaModHandler.class.getName());

	// lol
	private static final String TRUE = "TRUE";
	private static final String FALSE = "FALSE";

	public static int typhoonCounter = 0;
	public static boolean isCatEventEnabled;
	public static boolean isDeadBranchEnabled;

	private final Properties marisaModDefaultProperties = new Properties();

	@SuppressWarnings("unused") // SpireInitializer
	public static void initialize() { new MarisaModHandler();}

	public MarisaModHandler() {
		BaseMod.subscribe(this);
		constructColors();
		constructConfiguration();
		logger.info("Finished Marisa mod basemod registration.");
	}

	private void constructConfiguration() {
		setDefaultProperties();
		try {
			final SpireConfig config = new SpireConfig("Marisa", "marisaConfig", marisaModDefaultProperties);
			config.load();
			isCatEventEnabled = config.getBool(Identifiers.Properties.IS_CAT_EVENT_ENABLED);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setDefaultProperties() {
		marisaModDefaultProperties.setProperty(Identifiers.Properties.IS_CAT_EVENT_ENABLED, TRUE);
	}

	private void constructColors() {
		BaseMod.addColor(AbstractCardEnum.MARISA_COLOR,
				Constants.STARLIGHT, Constants.STARLIGHT, Constants.STARLIGHT, Constants.STARLIGHT, Constants.STARLIGHT, Constants.STARLIGHT, Constants.STARLIGHT,
				Constants.ATTACK_CC, Constants.SKILL_CC, Constants.POWER_CC, Constants.ENERGY_ORB_CC, Constants.ATTACK_CC_PORTRAIT, Constants.SKILL_CC_PORTRAIT,
				Constants.POWER_CC_PORTRAIT, Constants.ENERGY_ORB_CC_PORTRAIT, Constants.CARD_ENERGY_ORB
		);
	}

	@Override
	public void receiveEditCharacters() {
		logger.info("begin editing characters");

		logger.info("add " + MarisaModClassEnum.MARISA.toString());
		BaseMod.addCharacter(
				new MarisaCharacter("Marisa"),
				Constants.MARISA_CHARACTER_BUTTON,
				Constants.MARISA_PORTRAIT,
				MarisaModClassEnum.MARISA
		);
		logger.info("done editing characters");
	}

	@Override
	public void receiveEditRelics() {
		logger.info("Begin editing relics.");

		addRelic(new MiniHakkero());
		addRelic(new BewitchedHakkero());
		addRelic(new MagicBroom());
		addRelic(new AmplifyWand());
		addRelic(new ExperimentalFamiliar());
		addRelic(new RampagingMagicTools());
		addRelic(new BreadOfAWashokuLover());
		addRelic(new SimpleLauncher());
		addRelic(new HandmadeGrimoire());
		addRelic(new ShroomBag());
		addRelic(new SproutingBranch());
		addRelic(new BigShroomBag());
		//addRelic(new Cape());

		BaseMod.addRelic(new CatCart(), RelicType.SHARED);
		//BaseMod.addRelicToCustomPool(new Cape_1(), AbstractCardEnum.MARISA_COLOR);

		logger.info("Relics editing finished.");
	}

	@Override
	public void receiveEditCards() {
		logger.info("starting editing cards");
		logger.info("adding cards for MARISA");
		for (AbstractCard card : loadCardsToAdd()) {
			logger.info("Adding card : " + card.name);
			BaseMod.addCard(card);
		}

		logger.info("done editing cards");
	}

	@Override
	public void receivePostExhaust(AbstractCard c) {
		// TODO Auto-generated method stub
	}

	@Override
	public void receivePostBattle(AbstractRoom r) {
		typhoonCounter = 0;
		logger.info("Marisa : PostBattle ; typhoon-counter reset");
	}

	@Override
	public void receiveCardUsed(AbstractCard card) {
		MarisaModHandler.logger.info("Marisa : Card used : " + card.cardID + " ; cost : " + card.costForTurn);
		if (MarisaHelpers.wasZeroCost(card)) {
			typhoonCounter++;
			MarisaModHandler.logger.info("typhoon-counter increased , now :" + typhoonCounter);
		}
		if (card.retain) {
			card.retain = false;
		}
		if (card.hasTag(CardTagEnum.SPARK)) {
			AbstractDungeon.actionManager.addToTop(new SparkCostAction());
		}
	}

	@Override
	public void receivePostEnergyRecharge() {
		if (AbstractDungeon.player.hand.isEmpty()) {
			return;
		}

		for (AbstractCard card : AbstractDungeon.player.hand.group) {
			if (card instanceof GuidingStar) {
				AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
				card.flash();
			}
		}
	}

	@Override
	public void receivePowersModified() {
		// TODO Auto-generated method stub

	}

	@Override
	public void receivePostDungeonInitialize() {
		// TODO Auto-generated method stub
	}

	@Override
	public void receivePostDraw(AbstractCard arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void receiveEditKeywords() {
		logger.info("Setting up custom keywords");
		String json = loadJson(makeLocalizedStringsPath(Settings.language, "keywords.json"));

		Gson gson = new Gson();
		Keywords keywords;
		keywords = gson.fromJson(json, Keywords.class);
		for (Keyword key : keywords.keywords) {
			logger.info("Loading keyword : " + key.NAMES[0]);
			BaseMod.addKeyword(key.NAMES, key.DESCRIPTION);
		}
		logger.info("Keywords setting finished.");
	}

	@Override
	public void receiveEditStrings() {
		logger.info("Beginning to edit strings for mod with ID: " + MOD_ID);

		loadLocalizedStringData(CardStrings.class, "cards.json");
		loadLocalizedStringData(CharacterStrings.class, "character.json");
		loadLocalizedStringData(EventStrings.class, "events.json");
		loadLocalizedStringData(PowerStrings.class, "powers.json");
		//loadLocalizedStringData(MonsterStrings.class, "monsters.json");
		loadLocalizedStringData(PotionStrings.class, "potions.json");
		loadLocalizedStringData(RelicStrings.class, "relics.json");
		loadLocalizedStringData(UIStrings.class, "ui.json");

		logger.info("Done editing strings");
	}

	@Override
	public void receivePostInitialize() {
		logger.info("Adding badge, configs, event and potion");
		final ModPanel settingsPanel = addModPanel();

		addEvents();
		addPotions();
		addMonsters();

		final Texture badge = ImageMaster.loadImage(Constants.MOD_BADGE);
		BaseMod.registerModBadge(
				badge,
				"Marisa",
				"Flynn, Hell, Hohner_257, Samsara, Aleosiss",
				"The poor blonde girl from the Touhou Project",
				settingsPanel
		);
	}

	private ModPanel addModPanel() {
		UIStrings deadBranchReplacementUI = CardCrawlGame.languagePack.getUIString("Marisa:DeadBranchReplacement");
		UIStrings blackCatEventForAllUI = CardCrawlGame.languagePack.getUIString("Marisa:EnableBlackCatEventForAll");

		final ModPanel settingsPanel = new ModPanel();
		final ModLabeledToggleButton enableBlackCatButton =
				new ModLabeledToggleButton(
						blackCatEventForAllUI.TEXT[0],
						350.0f,
						700.0f,
						Settings.CREAM_COLOR,
						FontHelper.charDescFont,
						isCatEventEnabled,
						settingsPanel,
						label -> {},
						button -> {
							isCatEventEnabled = button.enabled;
							try {
								final SpireConfig config = new SpireConfig("MarisaMod", "MarisaModCongfig", marisaModDefaultProperties);
								config.setBool("enablePlaceholder", isCatEventEnabled);
								config.save();
							} catch (Exception e) {
								e.printStackTrace();
							}
						});

		final ModLabeledToggleButton enableDeadBranchButton =
				new ModLabeledToggleButton(
						deadBranchReplacementUI.TEXT[0],
						350.0f,
						600.0f,
						Settings.CREAM_COLOR,
						FontHelper.charDescFont,
						isDeadBranchEnabled,
						settingsPanel,
						label -> {
						},
						button -> {
							isCatEventEnabled = button.enabled;
							try {
								final SpireConfig config = new SpireConfig("MarisaMod", "MarisaModCongfig",
										marisaModDefaultProperties);
								config.setBool("enablePlaceholder", isDeadBranchEnabled);
								config.save();
							} catch (Exception e) {
								e.printStackTrace();
							}
						});

		settingsPanel.addUIElement(enableBlackCatButton);
		settingsPanel.addUIElement(enableDeadBranchButton);
		return settingsPanel;
	}

	private void addMonsters() {
		BaseMod.addMonster(Identifiers.Encounters.ORIN_ENCOUNTER, Orin::new);
		BaseMod.addMonster(Identifiers.Encounters.ZOMBIE_FAIRY_ENCOUNTER, (BaseMod.GetMonster) ZombieFairy::new);
	}

	private void addEvents() {
		BaseMod.addEvent(Mushrooms_MRS.ID, Mushrooms_MRS.class, Exordium.ID);
		BaseMod.addEvent(OrinTheCat.ID, OrinTheCat.class, TheBeyond.ID);
	}

	private void addPotions() {
		BaseMod.addPotion(ShroomBrew.class,
				Color.NAVY.cpy(), Color.LIME.cpy(), Color.OLIVE,
				ShroomBrew.POTION_ID,
				MarisaModClassEnum.MARISA
		);

		BaseMod.addPotion(StarNLove.class,
				Color.BLUE.cpy(), Color.YELLOW.cpy(), Color.NAVY,
				StarNLove.POTION_ID,
				MarisaModClassEnum.MARISA
		);

		BaseMod.addPotion(BottledSpark.class,
				Color.BLUE.cpy(), Color.YELLOW.cpy(), Color.NAVY,
				BottledSpark.POTION_ID,
				MarisaModClassEnum.MARISA
		);
	}

	private void loadLocalizedStringData(Class<?> stringType, String stringsFileName) {
		// We load english first as a fallback for yet-to-be-translated things, then load the "true" language if it is not English
		BaseMod.loadCustomStringsFile(stringType, makeLocalizedStringsPath(Settings.GameLanguage.ENG, stringsFileName));
		if (!Settings.GameLanguage.ENG.equals(Settings.language)) {
			BaseMod.loadCustomStringsFile(stringType, makeLocalizedStringsPath(Settings.language, stringsFileName));
		}
	}

	public static String makeLocalizedStringsPath(Settings.GameLanguage language, String resourcePath) {
		String languageFolder = language.name().toLowerCase(Locale.ROOT);
		String path = "marisa/localization/" + languageFolder + "/" + resourcePath;
		logger.info("creating localized string path:" + MarisaModHandler.class.getClassLoader().getResource(path).getPath());
		return path;
	}

	private static String loadJson(String jsonPath) {
		return Gdx.files.internal(jsonPath).readString(String.valueOf(StandardCharsets.UTF_8));
	}

	private List<AbstractCard> loadCardsToAdd() {
		ArrayList<AbstractCard> cardLoader = new ArrayList<>();

		cardLoader.add(new Strike_MRS());
		cardLoader.add(new Defend_MRS());
		cardLoader.add(new MasterSpark());
		cardLoader.add(new Upsweep());

		cardLoader.add(new DoubleSpark());
		cardLoader.add(new NonDirectionalLaser());
		cardLoader.add(new LuminousStrike());
		cardLoader.add(new MysteriousBeam());
		cardLoader.add(new WitchLeyline());
		cardLoader.add(new Surprise());
		cardLoader.add(new Rebound());
		cardLoader.add(new UnstableBomb());
		cardLoader.add(new StarBarrage());
		cardLoader.add(new ShootingEcho());
		cardLoader.add(new MachineGunSpark());
		cardLoader.add(new DarkSpark());
		cardLoader.add(new DeepEcologicalBomb());
		cardLoader.add(new MeteoricShower());
		cardLoader.add(new GravityBeat());
		cardLoader.add(new GrandCross());
		cardLoader.add(new DragonMeteor());
		cardLoader.add(new RefractionSpark());
		cardLoader.add(new Robbery());
		cardLoader.add(new ChargeUpSpray());
		cardLoader.add(new AlicesGift());
		cardLoader.add(new FairyDestructionRay());
		cardLoader.add(new BlazingStar());
		cardLoader.add(new ShootTheMoon());
		cardLoader.add(new FinalSpark());
		cardLoader.add(new WarmingUp());
		cardLoader.add(new AbsoluteMagnitude());
		cardLoader.add(new TreasureHunter());
		cardLoader.add(new CollectingQuirk());

		cardLoader.add(new MilkyWay());
		cardLoader.add(new AsteroidBelt());
		cardLoader.add(new Upgrade());
		cardLoader.add(new SporeCrump());
		cardLoader.add(new IllusionStar());
		cardLoader.add(new EnergyRecoil());
		cardLoader.add(new GasGiant());
		cardLoader.add(new StardustReverie());
		cardLoader.add(new MagicAbsorber());
		cardLoader.add(new Occultation());
		cardLoader.add(new EarthLightRay());
		cardLoader.add(new BlazeAway());
		cardLoader.add(new ChargingUp());
		cardLoader.add(new DarkMatter());
		cardLoader.add(new MagicChant());
		cardLoader.add(new OneTimeOff());
		cardLoader.add(new ManaConvection());
		cardLoader.add(new PropBag());
		cardLoader.add(new SprinkleStarSeal());
		cardLoader.add(new GalacticHalo());
		cardLoader.add(new SuperPerseids());
		cardLoader.add(new PulseMagic());
		cardLoader.add(new Orbital());
		cardLoader.add(new BigCrunch());
		cardLoader.add(new OpenUniverse());
		cardLoader.add(new StarlightTyphoon());
		cardLoader.add(new MaximisePower());
		cardLoader.add(new UltraShortWave());
		cardLoader.add(new ManaRampage());
		cardLoader.add(new BinaryStars());
		cardLoader.add(new Acceleration());

		cardLoader.add(new WitchOfGreed());
		cardLoader.add(new SatelliteIllusion());
		cardLoader.add(new OortCloud());
		cardLoader.add(new OrrerysSun());
		cardLoader.add(new EnergyFlow());
		cardLoader.add(new EventHorizon());
		cardLoader.add(new Singularity());
		cardLoader.add(new CasketOfStar());
		//cardsToAdd.add(new PolarisUnique());
		cardLoader.add(new EscapeVelocity());
		cardLoader.add(new MillisecondPulsars());
		cardLoader.add(new Supernova());

		cardLoader.add(new Spark());
		cardLoader.add(new GuidingStar());
		cardLoader.add(new BlackFlareStar());
		cardLoader.add(new WhiteDwarfStar());
		cardLoader.add(new Exhaustion());
		cardLoader.add(new Wraith());

		return cardLoader;
	}

	static class Keywords {
		Keyword[] keywords;
	}

	/*



............................................................................................................................................
............................................................................................................................................
....................................................:7......................................................................................
....................................................7II7?,::::::::::::......................................................................
.................................,,,...............,,????7+~::::::::::::,...................................................................
..............................~:,,,:::......,..::::=I777 7?I77,:::::::::::..................................................................
...................................,,:::::::::::::,7777I777777777=,:::::::::................................................................
....................................,,,,:::,,,,,.::=7 777777?777777I::,,,,,,::..............................................................
...............................................,:,,777777777777?777777+.,,,,,,,.............................................................
..............................................:,I7III777777?77777I?77I77.,,,,,,,,...........................................................
............................................,,,:7I++II7777777?=7I77I=77I=,,,,,,,,,.....~?IIIIII?+=:,........................................
...........................................,,,,,,=7777777II77777?I?77+I7I,,,,,,,,.=+7777777777777777777777I?=====...........................
..........................................,,,,,,7II7777777777=?7II77???=~:..,,:,II77I?==+?I77777777777777IIIIII7:...........................
........................................,,,,,,,..===7777?=I=IIIII++++?I7III7I77~7II777777777777777777777777+................................
.......................................,,,,~7III7=I?...,~?I7?III=II..,,,,,,..~I~=?77I?+++?I7777777777I7777=.................................
..................................,,,,,,,,,~++III?+IIIIIIIIIIII7=====III7+~~?II77:,,,?7I?+?I7777777I++IIIII7................................
..........................,,,,,,+?III?7~~??IIII+7+==+:+?++?IIIIIIIIIIII?7=I?IIIIIIII=,,.,:,+77777?I7777=.,..................................
.......................,,,,,,,7IIIIII+?7III7I~~:III~?II=?III+:~IIIIIIIII?II,==:I7=II~,,,,,,,,.~7I77I=+7.....................................
...................::::::,,,,IIIIIIIIIIII:II=IIIIIII?I=I???~~+~I=I?II??I?I7II:III=?IIII+..I??I7,,,.+777.....................................
................,:::::::,,:~7IIIII7I7I7??I?=???I?IIII?+I?I=~I?,I?=??????IIII?I+??I=?=+III??IIII7.,,,,.......................................
.............:::::::,~+=+7IIIIIIII7?I7:???~?????~I???+~I??=~I?=~?I=+II~???????7????I=I??III7?II,,,,,:,,,:...................................
............:::::,+IIIIII?+II7II77777II??=????+?:I???+=????+?I+7+II=?:I?+??????=+????=???+IIIIIII:.7I~7?:,,,................................
...........,:::,7IIIIIIIIIIII7I777?+~77?I7???++=7?~+?++I~7?IIII~7+??~77??=I????~=+????=??:7IIII77I+?IIIIII=.,,:.............................
..............,7IIIIIIIII7777II++??++I??=????+=7II+??,I+=?+~~II?I777~~I???.I+??~=+?????+??+=?I7777IIII+?I7,:::::,,..........................
..................77IIIIII7I77777+++~I???~I??I~I.:=,~~=,,77I7+I+77I7I7~:...:?,I.+++:??????:?I+7777IIIIIIII7~,,::,,:.........................
...................IIII77777777?~+?==+I?+?+?++?+7:~=::~=~I777=+I7777I7I7=?=:::~.+::+?????+I==?~?7777777II???II7I7~::........................
....................,=+?II?=?7I???===?=+???+++=I7=~++~=?+7777?I777777777:==~~=:?=?+~===????++?+I77I?I77I77I7777777=.........................
...............................??+:=?=~???:~?+=I7II~=~~I7777777777777777~+I??+==+?+??:+=??+?=+~I77+7777:..:I777I=:..........................
...............................I??=??~??????=??I=IIIIII77777777I77777777IIII7=7+?????+:~+??=+=+?~?+~,.......................................
...............................~I????~???+???:IIII=7II777777777777777777II7+=I+I????I=+++?=+~+??............................................
..............................??+,??+~=?+:+???????I+II777I?+~~~~=I7I7777I+=I???????+=++~+??=???~............................................
.............................~?~+??+=+?:I~?:+????===7777777I=+??==777777+??7I??~=?+==?????+??==:............................................
.............................:I.+????:??:+:=~=???I,:I7I7777777777777777I~=+I++II?+?+??+??+???..,:...........................................
..............................~..?+??=?==I?~?+II?=+?=~==?=I77777777?=++=~==I===?I?++???+?++:?...............................................
...............................,..I++II:I??=+,I?~?~7III+~III?===?II?~IIII?~:=~=??I~I???+??+,.:,.............................................
....................................+I?III+I=I=?::~7777I=:~=???++==~I777I?:+~++~I~I??++?I:+,................................................
.....................................,.I++=I=~?:.I+II7I77777777777?I+7=+II=+=:.+,II?+~+=~:..................................................
....................................  7I:7?,.,,,=~=7~?7777777777777777..==?.,,,,.=II77777,..................................................
..................................I777777I~::::::7777=+77I?+====+?++I==.7~.~~::::.77777777,.................................................
.................................+ 77777II::::::,7777777  I+==+IIIIII7.~III7+++++++++++++++++++++++++++++++++++++++++=~.....................
................................=77777777I:::::::+777777777777?+?++.~7IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII?................
...............................I7777777777,,:::::,+777777777777~?:~IIIII====II:7?~7IIII+=III==~+III7~~III?IIIIII~:77?,=77III7+..............
...............................III77777777.::::::::::::,:::::,.7.:IIIIII....:....,.=7......?I+7II?,:....+?..7??IIII????I+I7III+.............
..............................++II77777777.::::::::::::~?+++???I.?IIIII7,,,,:.=:.==?III.?7:::::+III7..++++??II,:==.?~=7.?IIIII7.............
............................+7777777IIIII7,:::::::::::::.?+????I.+IIIII7====?......+II.=7?.IIIIIII.=.,I:?I:.7I,=?I.?:=I.?IIIIII.............
............................777777I=7I==?I,::::::::::::=I,:,...,,.?IIII7....?.......?.~II7~,..,~7I?..=I~..=7II,+==.?I:~.?IIII7..............
.............................:777?+~7777I7,:::::::::::,??+,:::::::.,IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII,...............
.................................:777+~+II~::::::::::,,,,::,,::::::,:,..=7I77777777777777777777777777777777777777777777?,...................
..................................+=IIIIIII.:::::::,,,,,,,,,,,,,,,,,,,,:::,,,,,,,.????????I,................................................
....................................7777777.,::::,,,,,,,,=.:,,,,,,,,,.??:,,,,::,,.7IIIIII77:................................................
....................................7IIIIII~.,,,,,,,,,,,==:.,,,,,,,,,,:~.,,,,,,,,.IIIIIIIII~................................................
............................................................................................................................................
............................................................................................................................................
............................................................................................................................................

	 */
}
