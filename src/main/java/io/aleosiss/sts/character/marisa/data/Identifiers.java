package io.aleosiss.sts.character.marisa.data;


import io.aleosiss.sts.character.marisa.utils.MarisaHelpers;

import static io.aleosiss.sts.character.marisa.utils.MarisaHelpers.*;

public class Identifiers {
	public static class Properties extends Identifiers {
		public static final String IS_CAT_EVENT_ENABLED = "isCatEventEnabled";
	}

	public static class Powers extends Identifiers {
		// Marisa
		public static final String BLAZE_AWAY            = makeID("BlazeAwayPower");
		public static final String CASKET_OF_STARS       = makeID("CasketOfStarPower");
		public static final String CHARGE_UP             = makeID("ChargeUpPower");
		public static final String DARK_MATTER           = makeID("DarkMatterPower");
		public static final String ENERGY_FLOW           = makeID("EnergyFlowPower");
		public static final String ESCAPE_VELOCITY       = makeID("ExtraDraw");
		public static final String EVENT_HORIZON         = makeID("EventHorizonPower");
		public static final String GRAND_CROSS           = makeID("GrandCrossPower");
		public static final String MANA_RAMPAGE          = makeID("ManaRampagePower");
		public static final String MILLISECOND_PULSARS   = makeID("MilliPulsaPower");
		public static final String MAXIMIZE_POWER        = makeID("MPPower");
		public static final String ONE_TIME_OFF          = makeID("OneTimeOffPlusPower");
		public static final String ORRERYS_SUN           = makeID("OrrerysSunPower");
		public static final String POLARIS_UNIQUE        = makeID("PolarisUniquePower");
		public static final String PROP_BAG              = makeID("PropBagPower");
		public static final String PULSE_MAGIC           = makeID("PulseMagicPower");
		public static final String SATELLITE_ILLUSION    = makeID("SatelIllusPower");
		public static final String SINGULARITY           = makeID("SingularityPower");
		public static final String SUPERNOVA             = makeID("SuperNovaPower");
		public static final String WITCH_OF_GREED_POTION = makeID("WitchOfGreedPotion");
		public static final String WITCH_OF_GREED_GOLD   = makeID("WitchOfGreedGold");

		// Monsters
		public static final String WRAITH                = makeID("Wraith");
		public static final String LIMBO_CONTACT         = makeID("LimboContact");
		public static final String INFERNO_CLAW          = makeID("InfernoClaw");
	}

	public static class Relics {
		public static final String SIMPLE_LAUNCHER          = makeID("SimpleLauncher");
		public static final String AMPLIFY_WAND             = makeID("AmplifyWand");
		public static final String BEWITCHED_HAKKERO        = makeID("BewitchedHakkero");
		public static final String BIG_SHROOM_BAG           = makeID("BigShroomBag");
		public static final String BREAD_OF_A_WASHOKU_LOVER = makeID("BreadOfAWashokuLover");
		public static final String CAT_CART                 = makeID("CatCart");
		public static final String EXPERIMENTAL_FAMILIAR    = makeID("ExperimentalFamiliar");
		public static final String HANDMADE_GRIMOIRE        = makeID("HandmadeGrimoire");
		public static final String MAGIC_BROOM              = makeID("MagicBroom");
		public static final String MINI_HAKKERO             = makeID("MiniHakkero");
		public static final String RAMPAGING_MAGIC_TOOLS    = makeID("RampagingMagicTools");
		public static final String SHROOM_BAG               = makeID("ShroomBag");
		public static final String SPROUTING_BRANCH         = makeID("SproutingBranch");

		//     non-Marisa
		public static final String PHILO_STONE              = "Philosopher's Stone";
		public static final String CIRCLET                  = "Circlet";
		public static final String RED_CIRCLET              = "Red Circlet";
		public static final String CHEMICAL_X               = "Chemical X";
		public static final String WRIST_BLADE              = "WristBlade";
		public static final String MEDICAL_KIT              = "Medical Kit";
		public static final String MARK_OF_THE_BLOOM        = "Mark of the Bloom";

