package io.aleosiss.sts.character.marisa.powers.Marisa;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import io.aleosiss.sts.character.marisa.MarisaModHandler;
import io.aleosiss.sts.character.marisa.cards.derivations.GuidingStar;

import com.megacrit.cardcrawl.localization.PowerStrings;
import io.aleosiss.sts.character.marisa.data.Identifiers;

public class PolarisUniquePower extends AbstractPower {

	public static final String POWER_ID = Identifiers.Powers.POLARIS_UNIQUE;
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	private final AbstractPlayer player;
	public boolean gain;

	public PolarisUniquePower(AbstractCreature owner) {
		MarisaModHandler.logger.info("PolarisUniquePower : Init");
		this.name = NAME;
		this.ID = POWER_ID;
		this.type = AbstractPower.PowerType.BUFF;
		updateDescription();
		this.img = new Texture("marisa/img/powers/transmute.png");
		this.player = AbstractDungeon.player;
		this.gain = false;
		this.owner = owner;
	}


	public void stackPower(int stackAmount) {
		MarisaModHandler.logger.info("PolarisUniquePower : StackPower");
	}

	public void atStartOfTurnPostDraw() {
		MarisaModHandler.logger.info("PolarisUniquePower : Checking");

		for (AbstractCard c : player.drawPile.group) {
			if (c instanceof GuidingStar) {
				this.gain = true;
			}
		}
		MarisaModHandler.logger.info("PolarisUniquePower : Result : " + gain);
		if (gain) {
			flash();
			AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
		}
		this.gain = false;

		MarisaModHandler.logger.info("PolarisUniquePower : Done Checking");
	}

	public void updateDescription() {
		MarisaModHandler.logger.info("PolarisUniquePower : updating Description");
		this.description = (DESCRIPTIONS[0]);
		MarisaModHandler.logger.info("PolarisUniquePower : Done updating Description");
	}
}