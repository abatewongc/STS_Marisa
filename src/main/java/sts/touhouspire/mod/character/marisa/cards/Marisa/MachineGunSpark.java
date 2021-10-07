package sts.touhouspire.mod.character.marisa.cards.Marisa;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sts.touhouspire.mod.character.marisa.abstracts.MarisaCard;
import sts.touhouspire.mod.character.marisa.data.Identifiers;
import sts.touhouspire.mod.character.marisa.patches.AbstractCardEnum;
import sts.touhouspire.mod.character.marisa.patches.CardTagEnum;

public class MachineGunSpark extends MarisaCard {

	public static final String ID = Identifiers.Cards.MACHINE_GUN_SPARK;
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String IMG_PATH = "marisa/img/cards/MachineGunSpark.png";
	private static final int COST = 1;
	private static final int ATTACK_DMG = 1;
	private static final int CNT = 6;
	private static final int UPG_CNT = 2;

	public MachineGunSpark() {
		super(
				ID,
				NAME,
				IMG_PATH,
				COST,
				DESCRIPTION,
				AbstractCard.CardType.ATTACK,
				AbstractCardEnum.MARISA_COLOR,
				AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.ENEMY
		);

		this.baseDamage = ATTACK_DMG;
		this.magicNumber = this.baseMagicNumber = CNT;
		this.exhaust = true;
		this.tags.add(CardTagEnum.SPARK);
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		for (int i = 0; i < this.magicNumber; i++) {
			AbstractDungeon.actionManager.addToBottom(
					new DamageAction(
							m,
							new DamageInfo(p, this.damage, this.damageTypeForTurn),
							AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
							true
					)
			);
		}
	}

	public AbstractCard makeCopy() {
		return new MachineGunSpark();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(UPG_CNT);
		}
	}
}