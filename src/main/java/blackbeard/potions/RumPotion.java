package blackbeard.potions;

import basemod.abstracts.CustomPotion;

public abstract class RumPotion extends CustomPotion { //TODO: Fix, when Watcher releases
    public RumPotion(String name, String id, PotionRarity rarity, PotionSize size, PotionColor color) {
        super(name, id, rarity, size, color);
    }

    /*private static final String ID = "blackbeard:RumPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
    private static final int POTENCY = 2;

    public RumPotion() {
        super(NAME, ID, PotionRarity.UNCOMMON, PotionSize.H, PotionColor.ELIXIR);
        this.isThrown = false;
    }

    @Override
    public void initializeData() {
        this.potency = this.getPotency();
        this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        Keyword resistanceKeyword = TheBlackbeardMod.keywords.get(TheBlackbeardMod.RESISTANCE_KEYWORD);
        this.tips.add(new PowerTip(TipHelper.capitalize(resistanceKeyword.NAMES[0]), resistanceKeyword.DESCRIPTION));
    }

    @Override
    public void use(AbstractCreature unused) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ResistancePower(AbstractDungeon.player, this.potency), this.potency));
        }
    }

    public AbstractPotion makeCopy() {
        return new RumPotion();
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return POTENCY;
    }*/
}