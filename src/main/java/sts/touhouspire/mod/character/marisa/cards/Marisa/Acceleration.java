package sts.touhouspire.mod.character.marisa.cards.Marisa;

import sts.touhouspire.mod.character.marisa.abstracts.AmplifyCard;
import sts.touhouspire.mod.character.marisa.action.DrawDrawPileAction;
import sts.touhouspire.mod.character.marisa.data.Identifiers;
import sts.touhouspire.mod.character.marisa.patches.AbstractCardEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sts.touhouspire.mod.character.marisa.utils.AmplifyUtils;

public class Acceleration extends AmplifyCard {

	public static final String ID = Identifiers.Cards.ACCELERATION;
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION;
	public static final String IMG_PATH = "marisa/img/cards/GuidingStar.png";
	private static final int COST = 0;
	private static final int DRAW = 2;
	private static final int DRAW_UPG = 1;
	private static final int AMP = 1;
	private static final int AMP_UPG = 1;

	public Acceleration() {
		super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
				AbstractCard.CardType.SKILL,
				AbstractCardEnum.MARISA_COLOR,
				CardRarity.COMMON,
				AbstractCard.CardTarget.SELF);
		this.magicNumber = this.baseMagicNumber = AMP;
		this.block = this.baseBlock = DRAW;
	}

	@Override
	protected void applyPowersToBlock() {}

	public void use(AbstractPlayer player, AbstractMonster monster) {
		for (int i = 0; i < this.block; i++) {
			addToBot(new DrawDrawPileAction());
		}

		if (AmplifyUtils.Amplify(this, AMP)) {
			for (int i = 0; i < this.magicNumber; i++) {
				addToBot(new DrawDrawPileAction());
			}
		}
	}

	public AbstractCard makeCopy() {
		return new Acceleration();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(AMP_UPG);
		}
	}

}
