package com.georlegacy.general.suggestionbot.jda.listeners;

import com.georlegacy.general.suggestionbot.SuggestionBotBukkit;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.Color;
import java.util.List;

public class NewResponse extends ListenerAdapter {
    private SuggestionBotBukkit sb;
    public NewResponse(SuggestionBotBukkit sb) {
        this.sb = sb;
    }

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent e) {
        if (!e.getAuthor().isBot())
            e.getChannel().getHistory().retrievePast(2).queue((h) -> onMessagesRetrieved(e, h));
    }

    private void onMessagesRetrieved(PrivateMessageReceivedEvent e, List<Message> messages) {
        User author = e.getAuthor();
        String content = messages.get(1).getEmbeds().get(0).getDescription();
        String[] parts = content.split("SuggestionID: ");
        if (!sb.getCanRespond().canRespond(author, parts[parts.length-1].replaceAll("`", ""))) return;
        if (e.getMessage().getContentRaw().equalsIgnoreCase(sb.getDiscordConfig().getPrefix()+"cancel")) {
            System.out.println("is cancel");
            e.getChannel().sendMessage(sb.getEmbedUtil().buildEmbed(
                    new Color(114, 160, 255),
                    "**Response Result**",
                    "Your response to that suggestion has now been cancelled, any messages sent here will be ignored.",
                    null, null, null,
                    "SuggestionsBot",
                    "https://i.imgur.com/8mLH1hi.png"))
                    .queue();
            sb.getCanRespond().denyRespond(author, parts[parts.length-1].replaceAll("`", ""));
        } else {
            e.getChannel().sendMessage(sb.getEmbedUtil().buildEmbed(
                    new Color(114, 160, 255),
                    "**Response Result**",
                    "Your response has been submitted to the suggestion, any messages now sent to this channel will be ignored.",
                    null, null, null,
                    "SuggestionsBot",
                    "https://i.imgur.com/8mLH1hi.png"))
                    .queue();
            sb.getCanRespond().denyRespond(author, parts[parts.length-1]);
        }
    }
}