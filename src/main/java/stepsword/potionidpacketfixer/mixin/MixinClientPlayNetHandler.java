package stepsword.potionidpacketfixer.mixin;

import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.play.server.SPlayEntityEffectPacket;
import net.minecraft.network.play.server.SRemoveEntityEffectPacket;
import net.minecraft.potion.Effect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import stepsword.potionidpacketfixer.interfaces.SPlayEntityEffectPacketGetter;

@Mixin(ClientPlayNetHandler.class)
public class MixinClientPlayNetHandler {
    @Redirect(
        // the method this function is called in
        method = "handleUpdateMobEffect(Lnet/minecraft/network/play/server/SPlayEntityEffectPacket;)V",
        // target the invocation of System.out.println
        at = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/potion/Effect;byId(I)Lnet/minecraft/potion/Effect;"
        )
    )
    private Effect getEffect(int z, SPlayEntityEffectPacket packet) {
        if (packet instanceof SPlayEntityEffectPacketGetter)
            return Effect.byId(((SPlayEntityEffectPacketGetter) packet).getIntEffectId());
        else
            return Effect.byId(z);
    }

    @Redirect(
        // the method this function is called in
        method = "handleRemoveMobEffect(Lnet/minecraft/network/play/server/SRemoveEntityEffectPacket;)V",
        // target the invocation of System.out.println
        at = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/network/play/server/SRemoveEntityEffectPacket;getEffect()Lnet/minecraft/potion/Effect;"
        )
    )
    private Effect getEffectToRemove(SRemoveEntityEffectPacket packet) {
        if (packet instanceof SPlayEntityEffectPacketGetter)
            return Effect.byId(((SPlayEntityEffectPacketGetter) packet).getIntEffectId());
        else
            return packet.getEffect();
    }

}
