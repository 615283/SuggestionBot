package com.georlegacy.general.suggestionbot.jda;

import com.georlegacy.general.suggestionbot.SuggestionBotBukkit;
import com.georlegacy.general.suggestionbot.jda.listeners.NewResponse;
import com.georlegacy.general.suggestionbot.jda.listeners.NewSuggestion;
import com.georlegacy.general.suggestionbot.jda.listeners.ResponseReaction;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Icon;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class SuggestionBot {
    private SuggestionBotBukkit sb;
    public SuggestionBot(SuggestionBotBukkit sb) {
        this.sb = sb;
    }

    JDA jda;
    public void startBot() {
        try {
            jda = new JDABuilder(AccountType.BOT).setToken(sb.getDiscordConfig().getBotToken()).setGame(Game.listening("suggestions")).buildBlocking();
            jda.getSelfUser().getManager().setName("Suggestions").queue();
            jda.getSelfUser().getManager().setAvatar(Icon.from(sb.getResource("icon.png"))).queue();
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        jda.addEventListener(new NewSuggestion(sb));
        jda.addEventListener(new NewResponse(sb));
        jda.addEventListener(new ResponseReaction(sb));
    }

    public void stopBot() {
        jda.shutdown();
    }

}
