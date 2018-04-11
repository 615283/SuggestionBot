package com.georlegacy.general.suggestionbot.jda.listeners;

import com.georlegacy.general.suggestionbot.SuggestionBotBukkit;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class NewSuggestion extends ListenerAdapter {
    private SuggestionBotBukkit sb;
    public NewSuggestion(SuggestionBotBukkit sb) {
        this.sb = sb;
    }

    @SuppressWarnings("unused")
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getAuthor().isBot()) return;
        Message msg = e.getMessage();
        MessageChannel msgChannel = e.getChannel();
        String content = msg.getContentRaw();

        if (!msgChannel.getId().equals(sb.getDiscordConfig().getSuggestionChannelID())) return;

        if (!content.toLowerCase().startsWith(sb.getDiscordConfig().getSuggestionPrefix().toLowerCase())) {
            msg.delete().queue();
            msgChannel.sendMessage(sb.getEmbedUtil().buildEmbed(new Color(255, 37, 7), "**Error**", "This channel is only for suggestions! Begin your message with `[SUGGESTION]` or it will be removed again.", null, null, null, null, null)).queue((message) -> message.delete().queueAfter(5, TimeUnit.SECONDS) );
            return;
        }

        msg.clearReactions();
        msg.addReaction("\u2705").queue();
        msg.addReaction("\u274c").queue();
        msg.addReaction("\u2754").queue();
    }

}
