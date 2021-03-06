package com.kiwifisher.mobstacker;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import org.junit.Test;

/**
 * Tests ensuring that the more complex yaml files included can be loaded properly.
 * 
 * @author Jikoo
 */
public class ConfigLoadTest {

    /*
     * Currently nonfunctional - default loot.yml requires loading a PotionMeta. Due to Bukkit's
     * implementation, this cannot be tested without a running server instance or OBC access.
     */
//    @Test
//    public void testLoot() {
//        loadYaml("src/main/resources/loot.yml");
//    }

    @Test
    public void testExperience() {
        loadYaml("src/main/resources/experience.yml");
    }

    public void loadYaml(String fileName) {
        MobStacker.registerSerializableClasses();
        File file = new File(fileName);
        if (!file.exists()) {
            fail("File not found: " + fileName);
        }

        try {
            new YamlConfiguration().load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            fail("Could not load file: " + fileName);
        }
    }

}
