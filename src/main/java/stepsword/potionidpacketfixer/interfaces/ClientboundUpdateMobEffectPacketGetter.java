package stepsword.potionidpacketfixer.interfaces;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ClientboundUpdateMobEffectPacketGetter {
    @OnlyIn(Dist.CLIENT)
    public int getIntEffectId();
}
