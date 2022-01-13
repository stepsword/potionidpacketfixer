package stepsword.potionidpacketfixer.mixin;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SPlayEntityEffectPacket;
import net.minecraft.network.play.server.SRemoveEntityEffectPacket;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import stepsword.potionidpacketfixer.interfaces.SPlayEntityEffectPacketGetter;

@Debug(export=true)
@Mixin(SRemoveEntityEffectPacket.class)
public class MixinSRemoveEntityEffectPacket implements SPlayEntityEffectPacketGetter {

    int intEffectID;

    @Inject(method="<init>(ILnet/minecraft/potion/Effect;)V", at = @At("TAIL"))
    public void constructorTail(int p_i46891_1_, Effect p_i46891_2_, CallbackInfo info) {
        intEffectID = Effect.getId(p_i46891_2_);
    }

    @Inject(method="write(Lnet/minecraft/network/PacketBuffer;)V", at = @At("TAIL"))
    public void writeTail(PacketBuffer buffer, CallbackInfo info) {
        buffer.writeInt(intEffectID);
    }
    @Inject(method="read(Lnet/minecraft/network/PacketBuffer;)V", at = @At("TAIL"))
    public void readTail(PacketBuffer buffer, CallbackInfo info) {
        intEffectID = buffer.readInt();
    }

    @OnlyIn(Dist.CLIENT)
    public int getIntEffectId() {
        return this.intEffectID;
    }

}
