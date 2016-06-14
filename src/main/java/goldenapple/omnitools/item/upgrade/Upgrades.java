package goldenapple.omnitools.item.upgrade;

import java.util.ArrayList;

public class Upgrades {
    public static ArrayList<Upgrade> registry = new ArrayList<>(1);
    public static UpgradeEmpowered empowered = new UpgradeEmpowered();

    static {
        registry.add(empowered);
    }
}
