package blackbeard.potions;

import basemod.abstracts.CustomPotion;

public abstract class OrangeJuicePotion extends CustomPotion { //TODO: Fix, when Watcher releases
    public OrangeJuicePotion(String name, String id, PotionRarity rarity, PotionSize size, PotionColor color) {
        super(name, id, rarity, size, color);
    }

    /*private static final String ID = "blackbeard:OrangeJuicePotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
    private static final int POTENCY = 8;

    public OrangeJuicePotion() {
        super(NAME, ID, PotionRarity.COMMON, PotionSize.BOTTLE, PotionColor.ELIXIR);
        this.isThrown = false;
    }

    @Override
    public void initializeData() {
        this.potency = this.getPotency();
        this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public void use(AbstractCreature unused) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, this.potency));
            AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(AbstractDungeon.player));
        }
    }

    public AbstractPotion makeCopy() {
        return new OrangeJuicePotion();
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return POTENCY;
    }*/
}