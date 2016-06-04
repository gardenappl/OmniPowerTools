package goldenapple.omnitools.config;

public class MiningMode {
    public final int x;
    public final int y;
    public final int z;

    public MiningMode(int xRadius, int yRadius, int zRadius){
        x = xRadius;
        y = yRadius;
        z = zRadius;
    }

    @Override
    public String toString() {
        return String.format("%sx%sx%s", x * 2 - 1, y * 2 - 1, z * 2 - 1);
    }
}
