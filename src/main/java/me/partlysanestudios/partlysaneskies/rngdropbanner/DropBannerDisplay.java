//
// Written by Su386.
// See LICENSE for copyright and license notices.
//

package me.partlysanestudios.partlysaneskies.rngdropbanner;

import java.awt.Color;

import gg.essential.elementa.ElementaVersion;
import gg.essential.elementa.UIComponent;
import gg.essential.elementa.components.UIWrappedText;
import gg.essential.elementa.components.Window;
import gg.essential.elementa.constraints.CenterConstraint;
import gg.essential.elementa.constraints.PixelConstraint;
import gg.essential.universal.UMatrixStack;
import me.partlysanestudios.partlysaneskies.PartlySaneSkies;
import me.partlysanestudios.partlysaneskies.utils.Utils;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DropBannerDisplay extends Gui {
    public static Drop drop;

    public DropBannerDisplay() {
    }

    // Waits to detect the rarte drop
    @SubscribeEvent
    public void onChatMessage(ClientChatReceivedEvent event) {
        String formattedMessage = event.message.getFormattedText();
        if (isRareDrop(formattedMessage) && PartlySaneSkies.config.rareDropBannerSound) {
            PartlySaneSkies.minecraft.thePlayer.playSound("partlysaneskies:rngdropjingle", 100, 1);
        }

        if (isRareDrop(formattedMessage) && PartlySaneSkies.config.rareDropBanner) {
            String unformattedMessage = event.message.getUnformattedText();


            String name = "";
            // Gets the name of teh drop category
            String dropCategory = unformattedMessage.substring(0, unformattedMessage.indexOf("! ") + 1);

            // Gets the colour of the drop category
            Color dropCategoryHex = Utils.colorCodetoColor.get(formattedMessage.substring(2, 4));

            // // Finds the amount of  magic find from the message
            name = formattedMessage.substring(formattedMessage.indexOf("! ") + 2);

            DropBannerDisplay.drop = new Drop(name, dropCategory, 1, PartlySaneSkies.getTime(), dropCategoryHex);
        }
    }

    public static boolean isRareDrop(String formattedMessage) {
        return formattedMessage.startsWith("§r§6§lRARE DROP! ")
        || formattedMessage.startsWith("§r§6§lPET DROP! ");
    }

    float SMALL_TEXT_SCALE = 5f;
    float BIG_TEXT_SCALE = 10f;

    Window window = new Window(ElementaVersion.V2);
    String topString = "empty";
    String dropNameString = "empty";
    String magicFindString = "empty";

    UIComponent topText = new UIWrappedText(dropNameString, true, new Color(0, 0, 0, 0), true)
            .setTextScale(new PixelConstraint(BIG_TEXT_SCALE/1075 * window.getWidth()))
            .setWidth(new PixelConstraint(window.getWidth()))
            .setX(new CenterConstraint())
            .setY(new PixelConstraint(window.getHeight() * .333f))
            .setChildOf(window);

    UIComponent dropNameText = new UIWrappedText(dropNameString, true, new Color(0, 0, 0, 0), true)
            .setTextScale(new PixelConstraint(SMALL_TEXT_SCALE/1075 * window.getWidth()))
            .setWidth(new PixelConstraint(window.getWidth()))
            .setX(new CenterConstraint())
            .setY(new PixelConstraint(topText.getBottom() + window.getHeight() * .11f))
            .setChildOf(window);

    @SubscribeEvent
    public void renderText(RenderGameOverlayEvent.Text event) {

        // Color nameColor = new Color(255, 255, 255);
        Color categoryColor = new Color(255, 255, 255);

        if (DropBannerDisplay.drop == null) {
            dropNameString = "";
            topString = "";
            magicFindString = "";
            categoryColor = new Color(255, 255, 255, 0);
        } 
        else {
            categoryColor = drop.dropCategoryColor;
            dropNameString = "x" + drop.amount + " " + drop.name;
            topString = drop.dropCategory;
            // It should be after a third of the rare drop time, and before 10/12ths 
            if (PartlySaneSkies.getTime() - drop.timeDropped > (1f / 3f * PartlySaneSkies.config.rareDropBannerTime * 1000)
                    && PartlySaneSkies.getTime()
                            - drop.timeDropped < (10f / 12f * PartlySaneSkies.config.rareDropBannerTime * 1000)) {
                if (Math.round((drop.timeDropped - PartlySaneSkies.getTime()) / 1000f * 4) % 2 == 0) {
                    categoryColor = Color.white; 
                } else {
                    categoryColor = drop.dropCategoryColor;
                }
            }

            if (!Utils.onCooldown(drop.timeDropped, (long) (PartlySaneSkies.config.rareDropBannerTime * 1000))){
                drop = null;
            }
        }

        ((UIWrappedText) topText)
                .setText(topString)
                .setTextScale(new PixelConstraint(BIG_TEXT_SCALE/1075 * window.getWidth()))
                .setX(new CenterConstraint())
                .setY(new PixelConstraint(window.getHeight() * .3f))
                .setColor(categoryColor);
        ((UIWrappedText) dropNameText)
                .setText(dropNameString)
                .setTextScale(new PixelConstraint(SMALL_TEXT_SCALE/1075 * window.getWidth()))
                .setX(new CenterConstraint())
                .setY(new PixelConstraint(topText.getBottom() + window.getHeight() * .11f));
        window.draw(new UMatrixStack());
    }
}
