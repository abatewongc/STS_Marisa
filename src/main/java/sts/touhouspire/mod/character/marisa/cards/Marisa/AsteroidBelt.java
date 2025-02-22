package sts.touhouspire.mod.character.marisa.cards.Marisa;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sts.touhouspire.mod.character.marisa.abstracts.MarisaCard;
import sts.touhouspire.mod.character.marisa.data.Identifiers;
import sts.touhouspire.mod.character.marisa.patches.AbstractCardEnum;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import sts.touhouspire.mod.character.marisa.utils.AmplifyUtils;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager;

public class AsteroidBelt extends MarisaCard {

	public static final String ID = Identifiers.Cards.ASTEROID_BELT;
	public static final String IMG_PATH = "marisa/img/cards/Asteroid.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int BLOCK_AMT = 8;
	private static final int UPGRADE_PLUS_BLOCK = 3;
	private static final int AMP = 1;


	public AsteroidBelt() {
		super(
				ID,
				NAME,
				IMG_PATH,
				COST,
				DESCRIPTION,
				AbstractCard.CardType.SKILL,
				AbstractCardEnum.MARISA_COLOR,
				AbstractCard.CardRarity.COMMON,
				AbstractCard.CardTarget.SELF
		);

		this.baseBlock = BLOCK_AMT;
	}

	public void use(AbstractPlayer player, AbstractMonster m) {
		actionManager.addToBottom(new GainBlockAction(player, player, this.block));
		if (AmplifyUtils.Amplify(this, AMP)) {
			actionManager.addToBottom(new ApplyPowerAction(player, player, new NextTurnBlockPower(player, this.block), this.block));
		}
	}

	public AbstractCard makeCopy() {
		return new AsteroidBelt();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeBlock(UPGRADE_PLUS_BLOCK);
		}
	}
}