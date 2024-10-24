package com.coqire.bagekslibrary;


import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(BageksLibrary.MOD_ID)
public class BageksLibrary
{
    public static final String MOD_ID = "bagekslibrary";

    private static final Logger LOGGER = LogUtils.getLogger();
    public BageksLibrary()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
    }

}