		// Portable Prop Bag (-Amplify Wand)
		public static final String LETTER_OPENER            = "Letter Opener";
		public static final String MEAT_ON_THE_BONE         = "Meat on the Bone";
		public static final String MUMMIFIED_HAND           = "Mummified Hand";
		public static final String SHURIKEN                 = "Shuriken";
		public static final String GREMLIN_HORN             = "Gremlin Horn";
		public static final String SUNDIAL                  = "Sundial";
		public static final String MERCURY_HOURGLASS        = "Mercury Hourglass";
		public static final String ORNAMENTAL_FAN           = "Ornamental Fan";
		public static final String KUNAI                    = "Kunai";
		public static final String BLUE_CANDLE              = "Blue Candle";

	}

	public static class Potions extends Identifiers {
		public static final String BOTTLED_SPARK    = makeID("BottledSpark");
		public static final String SHROOM_BREW      = makeID("ShroomBrew");
		public static final String STAR_N_LOVE      = makeID("StarNLove");
	}

	public static class Monsters extends Identifiers {
		public static final String ORIN             = makeID("Orin");
		public static final String ZOMBIE_FAIRY     = makeID("ZombieFairy");
	}

	public static class Cards extends Identifiers {
		public static final String ABSOLUTE_MAGNITUDE    = makeID("AbsoluteMagnitude");
		public static final String ACCELERATION          = makeID("Acceleration");
		public static final String ALICES_GIFT           = makeID("AlicesGift");
		public static final String ASTEROID_BELT         = makeID("AsteroidBelt");
		public static final String BIG_CRUNCH            = makeID("BigCrunch");
		public static final String BINARY_STARS          = makeID("BinaryStars");
		public static final String BLAZE_AWAY            = makeID("BlazeAway");
		public static final String BLAZING_STAR          = makeID("BlazingStar");
		public static final String CASKET_OF_STAR        = makeID("CasketOfStar");
		public static final String CHARGE_UP_SPRAY       = makeID("ChargeUpSpray");
		public static final String CHARGING_UP           = makeID("ChargingUp");
		public static final String COLLECTING_QUIRK      = makeID("CollectingQuirk");
		public static final String DARK_MATTER           = makeID("DarkMatter");
		public static final String DARK_SPARK            = makeID("DarkSpark");
		public static final String DEEP_ECOLOGICAL_BOMB  = makeID("DeepEcologicalBomb");
		public static final String DEFEND                = makeID("Defend");
		public static final String DOUBLE_SPARK          = makeID("DoubleSpark");
		public static final String DRAGON_METEOR         = makeID("DragonMeteor");
		public static final String EARTHLIGHT_RAY        = makeID("EarthLightRay");
		public static final String ENERGY_FLOW           = makeID("EnergyFlow");
		public static final String ENERGY_RECOIL         = makeID("EnergyRecoil");
		public static final String ESCAPE_VELOCITY       = makeID("EscapeVelocity");
		public static final String EVENT_HORIZON         = makeID("EventHorizon");
		public static final String FAIRY_DESTRUCTION_RAY = makeID("FairyDestructionRay");
		public static final String FINAL_SPARK           = makeID("FinalSpark");
		public static final String GALACTIC_HALO         = makeID("GalacticHalo");
		public static final String GAS_GIANT             = makeID("GasGiant");
		public static final String GRAND_CROSS           = makeID("GrandCross");
		public static final String GRAVITY_BEAT          = makeID("GravityBeat");
		public static final String ILLUSION_STAR         = makeID("IllusionStar");
		public static final String LUMINOUS_STRIKE       = makeID("LuminesStrike");
		public static final String MACHINE_GUN_SPARK     = makeID("MachineGunSpark");
		public static final String MAGIC_ABSORBER        = makeID("MagicAbsorber");
		public static final String MAGIC_CHANT           = makeID("MagicChant");
		public static final String MANA_CONVECTION       = makeID("ManaConvection");
		public static final String MANA_RAMPAGE          = makeID("ManaRampage");
		public static final String MASTER_SPARK          = makeID("MasterSpark");
		public static final String MAXIMIZE_POWER        = makeID("MaximisePower");
		public static final String METEORIC_SHOWER       = makeID("MeteoricShower");
		public static final String MILKY_WAY             = makeID("MilkyWay");
		public static final String MILLISECOND_PULSARS   = makeID("MillisecondPulsars");
		public static final String MYSTERIOUS_BEAM       = makeID("MysteriousBeam");
		public static final String NONDIRECTIONAL_LASER  = makeID("NonDirectionalLaser");
		public static final String OCCULTATION           = makeID("Occultation");
		public static final String ONE_TIME_OFF          = makeID("OneTimeOff");
		public static final String OORT_CLOUD            = makeID("OortCloud");
		public static final String OPEN_UNIVERSE         = makeID("OpenUniverse");
		public static final String ORBITAL               = makeID("Orbital");
		public static final String ORRERYS_SUN           = makeID("OrrerysSun");
		public static final String UPGRADE               = makeID("PowerUp");
		public static final String PROP_BAG              = makeID("PropBag");
		public static final String PULSE_MAGIC           = makeID("PulseMagic");
		public static final String REBOUND               = makeID("Rebound");
		public static final String REFRACTION_SPARK      = makeID("RefractionSpark");
		public static final String ROBBERY               = makeID("Robbery");
		public static final String SATELLITE_ILLUSION    = makeID("SatelliteIllusion");
		public static final String SHOOTING_ECHO         = makeID("ShootingEcho");
		public static final String SHOOT_THE_MOON        = makeID("ShootTheMoon");
		public static final String SINGULARITY           = makeID("Singularity");
		public static final String SPORE_CRUMP           = makeID("SporeBomb");
		public static final String SPRINKLE_STAR_SEAL    = makeID("SprinkleStarSeal");
		public static final String STAR_BARRAGE          = makeID("StarBarrage");
		public static final String STARDUST_REVERIE      = makeID("StarDustReverie");
		public static final String STARLIGHT_TYPHOON     = makeID("StarlightTyphoon");
		public static final String STRIKE                = makeID("Strike");
		public static final String SUPERNOVA             = makeID("SuperNova");
		public static final String SUPER_PERSEIDS        = makeID("SuperPerseids");
		public static final String SURPRISE              = makeID("Surprise");
		public static final String TREASURE_HUNTER       = makeID("TreasureHunter");
		public static final String ULTRA_SHORTWAVE       = makeID("UltimateShortwave");
		public static final String UNSTABLE_BOMB         = makeID("UnstableBomb");
		public static final String UPSWEEP               = makeID("UpSweep");
		public static final String WARMING_UP            = makeID("WarmingUp");
		public static final String WITCH_LEYLINE         = makeID("WitchLeyline");
		public static final String WITCH_OF_GREED        = makeID("WitchOfGreed");
		public static final String BLACK_FLARE_STAR      = makeID("BlackFlareStar");
		public static final String EXHAUSTION            = makeID("Exhaustion");
		public static final String GUIDING_STAR          = makeID("GuidingStar");
		public static final String SPARK                 = makeID("Spark");
		public static final String WHITE_DWARF_STAR      = makeID("WhiteDwarf");
		public static final String WRAITH                = makeID("Wraith");

	}

	public static class Events extends Identifiers {
		public static final String MUSHROOMS        = makeID("Mushrooms");
		public static final String ORIN_THE_CAT     = makeID("OrinTheCat");
	}

	public static class Encounters extends Identifiers {
		public static final String ORIN_ENCOUNTER           = makeID("Orin");
		public static final String ZOMBIE_FAIRY_ENCOUNTER   = makeID("ZombieFairy");
	}

	public static class Mods extends Identifiers {
		public static final String LETHALITY        = "Lethality";
		public static final String TIME_DILATION    = "Time Dilation";
    }
}
