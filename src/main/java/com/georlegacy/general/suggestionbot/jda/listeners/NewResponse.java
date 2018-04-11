package com.georlegacy.general.suggestionbot.jda.listeners;

import com.georlegacy.general.suggestionbot.SuggestionBotBukkit;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

public class NewResponse extends ListenerAdapter {
    private SuggestionBotBukkit sb;
    public NewResponse(SuggestionBotBukkit sb) {
        this.sb = sb;
    }

    @SuppressWarnings("unused")
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        System.out.println("in event");
        if (!e.getChannel().getType().equals(ChannelType.PRIVATE)) return;
        System.out.println("in private");
        if (e.getAuthor().isBot()) return;
        System.out.println("not bot");
        StringBuilder b = new StringBuilder();
        e.getChannel().getHistoryBefore(e.getMessageId(), 1).queue((messageHistory) -> b.append(messageHistory.getRetrievedHistory().get(0).getEmbeds().get(0).getDescription()));
        if (!sb.getCanRespond().canRespond(e.getAuthor(), b.toString().split("SuggestionID: ")[b.toString().split("SuggestionID: ").length-1])) return;
        System.out.println(b.toString());
        if (e.getMessage().getContentRaw().equalsIgnoreCase(sb.getDiscordConfig().getPrefix()+"cancel")) {
            System.out.println("is cancel");
            e.getChannel().sendMessage(sb.getEmbedUtil().buildEmbed(
                    new Color(114, 160, 255),
                    "**Response Result**",
                    "Your response to that suggestion has now been cancelled, any messages sent here will be ignored.",
                    null,
                    null,
                    null,
                    "SuggestionsBot",
                    "https://i.imgur.com/8mLH1hi.png"))
                    .queue();
            sb.getCanRespond().denyRespond(e.getAuthor(), b.toString().split("SuggestionID: ")[b.toString().split("SuggestionID: ").length-1]);
        } else {
            e.getChannel().sendMessage(sb.getEmbedUtil().buildEmbed(
                    new Color(114, 160, 255),
                    "**Response Result**",
                    "Your response has been submitted to the suggestion, any messages now sent to this channel will be ignored.",
                    null,
                    null,
                    null,
                    "SuggestionsBot",
                    "https://i.imgur.com/8mLH1hi.png"))
                    .queue();
            sb.getCanRespond().denyRespond(e.getAuthor(), b.toString().split("SuggestionID: ")[b.toString().split("SuggestionID: ").length-1]);
        }
    }
}
