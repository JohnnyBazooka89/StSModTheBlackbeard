package blackbeard.damageinfo;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class CannonballDamageInfo extends DamageInfo {
    public CannonballDamageInfo(AbstractCreature damageSource, int base, DamageType type) {
        super(damageSource, base, type);
    }
}
