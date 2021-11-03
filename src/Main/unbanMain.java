package Main;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public class unbanMain extends JavaPlugin implements Listener{

	public void onEnable() {
//		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Unban activated!");
	}


	public void onDisable() {
	}


	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {	
		
		if (!(sender instanceof Player)) {
		    sender.sendMessage(ChatColor.RED + "Only Players can use this command!");
		    return false;
		}
		
		if (args.length == 0) {
			sender.sendMessage(ChatColor.GRAY + "Usage" + ChatColor.DARK_GRAY + ": " + ChatColor.BLUE + "/unban <playername>");
			
			return true;
		}
		
		if (!sender.isOp()) {
			sender.sendMessage(ChatColor.RED + "Only " + ChatColor.BOLD + ChatColor.ITALIC + "OPERATORS " + ChatColor.RESET + ChatColor.RED + "can do this! \n"
					+ "Ask one if you think a player shouldn't be banned.");
			return false;
		}

        OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[0]);
        
        if (target == null) {
            sender.sendMessage("That is not a player.");
        }
        
		if(!target.isBanned()) {
			sender.sendMessage(ChatColor.RED + "This player isn't banned");

			return false;
		}

		Bukkit.getBanList(Type.NAME).pardon(args[0]);
		sender.sendMessage(args[0] + " is now unbanned!");


		return true;
	}
}