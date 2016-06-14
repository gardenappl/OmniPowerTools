package goldenapple.omnitools.config;

public class RFToolProperties {
    public static RFToolProperties DARK_DRILL_1;
    public static RFToolProperties DARK_DRILL_2;
    public static RFToolProperties DARK_DRILL_3;
    public static RFToolProperties DARK_DRILL_4;
    public static RFToolProperties DARK_CHAINSAW_1;
    public static RFToolProperties DARK_CHAINSAW_2;
    public static RFToolProperties DARK_CHAINSAW_3;
    public static RFToolProperties DARK_CHAINSAW_4;

    public int maxEnergy;
    public int rechargeRate;
    public int energyPerBlock;

    public RFToolProperties(int maxEnergy, int rechargeRate, int energyPerBlock) {
        this.maxEnergy = maxEnergy;
        this.rechargeRate = rechargeRate;
        this.energyPerBlock = energyPerBlock;
    }
}
