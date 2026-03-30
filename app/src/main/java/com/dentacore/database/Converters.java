package com.dentacore.database;
import androidx.room.TypeConverter;
import com.dentacore.models.Medication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
public class Converters {
    private static final Gson gson=new Gson();
    @TypeConverter public static String fromIntList(List<Integer> l){return l==null?null:gson.toJson(l);}
    @TypeConverter public static List<Integer> toIntList(String v){if(v==null)return new ArrayList<>();Type t=new TypeToken<List<Integer>>(){}.getType();return gson.fromJson(v,t);}
    @TypeConverter public static String fromMedList(List<Medication> l){return l==null?null:gson.toJson(l);}
    @TypeConverter public static List<Medication> toMedList(String v){if(v==null)return new ArrayList<>();Type t=new TypeToken<List<Medication>>(){}.getType();return gson.fromJson(v,t);}
}
