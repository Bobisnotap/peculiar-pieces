package amymialee.peculiarpieces.cca;

import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.util.RedstoneManager;
import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentInitializer;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import org.jetbrains.annotations.NotNull;

public class PeculiarChunkComponents implements ChunkComponentInitializer {
    public static final ComponentKey<RedstoneManager> REDSTONE = ComponentRegistry.getOrCreate(PeculiarPieces.id("redstone"), RedstoneManager.class);

    @Override
    public void registerChunkComponentFactories(@NotNull ChunkComponentFactoryRegistry registry) {
        registry.register(REDSTONE, RedstoneManager::new);
    }
}