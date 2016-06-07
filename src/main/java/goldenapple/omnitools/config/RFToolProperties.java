package goldenapple.omnitools.config;

public class RFToolProperties {

    public static RFToolProperties DARK_CHAINSAW_1;

    public int maxEnergy;
    public int rechargeRate;
    public int energyPerBlock;

    public RFToolProperties(int maxEnergy, int rechargeRate, int energyPerBlock) {
        this.maxEnergy = maxEnergy;
        this.rechargeRate = rechargeRate;
        this.energyPerBlock = energyPerBlock;
    }
}
