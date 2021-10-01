package io.aleosiss.sts.character.marisa.powers.Marisa;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import io.aleosiss.sts.character.marisa.MarisaModHandler;
import io.aleosiss.sts.character.marisa.data.Identifiers;

public class PropBagPower extends AbstractPower {

	public static final String POWER_ID = Identifiers.Powers.PROP_BAG;
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	private AbstractRelic relic;
	private static int IdOffset;
	private AbstractPlayer player;
	private String relicName;

	public PropBagPower(AbstractCreature owner, AbstractRelic relic) {
		this.name = NAME;
		this.ID = POWER_ID + IdOffset;
		this.owner = owner;
		IdOffset++;
		this.amount = -1;
		this.type = AbstractPower.PowerType.BUFF;
		this.img = new Texture("marisa/img/powers/diminish.png");
		this.relic = relic;
		this.player = AbstractDungeon.player;
		this.relicName = relic.name;
		MarisaModHandler.logger.info("PropBagPower : Granting relic : " + this.relicName);
		AbstractDungeon.getCurrRoom().spawnRelicAndObtain(
				Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, relic
		);
		relic.atBattleStart();
		updateDescription();
	}

	@Override
	public void stackPower(int stackAmount) {

	}

	public void onVictory() {
		player.loseRelic(relic.relicId);
	}

	public void updateDescription() {
		this.description = (DESCRIPTIONS[0] + this.relicName + DESCRIPTIONS[1]);
	}
}