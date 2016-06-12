package goldenapple.omnitools.item.upgrade;

import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AnvilUpgradeHandler {
    @SubscribeEvent
    public void onAnvilRepair(AnvilUpdateEvent event){
        for(Upgrade upgrade : Upgrades.registry){
            int currentLevel = UpgradeHelper.getUpgradeLevel(event.getLeft(), upgrade);

            if(upgrade.canApply(event.getLeft(), currentLevel + 1) && upgrade.isRecipeValid(event.getRight(), currentLevel + 1)){
                event.setOutput(UpgradeHelper.applyUpgrade(event.getLeft(), upgrade, currentLevel + 1));
                event.setMaterialCost(upgrade.getItemCost(event.getLeft(), currentLevel + 1));
                event.setCost(upgrade.getLevelCost(event.getLeft(), currentLevel + 1));
            }
        }
    }
}
