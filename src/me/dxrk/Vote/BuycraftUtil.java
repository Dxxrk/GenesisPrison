package me.dxrk.Vote;

import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import net.buycraft.plugin.client.ApiException;
import net.buycraft.plugin.client.ProductionApiClient;
import net.buycraft.plugin.data.Coupon;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class BuycraftUtil implements Listener, CommandExecutor {
    private static String secret = "7a465d1d42b7fd30862bbf282961c869668c66bc";
    private static ProductionApiClient buycraft = new ProductionApiClient(secret);
    private static Methods m = Methods.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("buymsg")) {
            if(!sender.isOp()) return false;
            if (args.length >= 3) {
                String name = args[0];
                String price = args[1];
                String pkgName = args[2];
                if (pkgName.equals("Genesis")) {
                    pkgName = m.c("&4&lG&c&le&6&ln&e&le&a&ls&b&li&d&ls &f&lRank");
                }
                StringBuilder s = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    if (i >= 3) {
                        s.append(args[i]).append(" ");
                    }
                }

                Bukkit.broadcastMessage(m.c("&9&m-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-"));
                Bukkit.broadcastMessage(m.c("&f&l" + name + " &bHas Purchased &6" + pkgName + " " + s + "&b."));
                Bukkit.broadcastMessage(m.c("&ePrice: &e$" + price));
                Bukkit.broadcastMessage(m.c("&9&m-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-"));

            }
        }
        if (label.equalsIgnoreCase("createcoupon")) {
            if(!sender.isOp()) return false;
            if (args.length == 2) {
                Player p = Bukkit.getPlayer(args[0]);
                double amount = Double.parseDouble(args[1]);
                try {
                    createCoupon(p, amount);
                } catch (IOException | ApiException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        if (label.equalsIgnoreCase("redeem")) {
            Player p = (Player) sender;
            if (!coupons().containsKey(p.getUniqueId().toString().replace("-", ""))) {
                p.sendMessage(m.c("&cYou have nothing to redeem."));
                return false;
            }
            if (p.hasPermission("genesis.redeem")) return false;
            try {
                createCouponbuycraft(p);
                Main.perms.playerAdd(p, "genesis.redeem");
            } catch (IOException | ApiException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    private static List<Integer> categories() {
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


    public void returnPurchases(Player p) {
        String name = p.getName();


    }


    public static Coupon coupon(String code, Coupon.Discount discount, Coupon.Expire expire, Date date) {
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
        for (Coupon c : buycraft.getAllCoupons()) {
            if (randomcode.equals(c.getCode())) {
                randomcode = generateCode();
            }
        }
        if (amount <= 0) {
            p.sendMessage(m.c("&cError: Amount is too low."));
            return;
        }
        Coupon.Discount discount = new Coupon.Discount("value", BigDecimal.valueOf(0.0), BigDecimal.valueOf(amount));
        Coupon.Expire expire = new Coupon.Expire("limit", 1, date);
        Coupon coupon = coupon(randomcode, discount, expire, date);

        buycraft.createCoupon(coupon);

        p.sendMessage(m.c("&bYour coupon code for &a$" + amount + " &b is &6" + randomcode));

    }

    private static HashMap<String, Double> coupons() {
        HashMap<String, Double> coupons = new HashMap<>();
        coupons.put("01c9f21b9a274976b2a2188cd16e8958", 851.0);
        coupons.put("0c8adfa9c066448e912409b98a812894", 22.75);
        coupons.put("180e14a3aaf74ad683f96f7bbfd22da8", 4.00);
        coupons.put("1ad0b0314fa042f0b4872e6d8473aa1d", 5.75);
        coupons.put("1af3317cef9f465f8456c73433037b94", 4.00);
        coupons.put("249c034473b6404b9c2681cf03bbe076", 18.75);
        coupons.put("274813ee5bae446a935f7b587d623f91", 35.44);
        coupons.put("2d60d0648d8944279b392778ecc37c0b", 10.0);
        coupons.put("3ab3033d5fad42768191cb5743a0d451", 29.25);
        coupons.put("3e1030ab25e44739873d5adabc324d84", 78.0);
        coupons.put("478590dfed15485ea3bbb72b3551ea71", 9.52);
        coupons.put("49d279e33105461bb2f92ee1d41ccc36", 117.97);
        coupons.put("4b6a3f3060324ee9883f2df00a6e4128", 10.0);
        coupons.put("4e752e446d924e22a3b7e4777fae8e22", 13.50);
        coupons.put("4f06f93c4aab41689cf74db1c4ef1bff", 9.25);
        coupons.put("59cb9636583e4172b798661d11f90890", 40.0);
        coupons.put("5a89208dd2d941628a82638d780009c8", 7.50);
        coupons.put("5be9757eb8914b409182b90f5f9fb642", 52.36);
        coupons.put("6257bd647c5a4be7b38d32147d624dfa", 22.50);
        coupons.put("6b2b99bb316e4429a493a25eb530b3e6", 15.0);
        coupons.put("7151e2dd0cee4964ac3c0d1ccc5ac1e7", 9.68);
        coupons.put("74fad5f6452a47d5871c38b68324f684", 13.0);
        coupons.put("76906bcbdab8427288fffabfdb61b414", 4.00);
        coupons.put("7e418df678fa4be8b999c38d99db4add", 47.50);
        coupons.put("8352889c8e664562b86fa0e6f3277427", 5.0);
        coupons.put("8bed6e7412cb4c51920ab0ee4c3e6d18", 100.0);
        coupons.put("9495592a6d014671a388ffb3aa9189f3", 4.00);
        coupons.put("9eb5568b968f47fe93903e62b45bd927", 55.25);
        coupons.put("a3d4d99f24724158994a2afc5ce538b1", 60.0);
        coupons.put("a7df727a08a94c86aa0e5ff0883dcd4d", 12.0);
        coupons.put("a9f4eba08c1d4535a0f69ea0f820e073", 21.75);
        coupons.put("aaa3e34cab284a239d319700cb196081", 47.50);
        coupons.put("b1f45c78e4f14fdf99d92a5151537f7b", 4.00);
        coupons.put("c80937f6dd2a4f65abc3a695c3dba88a", 289.08);
        coupons.put("c973f2cc24fc4c4dac792eaaf035eb15", 98.0);
        coupons.put("ca84314cf3e84866a86d861a83ee400b", 155.0);
        coupons.put("cb8d6351d97f472fa2628f3473b6fcc7", 76.0);
        coupons.put("d4191412585b47eea93789b0db10ebc0", 15.0);
        coupons.put("d4e7577743d74fdfbf5bcb182ac3d3b0", 21.25);
        coupons.put("dce072a18cd14c45bf5faaad4c98bd44", 7.50);
        coupons.put("e04268be8db04d84936ae4dae70ad15d", 132.0);
        coupons.put("f0916a631d8148cfb72ff9360731c726", 150.0);
        coupons.put("f273132d7665451f8de878f68814ccac", 170.0);
        coupons.put("fc6ede47eb554bb89afdc562906d421b", 50.0);
        coupons.put("febf70d52d7a466a93d41ebca2043801", 17.5);
        return coupons;
    }

    public static void createCouponbuycraft(Player p) throws IOException, ApiException {
        if (!coupons().containsKey(p.getUniqueId().toString().replace("-", ""))) return;

        double amount = coupons().get(p.getUniqueId().toString().replace("-", ""));

        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        Date date = today.getTime();
        String randomcode = generateCode();
        for (Coupon c : buycraft.getAllCoupons()) {
            if (randomcode.equals(c.getCode())) {
                randomcode = generateCode();
            }
        }
        Coupon.Discount discount = new Coupon.Discount("value", BigDecimal.valueOf(0.0), BigDecimal.valueOf(amount));
        Coupon.Expire expire = new Coupon.Expire("limit", 1, date);
        Coupon coupon = coupon(randomcode, discount, expire, date);

        buycraft.createCoupon(coupon);
        TextComponent msg = new TextComponent(m.c("&bYour coupon code for &a$" + amount + " &b is &6" + randomcode + " (&7Click to Copy)"));
        msg.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, randomcode));
        p.sendMessage(msg);
        p.sendMessage(m.c("&cYou can only use this command once! Do not lose the code!"));


    }


}
