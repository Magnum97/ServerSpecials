package me.magnum.specials.util;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import me.magnum.specials.commands.ItemHandler;
import me.magnum.specials.config.Config;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class InventoryBuilder implements InventoryProvider {
	
	
	@Override
	public void init (Player player, InventoryContents contents) {
		ItemBuilder next = new ItemBuilder(Config.getNEXT()).setName("§aNext Page");
		ItemBuilder prev = new ItemBuilder(Config.getPREV()).setName("§cPrevious Page");
		Pagination page = contents.pagination();
		ArrayList <ItemStack> list = new ArrayList <>(Config.specials.values());
		contents.newIterator("one", SlotIterator.Type.HORIZONTAL, 1, 1);
		contents.newIterator("two", SlotIterator.Type.HORIZONTAL, 2, 1);
		contents.newIterator("three", SlotIterator.Type.HORIZONTAL, 3, 1);
		int i = 0;
		// ClickableItem[] stuff = page.getPageItems(); //todo is this needed anymore?
		if (contents.iterator("one").isPresent()) {
			SlotIterator one = contents.iterator("one").get();
			while (one.column() < 8) {
				try {
					if (page.getPageItems()[i]==null)
						break;
					one.set(page.getPageItems()[i]);
					i++;
				}
				catch (IndexOutOfBoundsException ignore) {
				}
			}
		}
		if (contents.iterator("two").isPresent()) {
			SlotIterator two = contents.iterator("two").get();
			while (two.column() < 8) {
				try {
					if (page.getPageItems()[i]==null)
						break;
					
					two.set(page.getPageItems()[i]);
					
					i++;
				}
				catch (IndexOutOfBoundsException ignore) {
				
				}
			}
		}
		if (contents.iterator("three").isPresent()) {
			SlotIterator three = contents.iterator("three").get();
			while (three.column() < 8) {
				try {
					if (page.getPageItems()[i]==null)
						break;
					three.set(page.getPageItems()[i]);
				}
				catch (IndexOutOfBoundsException ignore) {
				}
				i++;
			}
		}
		page.setItems(toClickity(list));
		page.setItemsPerPage(21);
		
		contents.fill(ClickableItem.empty(new ItemBuilder(Material.STAINED_GLASS_PANE).setDyeColor(DyeColor.WHITE).toItemStack()));
		
		page.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 1, 1));
		
		contents.set(5, 3, ClickableItem.of(new ItemStack(prev.toItemStack()),
		                                    e -> ItemHandler.INVENTORY.open(player, page.previous().getPage())));
		contents.set(5, 5, ClickableItem.of(new ItemStack(next.toItemStack()),
		                                    e -> ItemHandler.INVENTORY.open(player, page.next().getPage())));
	}

	
/*
	@Override
	public void init (Player player, InventoryContents contents) {
		contents.newIterator("iterator",SlotIterator.Type.HORIZONTAL  ,0,0);
		
		SlotIterator it = contents.iterator("iterator").get();
		contents.fillBorders(ClickableItem.empty(new ItemBuilder(Material.HAY_BLOCK).toItemStack()));
for (String key : Item.specials.keySet())
	contents.add(ClickableItem.of(Item.specials.get(key), e -> {
		
		if (e.isLeftClick()) {
			player.getInventory().addItem(Item.specials.get(key));
			player.closeInventory();
			player.sendMessage("");
		}
		
		if (e.isRightClick()) {
			player.closeInventory();
			player.sendMessage("Wrong dang clicky click man.");
		}
	}));
	
	}
*/
	
	
	@Override
	public void update (Player player, InventoryContents contents) {
	}
	
	private ClickableItem[] toClickity (ArrayList <ItemStack> arrayList) {
		ClickableItem[] clickArray = new ClickableItem[arrayList.size()];
		for (int i = 0; i < arrayList.size(); i++) {
			clickArray[i] = ClickableItem.of(arrayList.get(i), c -> {
				ItemHandler ih = new ItemHandler();
				ih.give((Player) c.getWhoClicked(), c.getCurrentItem(), c.getCurrentItem().getAmount());
			});
		}
		return clickArray;
	}
	
}
