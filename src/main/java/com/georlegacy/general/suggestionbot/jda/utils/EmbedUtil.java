package com.georlegacy.general.suggestionbot.jda.utils;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;

public class EmbedUtil {

    public MessageEmbed buildEmbed(@Nonnull Color color, @Nonnull String title, @Nonnull String description, @Nullable String iconurl, @Nullable String thumbnailUrl, @Nullable MessageEmbed.Field field, @Nullable String footertext, @Nullable String footerIconUrl) {
        EmbedBuilder builder = new EmbedBuilder();

        builder
                .setTitle(title)
                .setColor(color)
                .setDescription(description);

        if (!(iconurl==null)) {
            builder.setAuthor(null, iconurl);
        }

        if (!(thumbnailUrl==null)) {
            builder.setThumbnail(thumbnailUrl);
        }

        if (!(field==null)) {
            builder.addField(field);
        }

        if (!(footertext==null)&&!(footerIconUrl==null)) {
            builder.setFooter(footertext, footerIconUrl);
        }
        return builder.build();
    }

}
