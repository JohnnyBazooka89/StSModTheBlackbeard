package blackbeard.interfaces;


import com.megacrit.cardcrawl.core.Settings;

import java.util.List;

public interface ILongTitle {

    /* This interface is used in LongTitlesFixPatch and LongTitlesSingleCardViewFixPatch to use smaller font for card's title. */

    List<Settings.GameLanguage> getLanguagesForFixingLongTitle();

}
