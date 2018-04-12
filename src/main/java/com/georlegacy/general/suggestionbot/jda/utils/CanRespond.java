package com.georlegacy.general.suggestionbot.jda.utils;

import com.georlegacy.general.suggestionbot.SuggestionBotBukkit;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CanRespond {
    private SuggestionBotBukkit sb;
    public CanRespond(SuggestionBotBukkit sb) {
        this.sb = sb;
    }

    public boolean canRespond(User u, String id) {
        YamlConfiguration responseRT = YamlConfiguration.loadConfiguration(new File(sb.getDataFolder()+File.separator+"responseRT.yml"));
        if (responseRT.contains(u.getId()+"."+id)) {
            if (responseRT.getBoolean(u.getId()+"."+id)) {
                return true;
            } else {
                return false;
            }
        } else return false;
    }

    public void allowRespond(User u, String id) {
        System.out.println("saving respond permission");
        YamlConfiguration responseRT = YamlConfiguration.loadConfiguration(new File(sb.getDataFolder()+File.separator+"responseRT.yml"));
        responseRT.set(u.getId()+"."+id, true);
        try {
            responseRT.save(new File(sb.getDataFolder()+File.separator+"responseRT.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void denyRespond(User u, String id) {
        YamlConfiguration responseRT = YamlConfiguration.loadConfiguration(new File(sb.getDataFolder()+File.separator+"responseRT.yml"));
        responseRT.set(u.getId()+"."+id, false);
        try {
            responseRT.save(new File(sb.getDataFolder()+File.separator+"responseRT.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
