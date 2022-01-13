package stepsword.potionidpacketfixer.mixin;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.world.effect.MobEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import stepsword.potionidpacketfixer.interfaces.ClientboundUpdateMobEffectPacketGetter;

@Mixin(ClientPacketListener.class)
public class MixinClientPacketListener {
    @Redirect(
        // the method this function is called in
        method = "handleUpdateMobEffect(Lnet/minecraft/network/protocol/game/ClientboundUpdateMobEffectPacket;)V",
        // target the invocation of System.out.println
        at = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/world/effect/MobEffect;byId(I)Lnet/minecraft/world/effect/MobEffect;"
        )
    )
    private MobEffect getEffect(int z, ClientboundUpdateMobEffectPacket packet) {
        if (packet instanceof ClientboundUpdateMobEffectPacketGetter)
            return MobEffect.byId(((ClientboundUpdateMobEffectPacketGetter) packet).getIntEffectId());
        else
            return MobEffect.byId(z);
    }
    @Redirect(
            // the method this function is called in
            method = "handleRemoveMobEffect(Lnet/minecraft/network/protocol/game/ClientboundRemoveMobEffectPacket;)V",
            // target the invocation of System.out.println
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/protocol/game/ClientboundRemoveMobEffectPacket;getEffect()Lnet/minecraft/world/effect/MobEffect;"
            )
    )
    private MobEffect getEffectToRemove(ClientboundRemoveMobEffectPacket packet) {
        if (packet instanceof ClientboundUpdateMobEffectPacketGetter)
            return MobEffect.byId(((ClientboundUpdateMobEffectPacketGetter) packet).getIntEffectId());
        else
            return packet.getEffect();
    }

}
