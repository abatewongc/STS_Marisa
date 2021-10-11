package sts.touhouspire.mod.character.marisa.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import sts.touhouspire.mod.character.marisa.data.Identifiers;
import sts.touhouspire.mod.character.marisa.powers.Marisa.ChargeUpPower;
import basemod.abstracts.CustomRelic;

public class RampagingMagicTools extends CustomRelic {

	public static final String ID = Identifiers.Relics.RAMPAGING_MAGIC_TOOLS;
	private static final String IMG = "marisa/img/relics/RamTool.png";
	private static final String IMG_OTL = "marisa/img/relics/outline/RamTool.png";

	private static final int frailAmount = 2;
	private static final int weakAmount = 2;
	private static final int vulnAmount = 2;
	private static final int poisonAmount = 3;
	private static final int chargeAmount = 8;

	public RampagingMagicTools() {
		super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.BOSS, LandingSound.FLAT);
	}

	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	public AbstractRelic makeCopy() {
		return new RampagingMagicTools();
	}

	public void onEquip() {
		AbstractDungeon.player.energy.energyMaster += 1;
	}

	public void onUnequip() {
		AbstractDungeon.player.energy.energyMaster -= 1;
	}

	public void atBattleStart() {
		int rng = AbstractDungeon.miscRng.random(0, 4);
		AbstractPower battleStartPower = null;
		AbstractPlayer p = AbstractDungeon.player;
		switch (rng) {
			case 0:
				battleStartPower = new FrailPower(p, frailAmount, false);
				break;
			case 1:
				battleStartPower = new WeakPower(p, weakAmount, false);
				break;
			case 2:
				battleStartPower = new VulnerablePower(p, vulnAmount, false);
				break;
			case 3:
				battleStartPower = new PoisonPower(p, p, poisonAmount);
				break;
			case 4:
				battleStartPower = new ChargeUpPower(p, chargeAmount);
				break;
			default:
				break;
		}
		if (battleStartPower != null) {
			AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, battleStartPower, battleStartPower.amount));
		}
	}
}