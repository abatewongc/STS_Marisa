package sts.touhouspire.mod.character.marisa.cards.derivations;

import sts.touhouspire.mod.character.marisa.abstracts.AmplifiedAttack;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.touhouspire.mod.character.marisa.data.Identifiers;

public class WhiteDwarfStar extends AmplifiedAttack {

	public static final String ID = Identifiers.Cards.WHITE_DWARF_STAR;
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION;
	private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	public static final String IMG_PATH = "marisa/img/cards/Marisa/WhiteDwarf.png";
	private static final int COST = 0;
	private static final int ATTACK_DAMAGE = 0;
	private static final int HANDSIZE_REQUIRED = 4;
	private static final float MULTIPLIER = 2f;
	private static final float MULTIPLIER_UPG = 3f;

	private float magn = MULTIPLIER;

	public WhiteDwarfStar() {
		super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.ATTACK, CardColor.COLORLESS, AbstractCard.CardRarity.SPECIAL, AbstractCard.CardTarget.ENEMY);
		this.baseDamage = this.damage = ATTACK_DAMAGE;
		this.exhaust = true;
	}

	@Override
	public void applyPowers() {
		AbstractPlayer player = AbstractDungeon.player;
		this.ampNumber = (int) (Math.floor(player.discardPile.size() * this.magn));
		super.applyPowers();
	}

	@Override
	public void calculateDamageDisplay(AbstractMonster mo) {
		calculateCardDamage(mo);
	}

	@Override
	public void calculateCardDamage(AbstractMonster mo) {
		AbstractPlayer player = AbstractDungeon.player;
		this.ampNumber = (int) (Math.floor(player.discardPile.size() * this.magn));
		super.calculateCardDamage(mo);
	}

	public boolean canUse(AbstractPlayer p, AbstractMonster m) {
		if (p.hand.size() <= HANDSIZE_REQUIRED) {
			return true;
		} else {
			this.cantUseMessage = EXTENDED_DESCRIPTION[0];
			return false;
		}
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
				new DamageAction(
						m,
						new DamageInfo(p, this.block, this.damageTypeForTurn),
						AttackEffect.SLASH_DIAGONAL
				)
		);
		AbstractDungeon.actionManager.addToBottom(
				new MakeTempCardInHandAction(new Burn(), 2)
		);
	}

	public AbstractCard makeCopy() {
		return new WhiteDwarfStar();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.magn = MULTIPLIER_UPG;
			this.rawDescription = DESCRIPTION_UPG;
			initializeDescription();
		}
	}
}