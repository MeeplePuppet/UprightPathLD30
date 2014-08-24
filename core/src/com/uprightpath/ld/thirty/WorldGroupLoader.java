package com.uprightpath.ld.thirty;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.uprightpath.ld.thirty.logic.WorldGroup;

/**
 * Created by Geo on 8/24/2014.
 */
public class WorldGroupLoader extends AsynchronousAssetLoader<WorldGroup, WorldGroupLoader.WorldGrouptLoaderParameters> {
    private static Kryo kryo = new Kryo();
    private WorldGroup worldGroup;

    public WorldGroupLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, WorldGrouptLoaderParameters parameter) {
        load(file);
    }

    @Override
    public WorldGroup loadSync(AssetManager manager, String fileName, FileHandle file, WorldGrouptLoaderParameters parameter) {
        return worldGroup;
    }

    private void load(FileHandle file) {
        Input input = new Input(file.read());
        worldGroup = kryo.readObject(input, WorldGroup.class);
        input.close();
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, WorldGrouptLoaderParameters parameter) {
        return new Array<AssetDescriptor>();
    }

    public class WorldGrouptLoaderParameters extends AssetLoaderParameters<WorldGroup> {
    }
}
