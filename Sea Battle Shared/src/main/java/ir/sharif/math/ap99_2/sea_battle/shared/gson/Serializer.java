package ir.sharif.math.ap99_2.sea_battle.shared.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class Serializer<T> implements JsonSerializer<T> {

    @Override
    public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject retValue = new JsonObject();
        String className = src.getClass().getName();
        retValue.addProperty(Constants.CLASSNAME, className);
        JsonElement elem = context.serialize(src);
        retValue.add(Constants.INSTANCE, elem);
        return retValue;
    }
}
