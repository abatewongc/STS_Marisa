package io.aleosiss.sts.character.marisa.powers.Marisa.deprecated;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import io.aleosiss.sts.character.marisa.data.Identifiers;

public class ManaRampagePower extends AbstractPower {

	public static final String POWER_ID = Identifiers.Powers.MANA_RAMPAGE;
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public ManaRampagePower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.img = new Texture("marisa/img/powers/doubleTap.png");
	}

	public void updateDescription() {
		this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
	}

	public void onUseCard(AbstractCard card, UseCardAction action) {
		if ((!card.purgeOnUse) && (card.type == AbstractCard.CardType.ATTACK) && (this.amount > 0)) {
			flash();
			for (int i = 0; i < this.amount; i++) {
				AbstractMonster monster = null;
				if (action.target != null) {
					monster = (AbstractMonster) action.target;
				}
				AbstractCard generatedCard = card.makeStatEquivalentCopy();
				AbstractDungeon.player.limbo.addToBottom(generatedCard);
				generatedCard.current_x = card.current_x;
				generatedCard.current_y = card.current_y;
				generatedCard.target_x = (Settings.WIDTH / 2.0F - 300.0F * Settings.scale);
				generatedCard.target_y = (Settings.HEIGHT / 2.0F);
				generatedCard.freeToPlayOnce = true;
				if (monster != null) {
					generatedCard.calculateCardDamage(monster);
				}
				generatedCard.purgeOnUse = true;
				AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(generatedCard, monster, card.energyOnUse));
			}
			AbstractDungeon.actionManager
					.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
		}
	}

	public void atEndOfTurn(boolean isPlayer) {
		if (isPlayer) {
			AbstractDungeon.actionManager
					.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
		}
	}
}
