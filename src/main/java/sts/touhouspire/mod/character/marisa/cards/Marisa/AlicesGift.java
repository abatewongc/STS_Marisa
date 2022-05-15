package sts.touhouspire.mod.character.marisa.cards.Marisa;

import sts.touhouspire.mod.character.marisa.abstracts.MarisaCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.touhouspire.mod.character.marisa.data.Identifiers;
import sts.touhouspire.mod.character.marisa.patches.AbstractCardEnum;
import sts.touhouspire.mod.character.marisa.utils.AmplifyUtils;

public class AlicesGift extends MarisaCard {
	public static final String ID = Identifiers.Cards.ALICES_GIFT;
	public static final String IMG_PATH = "marisa/img/cards/GiftDoll_v2.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 0;
	private static final int ATK = 5;
	private static final int ATK_UPG = 2;
	private static final int AMP = 2;

	public AlicesGift() {
		super(
				ID,
				NAME,
				IMG_PATH,
				COST,
				DESCRIPTION,
				CardType.ATTACK,
				AbstractCardEnum.MARISA_COLOR,
				CardRarity.UNCOMMON,
				CardTarget.ENEMY
		);
	this.damage = this.baseDamage = ATK;
		this.exhaust = true;
		this.selfRetain = true;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeDamage(ATK_UPG);
		}
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (AmplifyUtils.Amplify(this, AMP)) {
			this.damage *= 3;
		}
		AbstractDungeon.actionManager.addToBottom(
				new DamageAction(
						m,
						new DamageInfo(p, this.damage, this.damageTypeForTurn),
						AttackEffect.FIRE
				)
		);
	}
}
