package sts.touhouspire.mod.character.marisa.powers.Marisa;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import sts.touhouspire.mod.character.marisa.MarisaModHandler;
import sts.touhouspire.mod.character.marisa.abstracts.OnAmplifySubscriber;
import sts.touhouspire.mod.character.marisa.action.DiscToHandATKOnly;
import sts.touhouspire.mod.character.marisa.data.Identifiers;

public class EventHorizonPower extends AbstractPower implements OnAmplifySubscriber {

	public static final String POWER_ID = Identifiers.Powers.EVENT_HORIZON;
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	private int count;

	public EventHorizonPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
		this.img = new Texture("marisa/img/powers/eventHorizon.png");
		this.count = amount;
		updateDescription();
	}

	public void atStartOfTurnPostDraw() {
		this.count = this.amount;
		updateDescription();
	}

	public void stackPower(int stackAmount) {
		super.stackPower(stackAmount);
		this.count += stackAmount;
		updateDescription();
	}

	public void onSpecificTrigger() {
		MarisaModHandler.logger.info("EventHorizonPower : Checking ; counter : " + this.count);
		if (this.count <= 0) {
			return;
		}

		MarisaModHandler.logger.info("EventHorizonPower : Action");
		AbstractPlayer player = AbstractDungeon.player;
		if (!player.discardPile.isEmpty()) {
			flash();
			AbstractDungeon.actionManager.addToBottom(
					new DiscToHandATKOnly(1)
			);
			this.count--;
			updateDescription();
		}

		MarisaModHandler.logger.info("EventHorizonPower : Done ; counter : " + this.count);
	}

	public void updateDescription() {
		this.description = (
				DESCRIPTIONS[0]
						+ this.amount
						+ DESCRIPTIONS[1]
						+ DESCRIPTIONS[2]
						+ this.count
						+ DESCRIPTIONS[3]
		);
	}

	@Override
	public void amplify() {
		this.onSpecificTrigger();
	}

}