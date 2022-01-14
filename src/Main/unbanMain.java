package Main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.StringUtil;


public class unbanMain extends JavaPlugin implements Listener{

	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Unban activated!");
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {	
		if (args.length == 0) {
			sender.sendMessage(ChatColor.GRAY + "Usage" + ChatColor.DARK_GRAY + ": " + ChatColor.BLUE + "/unban <playername>");

			return true;
		}

		if (!sender.isOp()) {
			sender.sendMessage(ChatColor.RED + "I'm sorry, but you do not have permission to perform this command. "
					+ "Please contact the server administrators if you believe that this is a mistake.");
			return false;
		}

		OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[0]);

		if (target == null) {
			sender.sendMessage("That is not a player.");
			return false;
		}

		if(!target.isBanned()) {
			sender.sendMessage(ChatColor.RED + "Nothing changed. The player isn't banned");

			return false;
		}

		Bukkit.getBanList(Type.NAME).pardon(target.getName());
		
		if(sender instanceof Player) {
			sender.sendMessage("Unbanned " + target.getName());
			sendInConsole("[" + sender.getName() + ": Unbanned " + target.getName() + "]");
		} else {
			sendInConsole("Unbanned " + target.getName());
		}
		
		return true;
	}
	
	public void sendInConsole(String text) {
		Bukkit.getConsoleSender().sendMessage(text);
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> arguments = new ArrayList<>();
		if(args.length == 1) {
			if(sender.isOp()) {
				for(OfflinePlayer player : Bukkit.getBannedPlayers())
					arguments.add(player.getName());
			} else {
				arguments.add("");
			}

		} else {
			arguments.add("");
		}
		return StringUtil.copyPartialMatches(args[0], arguments, new ArrayList<>());
	}
}