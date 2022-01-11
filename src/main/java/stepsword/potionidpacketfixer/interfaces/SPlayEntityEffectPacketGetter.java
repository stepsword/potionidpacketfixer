package stepsword.potionidpacketfixer.interfaces;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface SPlayEntityEffectPacketGetter {
    @OnlyIn(Dist.CLIENT)
    public int getIntEffectId();
}
