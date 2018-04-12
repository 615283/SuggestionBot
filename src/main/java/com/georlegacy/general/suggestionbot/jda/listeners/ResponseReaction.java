package com.georlegacy.general.suggestionbot.jda.listeners;

import com.georlegacy.general.suggestionbot.SuggestionBotBukkit;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

public class ResponseReaction extends ListenerAdapter {
    private SuggestionBotBukkit sb;
    public ResponseReaction(SuggestionBotBukkit sb) {
        this.sb = sb;
    }

    @SuppressWarnings("unused")
    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent e) {
        if (!e.getChannel().getId().equals(sb.getDiscordConfig().getSuggestionChannelID())) return;
        if (e.getUser().isBot()) return;
        if (!e.getReactionEmote().getName().equals("❔")&&!e.getReactionEmote().getName().equals("✅")&&!e.getReactionEmote().getName().equals("❌")) {
            if (sb.getDiscordConfig().getDenyOtherReactions()) e.getReaction().removeReaction(e.getUser()).queue();
            return;
        }
        if (e.getReactionEmote().getName().equals("❔")) {
            AtomicReference<String> content = new AtomicReference<String>();
            e.getChannel().getMessageById(e.getMessageId()).queue(message -> content.set(message.getContentRaw()));
            e.getUser().openPrivateChannel().queue(
                    (channel) -> channel.sendMessage(sb.getEmbedUtil().buildEmbed(
                            new Color(114, 160, 255),
                            "**Respond to suggestion**",
                            "To submit a response to the suggestion you reacted to, simply type your response below or type `"+sb.getDiscordConfig().getPrefix()+"cancel` and anything sent here will not be taken as a repsonse.\n\n The current suggestion that you are responding to is as follows: \n`"+content.get()+"`\nSuggestionID: `"+e.getMessageId()+"`",
                            null,
                            "https://i.imgur.com/ffccqpL.png",
                            null,
                            "SuggestionBot",
                            "https://i.imgur.com/8mLH1hi.png"))
                            .queue());
            sb.getCanRespond().allowRespond(e.getUser(), e.getMessageId());
        }

    }

}
