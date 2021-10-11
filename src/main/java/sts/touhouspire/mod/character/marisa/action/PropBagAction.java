package sts.touhouspire.mod.character.marisa.action;

import com.badlogic.gdx.math.MathUtils;
import sts.touhouspire.mod.character.marisa.MarisaModHandler;
import sts.touhouspire.mod.character.marisa.data.Identifiers;
import sts.touhouspire.mod.character.marisa.powers.Marisa.PropBagPower;
import sts.touhouspire.mod.character.marisa.relics.AmplifyWand;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BlueCandle;
import com.megacrit.cardcrawl.relics.GremlinHorn;
import com.megacrit.cardcrawl.relics.Kunai;
import com.megacrit.cardcrawl.relics.LetterOpener;
import com.megacrit.cardcrawl.relics.MeatOnTheBone;
import com.megacrit.cardcrawl.relics.MercuryHourglass;
import com.megacrit.cardcrawl.relics.MummifiedHand;
import com.megacrit.cardcrawl.relics.OrnamentalFan;
import com.megacrit.cardcrawl.relics.Shuriken;
import com.megacrit.cardcrawl.relics.Sundial;

import java.util.ArrayList;
import java.util.HashMap;

public class PropBagAction extends AbstractGameAction {

	private static final HashMap<String, ? extends AbstractRelic> PROP_BAG_RELICS = new HashMap<String, AbstractRelic>() {{
		put(Identifiers.Relics.MEAT_ON_THE_BONE, new MeatOnTheBone());
		put(Identifiers.Relics.MUMMIFIED_HAND, new MummifiedHand());
		put(Identifiers.Relics.LETTER_OPENER, new LetterOpener());
		put(Identifiers.Relics.SHURIKEN, new Shuriken());
		put(Identifiers.Relics.GREMLIN_HORN, new GremlinHorn());
		put(Identifiers.Relics.SUNDIAL, new Sundial());
		put(Identifiers.Relics.MERCURY_HOURGLASS, new MercuryHourglass());
		put(Identifiers.Relics.ORNAMENTAL_FAN, new OrnamentalFan());
		put(Identifiers.Relics.KUNAI, new Kunai());
		put(Identifiers.Relics.BLUE_CANDLE, new BlueCandle());
		put(Identifiers.Relics.AMPLIFY_WAND, new AmplifyWand());
	}};

	public PropBagAction() {
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_FAST;
	}

	public void update() {
		AbstractPlayer player = AbstractDungeon.player;

		MarisaModHandler.logger.info("PropBagAction: Checking for relics");
		ArrayList<AbstractRelic> relics = new ArrayList<>();
		AbstractRelic relic;
		for (String relicId : PROP_BAG_RELICS.keySet()) {
			if (!player.hasRelic(relicId)) {
				relics.add(PROP_BAG_RELICS.get(relicId));
			}
		}

		if (relics.size() <= 0) {
			MarisaModHandler.logger.info("PropBagAction: No relic to give, returning");
			this.isDone = true;
			return;
		}
		if (relics.size() == 1) {
			relic = relics.get(0);
			MarisaModHandler.logger.info("PropBagAction : Only one relic to give : " + relic.relicId);
		} else {
			int index = MathUtils.random(0, relics.size() - 1);
			relic = relics.get(index);
			MarisaModHandler.logger.info("PropBagAction: random relic : " + relic.relicId + " ; random index : " + index);
		}

		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new PropBagPower(player, relic)));
		this.isDone = true;
	}
}