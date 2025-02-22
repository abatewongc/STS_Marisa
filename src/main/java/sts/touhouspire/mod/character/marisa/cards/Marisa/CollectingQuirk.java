package sts.touhouspire.mod.character.marisa.cards.Marisa;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import sts.touhouspire.mod.character.marisa.MarisaModHandler;
import sts.touhouspire.mod.character.marisa.action.UnstableBombAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.touhouspire.mod.character.marisa.data.Identifiers;
import sts.touhouspire.mod.character.marisa.patches.AbstractCardEnum;
import sts.touhouspire.mod.character.marisa.abstracts.MarisaCard;
import sts.touhouspire.mod.character.marisa.utils.MarisaHelpers;
import sts.touhouspire.mod.character.marisa.vfx.CollectingQuirkEffect;

import static sts.touhouspire.mod.character.marisa.data.Identifiers.*;

public class CollectingQuirk extends MarisaCard {

	public static final String ID = Cards.COLLECTING_QUIRK;
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	public static final String IMG_PATH = "marisa/img/cards/collectingquirk.png";
	private static final int COST = 2;
	private static final int DIVIDER = 4;
	private static final int UPG_DIVIDER = 3;
	private static final int ATK_DMG = 9;
	private int counter;

	public CollectingQuirk() {
		super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnum.MARISA_COLOR, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ALL_ENEMY);
		this.baseDamage = ATK_DMG;
		this.magicNumber = this.baseMagicNumber = DIVIDER;
		this.block = this.baseBlock = 0;
		this.counter = 0;
	}

	@Override
	public void applyPowers() {
		super.applyPowers();
		getNumberOfHits();
		modifyBlock();
		this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
		initializeDescription();
		MarisaModHandler.logger.info(
				"CollectingQuirk : applyPowers : damage :"
						+ this.damage
						+ " ; counter : " + this.counter
						+ " ; block :" + this.block
						+ " ; magic number :" + this.magicNumber
		);
	}

	@Override
	public void calculateCardDamage(AbstractMonster monster) {
		//super.calculateCardDamage(mo);
		getNumberOfHits();
		modifyBlock();
		this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
		initializeDescription();
		MarisaModHandler.logger.info(
				"CollectingQuirk : applyPowers : damage :"
						+ this.damage
						+ " ; counter : " + this.counter
						+ " ; block :" + this.block
						+ " ; magic number :" + this.magicNumber
		);
	}

	public void use(AbstractPlayer player, AbstractMonster monster) {
		counter = getNumberOfHits();
		if (counter > 0) {
			float duration = Settings.FAST_MODE ? 0.5f : 1.0f;
			MonsterGroup monsters = AbstractDungeon.getMonsters();
			this.addToBot(new VFXAction(new CollectingQuirkEffect(monsters.shouldFlipVfx(), (float) monsters.monsters.stream().mapToDouble(m -> m.drawX).average().orElse(Settings.WIDTH)), duration));
			this.addToBot(new UnstableBombAction(MarisaHelpers.getRandomMonster(), this.damage, this.damage, this.counter));
		}
	}

	public void onMoveToDiscard() {
		this.rawDescription = DESCRIPTION;
		initializeDescription();
	}

	public AbstractCard makeCopy() {
		return new CollectingQuirk();
	}

	private int getNumberOfHits() {
		AbstractPlayer player = AbstractDungeon.player;
		int divider = DIVIDER;
		if (this.upgraded) {
			divider = UPG_DIVIDER;
		}

		counter = player.relics.size();
		if (player.hasRelic(Relics.CIRCLET)) {
			counter += player.getRelic(Relics.CIRCLET).counter - 1;
		}
		if (player.hasRelic(Relics.RED_CIRCLET)) {
			counter += player.getRelic(Relics.RED_CIRCLET).counter - 1;
		}

		counter /= divider;

		return counter;
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.magicNumber = this.baseMagicNumber = UPG_DIVIDER;
			this.upgradedMagicNumber = true;
		}
	}

	private void modifyBlock() {
		if (this.counter > 0) {
			this.isBlockModified = true;
			this.block = this.baseBlock = this.counter;
		} else {
			this.isBlockModified = false;
			this.block = this.baseBlock = 0;
		}
	}
}