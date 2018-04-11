package com.georlegacy.general.suggestionbot;

import com.georlegacy.general.suggestionbot.jda.SuggestionBot;
import com.georlegacy.general.suggestionbot.jda.utils.CanRespond;
import com.georlegacy.general.suggestionbot.jda.utils.DiscordConfig;
import com.georlegacy.general.suggestionbot.jda.utils.EmbedUtil;
import org.bukkit.plugin.java.JavaPlugin;

public class SuggestionBotBukkit extends JavaPlugin {private EmbedUtil embedUtil = new EmbedUtil();
    public EmbedUtil getEmbedUtil() {
        return embedUtil;
    }

    private DiscordConfig discordConfig = new DiscordConfig(this);
    public DiscordConfig getDiscordConfig() {
        return discordConfig;
    }

    private SuggestionBot suggestionBot = new SuggestionBot(this);
    public SuggestionBot getSuggestionBot() {
        return suggestionBot;
    }

    private CanRespond canRespond = new CanRespond(this);
    public CanRespond getCanRespond() {
        return canRespond;
    }

    @Override
    public void onEnable() {
        getDiscordConfig().loadConfig();
        getSuggestionBot().startBot();
    }

    @Override
    public void onDisable() {
        getSuggestionBot().stopBot();
    }

}
