package basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.FontHelper;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(
        clz=AbstractCard.class,
        method="initializeDescription"
)
public class FixDescriptionWidthCustomDynamicVariable
{
    @SpireInsertPatch(
            locator=Locator.class,
            localvars={"gl", "word"}
    )
    public static void Insert(AbstractCard __instance, @ByRef GlyphLayout[] gl, @ByRef String[] word)
    {
        if (BaseMod.keywordIsUnique(word[0].toLowerCase())) {
            String prefix = BaseMod.getKeywordPrefix(word[0].toLowerCase());
            word[0] = word[0].replaceFirst(prefix, "");
            gl[0].width -= (new GlyphLayout(FontHelper.cardDescFont_N, prefix)).width;
        }
        else if (word[0].startsWith("!")) {
            gl[0] = new GlyphLayout(FontHelper.cardDescFont_N, "!D");
        }
    }

    private static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "DESC_BOX_WIDTH");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
