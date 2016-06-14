package goldenapple.omnitools.item.upgrade;

import java.util.ArrayList;

public class Upgrades {
    public static ArrayList<Upgrade> registry = new ArrayList<>(2);
    public static UpgradeEmpowered empowered = new UpgradeEmpowered();
    public static UpgradeBeastMode beastMode = new UpgradeBeastMode();

    static {
        registry.add(empowered);
        registry.add(beastMode);
    }
}
