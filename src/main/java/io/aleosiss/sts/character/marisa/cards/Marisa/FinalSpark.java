package io.aleosiss.sts.character.marisa.cards.Marisa;


import static io.aleosiss.sts.character.marisa.patches.CardTagEnum.SPARK;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;

import io.aleosiss.sts.character.marisa.data.Identifiers;
import io.aleosiss.sts.character.marisa.patches.AbstractCardEnum;
import io.aleosiss.sts.character.marisa.abstracts.MarisaCard;

public class FinalSpark extends MarisaCard {

	public static final String ID = Identifiers.Cards.FINAL_SPARK;
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION;
	public static final String IMG_PATH = "marisa/img/cards/FinalSpark.png";

	private static final int COST = 7;
	private static final int ATK_DMG = 40;

	public FinalSpark() {
		super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnum.MARISA_COLOR, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ALL_ENEMY);
		this.isMultiDamage = true;
		this.baseDamage = ATK_DMG;
		this.tags.add(SPARK);
	}

	public void use(AbstractPlayer player, AbstractMonster monster) {

		AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
		AbstractDungeon.actionManager.addToBottom(new VFXAction(new MindblastEffect(player.dialogX, player.dialogY, false)));

		AbstractDungeon.actionManager.addToBottom(
				new DamageAllEnemiesAction(
						player,
						this.multiDamage,
						this.damageTypeForTurn,
						AbstractGameAction.AttackEffect.SLASH_DIAGONAL
				)
		);
		if (!this.freeToPlayOnce) {
			AbstractDungeon.actionManager.addToBottom(
					new GainEnergyAction(-this.costForTurn)
			);
			this.freeToPlayOnce = true;
		}
		this.upgradeBaseCost(COST);
		this.setCostForTurn(COST);
		this.isCostModified = false;
	}

	public AbstractCard makeCopy() {
		return new FinalSpark();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.rawDescription = DESCRIPTION_UPG;
			this.selfRetain = true;
			initializeDescription();
		}
	}
}