package stepsword.potionidpacketfixer.mixin;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import stepsword.potionidpacketfixer.interfaces.ClientboundUpdateMobEffectPacketGetter;

@Debug(export=true)
@Mixin(ClientboundUpdateMobEffectPacket.class)
public class MixinClientboundUpdateMobEffectPacket implements ClientboundUpdateMobEffectPacketGetter {
    int intEffectID;

    @Inject(method="<init>(ILnet/minecraft/potion/EffectInstance;)V", at = @At("TAIL"))
    public void constructorTail(int p_i46891_1_, MobEffectInstance p_i46891_2_, CallbackInfo info) {
        intEffectID = MobEffect.getId(p_i46891_2_.getEffect());
    }

    @Inject(method="write(Lnet/minecraft/network/FriendlyByteBuf;)V", at = @At("TAIL"))
    public void writeTail(FriendlyByteBuf buffer, CallbackInfo info) {
        buffer.writeInt(intEffectID);
    }

    @Inject(method="<init>(Lnet/minecraft/network/FriendlyByteBuf;)V", at = @At("TAIL"))
    public void readTail(FriendlyByteBuf buffer, CallbackInfo info) {
        intEffectID = buffer.readInt();
    }

    @OnlyIn(Dist.CLIENT)
    public int getIntEffectId() {
        return this.intEffectID;
    }

}
