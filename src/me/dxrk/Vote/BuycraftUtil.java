package me.dxrk.Vote;

import me.dxrk.Main.Methods;
import net.buycraft.plugin.client.ApiException;
import net.buycraft.plugin.client.ProductionApiClient;
import net.buycraft.plugin.data.Coupon;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class BuycraftUtil implements Listener, CommandExecutor{
	private static String secret = "7a465d1d42b7fd30862bbf282961c869668c66bc";
	private static ProductionApiClient buycraft = new ProductionApiClient(secret);
	private static Methods m = Methods.getInstance();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(label.equalsIgnoreCase("buymsg")) {
			if(args.length == 3) {
				String name = args[0];
				String price = args[1];
				String pkgName = args[2];
				if(pkgName.equals("Zeus")) {
					pkgName = m.c("&e&l⚡&f&lZeus&e&l⚡");
				}
				
				Bukkit.broadcastMessage(m.c("&9&m-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-"));
				Bukkit.broadcastMessage(m.c("&f&l"+name+" &bHas Purchased &6"+pkgName+"&b."));
				Bukkit.broadcastMessage(m.c("&ePrice: &e$"+price));
				Bukkit.broadcastMessage(m.c("&9&m-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-"));
				
			}
		}
		if(label.equalsIgnoreCase("createcoupon")){
			if(args.length == 2){
				Player p = Bukkit.getPlayer(args[0]);
				double amount = Double.parseDouble(args[1]);
				try {
					createCoupon(p, amount);
				} catch (IOException | ApiException e) {
					throw new RuntimeException(e);
				}

			}
		}
		return false;
	}

	private static List<Integer> categories(){
		List<Integer> cat = new ArrayList<>();
		cat.add(0);
		cat.add(1);
		cat.add(2);
		cat.add(3);
		cat.add(4);
		cat.add(5);
		cat.add(6);
		return cat;
	}
	private static List<Integer> packages() {
		List<Integer> cat = new ArrayList<>();

		return cat;
	}

	private static Random random = new Random();

	public static String generateCode() {
		char[] cs = new char[9];
		for (int i = 0; i < cs.length; i++)
			cs[i] = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".charAt(random.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".length()));
		return new String(cs);
	}

	public static Coupon coupon(String code, Coupon.Discount discount, Coupon.Expire expire, Date date){
		Coupon coupon = Coupon.builder()
				.code(code)
				.discount(discount)
				.userLimit(1)
				.basketType("Everything")
				.effective(new Coupon.Effective("cart", packages(), categories()))
				.redeemUnlimited(0)
				.discountMethod(2)
				.basketType("both")
				.expireNever(1)
				.expire(expire)
				.minimum(BigDecimal.valueOf(0.01))
				.startDate(date)
				.build();

		return coupon;
	}

		public static void createCoupon(Player p, double amount) throws IOException, ApiException {
			Calendar today = Calendar.getInstance();
			today.set(Calendar.HOUR_OF_DAY, 0);
			Date date = today.getTime();
			String randomcode = generateCode();
			for(Coupon c : buycraft.getAllCoupons()) {
				if (randomcode.equals(c.getCode())) {
					randomcode = generateCode();
				}
			}
			if(amount <=0){
				p.sendMessage(m.c("&cError: Amount is too low."));
				return;
			}
			Coupon.Discount discount = new Coupon.Discount("value", BigDecimal.valueOf(0.0), BigDecimal.valueOf(amount));
			Coupon.Expire expire = new Coupon.Expire("limit", 1, date);
			Coupon coupon = coupon(randomcode, discount, expire, date);

			buycraft.createCoupon(coupon);

			p.sendMessage(m.c("&bYour coupon code for &a$"+amount+" &b is &6"+randomcode));

		}






}
