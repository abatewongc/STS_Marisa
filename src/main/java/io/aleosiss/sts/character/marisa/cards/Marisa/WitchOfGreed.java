package io.aleosiss.sts.character.marisa.cards.Marisa;

import io.aleosiss.sts.character.marisa.MarisaModHandler;
import io.aleosiss.sts.character.marisa.data.Identifiers;
import io.aleosiss.sts.character.marisa.powers.Marisa.WitchOfGreedGold;
import io.aleosiss.sts.character.marisa.powers.Marisa.WitchOfGreedPotion;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import io.aleosiss.sts.character.marisa.abstracts.MarisaCard;
import io.aleosiss.sts.character.marisa.patches.AbstractCardEnum;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import io.aleosiss.sts.character.marisa.utils.AmplifyUtils;

public class WitchOfGreed extends MarisaCard {

	public static final String ID = Identifiers.Cards.WITCH_OF_GREED;
	public static final String IMG_PATH = "marisa/img/cards/Greed.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int STC = 15;
	private static final int UPG_STC = 10;

	private static final int AMP = 1;

	public WitchOfGreed() {
		super(
				ID,
				NAME,
				IMG_PATH,
				COST,
				DESCRIPTION,
				AbstractCard.CardType.POWER,
				AbstractCardEnum.MARISA_COLOR,
				AbstractCard.CardRarity.COMMON,
				AbstractCard.CardTarget.SELF
		);

		this.baseMagicNumber = this.magicNumber = STC;
		this.tags.add(AbstractCard.CardTags.HEALING);
	}


	public void use(AbstractPlayer p, AbstractMonster m) {
		if (AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT) {

			AbstractDungeon.getCurrRoom().addGoldToRewards(this.magicNumber);
			AbstractDungeon.actionManager.addToBottom(
					new ApplyPowerAction(
							p,
							p,
							new WitchOfGreedGold(p, this.magicNumber),
							this.magicNumber
					)
			);

			if (AmplifyUtils.Amplified(this, AMP)) {
				AbstractPotion po = AbstractDungeon.returnRandomPotion();
				AbstractDungeon.getCurrRoom().addPotionToRewards(po);
				AbstractDungeon.actionManager.addToBottom(
						new ApplyPowerAction(
								p,
								p,
								new WitchOfGreedPotion(p, 1),
								1
						)
				);
				MarisaModHandler.logger.info("WitchOfGreed : use : Amplified : adding :" + po.ID);
			}

		}

	}

	public AbstractCard makeCopy() {
		return new WitchOfGreed();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(UPG_STC);
		}
	}
}