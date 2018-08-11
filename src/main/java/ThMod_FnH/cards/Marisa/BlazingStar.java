package ThMod_FnH.cards.Marisa;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import ThMod_FnH.ThMod;
import ThMod_FnH.abstracts.AmplifiedAttack;
import ThMod_FnH.cards.special.Burn_MRS;
import ThMod_FnH.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;

public class BlazingStar 
	extends CustomCard {
	
	public static final String ID = "BlazingStar";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String IMG_PATH = "img/cards/Strike.png";
	
	private static final int COST = 2;
	private static final int ATK_DMG = 14;
	private static final int UPG_DMG = 4;
	private static final int AMP_DMG = 7;
	private static final int UPG_AMP = 2;
	private static final int AMP = 1;
	
	public BlazingStar() {
		super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.MARISA_COLOR, AbstractCard.CardRarity.RARE,
				AbstractCard.CardTarget.ENEMY);

		this.baseDamage = ATK_DMG;
		this.magicNumber = this.baseMagicNumber = AMP_DMG;
	}
	
	public void applyPowers(){
		AbstractPlayer p = AbstractDungeon.player;
		this.baseDamage = ATK_DMG;
		if (this.upgraded) {
			this.baseDamage += UPG_DMG;
		}
		for (AbstractCard c:p.hand.group) {
			if ((c instanceof Burn)||(c instanceof Burn_MRS)) {
				this.baseDamage += this.magicNumber;
			}
		}
		super.applyPowers();
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		if (ThMod.Amplified(AMP+this.costForTurn,AMP)) {
			this.damage *=2;
		}
		AbstractDungeon.actionManager.addToBottom(
				new DamageAction(
						m,
						new DamageInfo(p, this.damage, this.damageTypeForTurn),
						AbstractGameAction.AttackEffect.SLASH_DIAGONAL
						)
				);
	    
	}

	public AbstractCard makeCopy() {
		return new BlazingStar();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeDamage(UPG_DMG);
			upgradeMagicNumber(UPG_AMP);
		}
	}
}