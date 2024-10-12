package com.delu.ancienttimes.common.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;

import java.util.function.BiFunction;
import java.util.function.Consumer;

public class NbtUtils {

    /**
     * this will  get the value from the nbt and set it to something but only if that value exists to avoid setting values to 0
     * @param nbt the nbt we want it to read from
     * @param name the name of the value
     * @param valueGetter the value getter normally a lambda like <i>CompoundNBT::getInt</i>
     * @param valueSetter this is the setter which will be given the value from the getter and then should set some value
     * @param <T> the type of the value
     */
    public static <T> void setIfExists(CompoundTag nbt, String name, BiFunction<CompoundTag, String, T> valueGetter, Consumer<T> valueSetter){
        if (nbt.contains(name)){
            T value = valueGetter.apply(nbt, name);
            valueSetter.accept(value);
        }
    }


    public static <T> void setIfExists(CompoundTag nbt, String name, BiFunction<CompoundTag, String, T> valueGetter, SynchedEntityData data, EntityDataAccessor<T> setter){
        if (nbt.contains(name)){
            T value = valueGetter.apply(nbt, name);
            data.set(setter, value);
        }
    }


}
