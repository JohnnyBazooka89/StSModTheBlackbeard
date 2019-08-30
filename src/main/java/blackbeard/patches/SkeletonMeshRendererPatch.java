package blackbeard.patches;

import blackbeard.spineUtils.RegionAttachmentRenderedWithNoPremultipliedAlpha;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonMeshRenderer;
import com.esotericsoftware.spine.attachments.Attachment;
import com.esotericsoftware.spine.attachments.RegionAttachment;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import javassist.CannotCompileException;
import javassist.CtBehavior;

@SpirePatch(clz = SkeletonMeshRenderer.class, method = "draw", paramtypez = {PolygonSpriteBatch.class, Skeleton.class})
public class SkeletonMeshRendererPatch {

    @SpireInsertPatch(locator = Locator.class, localvars = {"premultipliedAlpha", "attachment"})
    public static void Insert(SkeletonMeshRenderer skeletonMeshRenderer, PolygonSpriteBatch batch, Skeleton skeleton, @ByRef boolean[] premultipliedAlpha, Attachment attachment) {
        if (attachment instanceof RegionAttachmentRenderedWithNoPremultipliedAlpha) {
            premultipliedAlpha[0] = false;
        }
    }

    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher matcher = new Matcher.InstanceOfMatcher(RegionAttachment.class);
            return LineFinder.findInOrder(ctMethodToPatch, matcher);
        }
    }

}
