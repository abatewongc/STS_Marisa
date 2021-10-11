package sts.touhouspire.mod.character.marisa.cards.Marisa;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;

import sts.touhouspire.mod.character.marisa.abstracts.AmplifiedAttack;
import sts.touhouspire.mod.character.marisa.data.Identifiers;
import sts.touhouspire.mod.character.marisa.patches.AbstractCardEnum;
import sts.touhouspire.mod.character.marisa.utils.AmplifyUtils;
import sts.touhouspire.mod.character.marisa.patches.CardTagEnum;

public class MasterSpark extends AmplifiedAttack {

	public static final String ID = Identifiers.Cards.MASTER_SPARK;
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String IMG_PATH = "marisa/img/cards/MasterSpark.png";

	private static final int COST = 1;
	private static final int ATK_DMG = 8;
	private static final int UPG_DMG = 3;
	private static final int AMP_DMG = 7;
	private static final int UPG_AMP = 2;
	private static final int AMP = 1;


	public MasterSpark() {
		super(
				ID,
				NAME,
				IMG_PATH,
				COST,
				DESCRIPTION,
				AbstractCard.CardType.ATTACK,
				AbstractCardEnum.MARISA_COLOR,
				AbstractCard.CardRarity.BASIC,
				AbstractCard.CardTarget.ENEMY
		);
		this.baseDamage = ATK_DMG;
		this.ampNumber = AMP_DMG;
		this.baseBlock = this.baseDamage + this.ampNumber;
		this.tags.add(CardTagEnum.SPARK);
	}

	public void use(AbstractPlayer player, AbstractMonster monster) {

		AbstractDungeon.actionManager.addToBottom(
				new VFXAction(
						new MindblastEffect(player.dialogX, player.dialogY, false)
				)
		);

		if (AmplifyUtils.Amplified(this, AMP)) {
			AbstractDungeon.actionManager.addToBottom(
					new DamageAction(
							monster,
							new DamageInfo(player, this.block, this.damageTypeForTurn),
							AbstractGameAction.AttackEffect.SLASH_DIAGONAL
					)
			);
		} else {
			AbstractDungeon.actionManager.addToBottom(
					new DamageAction(
							monster,
							new DamageInfo(player, this.damage, this.damageTypeForTurn),
							AbstractGameAction.AttackEffect.SLASH_DIAGONAL
					)
			);
		}
	}

	public AbstractCard makeCopy() {
		return new MasterSpark();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeDamage(UPG_DMG);
			this.ampNumber += UPG_AMP;
			this.block = this.baseDamage + this.ampNumber;
			this.isBlockModified = true;
		}
	}
}