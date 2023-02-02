package flyingfoodplugin.flyingfood;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Flyingfood extends JavaPlugin implements Listener {
        @Override
        public void onEnable() {
            Bukkit.getServer().getPluginManager().registerEvents(this, this);
            createFlyCookieRecipe();
        }

        @EventHandler
        public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
            Player player = event.getPlayer();
            ItemStack item = event.getItem();

            if (item.getType() == Material.COOKIE) {
                List<String> lore = item.getItemMeta().getLore();
                if (lore != null && lore.contains("Flying Cookie")) {
                    player.setAllowFlight(true);
                    player.setFlying(true);
                    player.sendMessage("You can now fly!");

                    Bukkit.getScheduler().runTaskLater(this, () -> {
                        player.setAllowFlight(false);
                        player.setFlying(false);
                        player.sendMessage("Your fly time has ended.");
                    }, 20 * 30); // 30 seconds in ticks
                }
            }
        }

        public void createFlyCookieRecipe() {
            ItemStack cookie = new ItemStack(Material.COOKIE);
            ItemMeta meta = cookie.getItemMeta();
            meta.setDisplayName("Flying Cookie");
            List<String> lore = new ArrayList<>();
            lore.add("Flying Cookie");
            meta.setLore(lore);
            cookie.setItemMeta(meta);

            ShapedRecipe recipe = new ShapedRecipe(cookie);
            recipe.shape("GGG", "GCG", "GGG");
            recipe.setIngredient('G', Material.GOLD_INGOT);
            recipe.setIngredient('C', Material.COOKIE);
            Bukkit.getServer().addRecipe(recipe);
        }
    }