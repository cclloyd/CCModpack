package com.cclloyd.ccmodpack.registry;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ForgeRecipe {
	
	ItemStack output;
	Item outputItem;
	int quantity;
	int cookTime;
	String name;
	List<Item> inputs;

	public ForgeRecipe(String name, Item outputItem, int quantity, int cookTime, Item... args) {
		this.name = name;
		this.outputItem = outputItem;
		this.quantity = quantity;
		this.cookTime = cookTime;
		this.inputs = new ArrayList<Item>();
		for (Item arg : args) {
			this.inputs.add(arg);
		}
		if (this.inputs.size() < 4)
			while (this.inputs.size() < 4) {
				this.inputs.add(null);
			}
		
	}
	
	public String name() {
		return name;
	}
	
	public Item output() {
		return outputItem;
	}
	
	public int quantity() {
		return quantity;
	}
	
	public int cookTime() {
		return cookTime;
	}
	
	public Item getInput(int slot) {
		return inputs.get(slot);
	}
	
	public String toString() {
		return this.name + this.quantity + "xRecipe";
	}
	
	public boolean isSameRecipe(ForgeRecipe recipe) {
		if (this.name == recipe.name)
			return true;
		return false;
	}
	
	public int inputCount() {
		if (this.inputs.get(1) == null)
			return 1;
		if (this.inputs.get(2) == null)
			return 2;
		if (this.inputs.get(3) == null)
			return 3;
		return 4;
	}
	
}
