package com.kiwifisher.mobstacker.loot.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kiwifisher.mobstacker.loot.api.ICondition;
import com.kiwifisher.mobstacker.loot.api.LootData;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

/**
 * IFunction implementation for smelting an item.
 * 
 * @author Jikoo
 */
public class FunctionFurnaceSmelt extends Function {

    public FunctionFurnaceSmelt() {}

    @Override
    public void modify(LootData lootData, Entity entity, int looting) {
        if (lootData == null) {
            return;
        }

        Iterator<Recipe> recipes = Bukkit.recipeIterator();
        while (recipes.hasNext()) {
            Recipe next = recipes.next();
            if (!(next instanceof FurnaceRecipe)) {
                continue;
            }
            FurnaceRecipe furnaceRecipe = (FurnaceRecipe) next;
            ItemStack input = furnaceRecipe.getInput();
            if (input.getType() != lootData.getMaterial()) {
                continue;
            }
            if (input.getDurability() == -1 || input.getDurability() == Short.MAX_VALUE) {
                // Inexact match, search for exact match.
                lootData.setMaterial(furnaceRecipe.getResult().getType());
                lootData.setData(furnaceRecipe.getResult().getDurability());
                continue;
            }
            if (input.getDurability() == lootData.getData()) {
                // Exact match, we're done.
                lootData.setMaterial(furnaceRecipe.getResult().getType());
                lootData.setData(furnaceRecipe.getResult().getDurability());
                break;
            }
        }
    }

    @Override
    public boolean isVariable() {
        return false;
    }

    public static FunctionFurnaceSmelt deserialize(Map<String, Object> serialization) {
        FunctionFurnaceSmelt function = new FunctionFurnaceSmelt();

        if (serialization.containsKey("conditions")) {
            Object conditions = serialization.get("conditions");
            if (conditions instanceof List) {
                List<ICondition> newConditions = new ArrayList<>();
                List<?> conditionList = (List<?>) conditions;
                for (Object condition : conditionList) {
                    if (condition instanceof ICondition) {
                        newConditions.add((ICondition) condition);
                    }
                }
                if (!newConditions.isEmpty()) {
                    function.setConditions(newConditions);
                }
            }
        }

        return function;
    }

}
