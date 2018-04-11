package com.georlegacy.general.suggestionbot.jda.utils;

import com.georlegacy.general.suggestionbot.SuggestionBotBukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class DiscordConfig {
    private SuggestionBotBukkit sb;
    public DiscordConfig(SuggestionBotBukkit sb) {
        this.sb = sb;
    }

    private YamlConfiguration discordConfig() {
        File f = new File(sb.getDataFolder()+File.separator+"discord.yml");
        return YamlConfiguration.loadConfiguration(f);
    }

    public void loadConfig() {
        if (!new File(sb.getDataFolder()+File.separator+"discord.yml").exists()) {
            sb.saveResource("discord.yml", true);
        }
        if (!new File(sb.getDataFolder()+File.separator+"runtime").exists()) {
            new File(sb.getDataFolder()+File.separator+"runtime").mkdirs();
        }
        if (!new File(sb.getDataFolder()+File.separator+"runtime"+File.separator+"responseRT").exists()) {
            sb.saveResource("responseRT.yml", true);
        }
    }

    public String getPrefix() {
        return discordConfig().getString("CommandPrefix");
    }

    public String getBotToken() {
        return discordConfig().getString("BotToken");
    }

    public String getSuggestionChannelID() {
        return discordConfig().getString("SuggestionChannelID");
    }

    public String getSuggestionPrefix() {
        return "[" + discordConfig().getString("SuggestionPrefix") + "]";
    }

    public boolean getDenyOtherReactions() {
        return discordConfig().getBoolean("DenyOtherReactions");
    }

}
