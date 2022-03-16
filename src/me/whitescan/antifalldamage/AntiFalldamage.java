package me.whitescan.antifalldamage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * 
 * @author Whitescan
 *
 */
public class AntiFalldamage extends JavaPlugin implements CommandExecutor, Listener {

	private List<UUID> antifalldamage = new ArrayList<>();

	@Override
	public void onEnable() {
		getCommand("antifalldamage").setExecutor(this);
		Bukkit.getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {

		if (!e.isCancelled()) {

			if (e.getCause() == DamageCause.FALL && antifalldamage.contains(e.getEntity().getUniqueId())) {
				e.setCancelled(true);
			}

		}

	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {

		UUID uuid = e.getPlayer().getUniqueId();

		if (antifalldamage.contains(uuid)) {
			antifalldamage.remove(uuid);
		}

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("antifalldamage.use")) {
			sender.sendMessage("§cSorry, you are not allowed to use this command.");
			return true;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage("§cSorry, this command only works for players!");
			return true;
		}

		Player actor = (Player) sender;

		if (antifalldamage.contains(actor.getUniqueId())) {
			antifalldamage.remove(actor.getUniqueId());
			actor.sendMessage("§eFallprotection toggled: §coff");

		} else {
			antifalldamage.add(actor.getUniqueId());
			actor.sendMessage("§eFallprotection toggled: §aon");
		}

		return true;
	}

}
