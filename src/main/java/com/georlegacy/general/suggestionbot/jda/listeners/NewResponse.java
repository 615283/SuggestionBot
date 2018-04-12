package com.georlegacy.general.suggestionbot.jda.listeners;

import com.georlegacy.general.suggestionbot.SuggestionBotBukkit;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

public class NewResponse extends ListenerAdapter {
    private SuggestionBotBukkit sb;
    public NewResponse(SuggestionBotBukkit sb) {
        this.sb = sb;
    }

    @SuppressWarnings("unused")
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (!e.getChannel().getType().equals(ChannelType.PRIVATE)) return;
        if (e.getAuthor().isBot()) return;
        AtomicReference<String> content = new AtomicReference<String>();
        e.getChannel().getHistoryBefore(e.getMessageId(), 1).queue((messageHistory) -> content.set(messageHistory.getRetrievedHistory().get(0).getEmbeds().get(0).getDescription()));
        System.out.println(content.get());
        if (!sb.getCanRespond().canRespond(e.getAuthor(), content.get().split("SuggestionID: ")[content.get().split("SuggestionID: ").length-1].replaceAll("`", ""))) return;
        System.out.println(content.get());
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
            sb.getCanRespond().denyRespond(e.getAuthor(), content.get().split("SuggestionID: ")[content.get().split("SuggestionID: ").length-1].replaceAll("`", ""));
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
            sb.getCanRespond().denyRespond(e.getAuthor(), content.get().split("SuggestionID: ")[content.get().split("SuggestionID: ").length-1]);
        }
    }
}
