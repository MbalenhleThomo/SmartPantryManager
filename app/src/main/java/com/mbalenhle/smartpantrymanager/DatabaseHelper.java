package com.mbalenhle.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "smart_pantry.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) { super(context, DB_NAME, null, DB_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE pantry_items (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, quantity REAL NOT NULL, unit TEXT NOT NULL)");
        db.execSQL("CREATE TABLE recipes (id INTEGER PRIMARY KEY, name TEXT NOT NULL, instructions TEXT NOT NULL)");
        db.execSQL("CREATE TABLE recipe_ingredients (recipe_id INTEGER NOT NULL, ingredient_name TEXT NOT NULL, required_qty REAL NOT NULL)");

        // Seed 15 recipes
        String[][] seeds = {
                {"1", "Tomato Egg Stir-fry", "1. Beat eggs. 2. Fry egg. 3. Add tomato.", "tomato:2,egg:2,oil:1"},
                {"2", "Veggie Omelette", "1. Whisk eggs. 2. Fry onion. 3. Pour eggs.", "egg:3,onion:1,oil:1"},
                {"3", "Garlic Pasta", "1. Boil pasta. 2. Saute garlic. 3. Toss.", "pasta:200,garlic:2,oil:1"},
                {"4", "Onion Soup", "1. Caramelize onions. 2. Add water. 3. Simmer.", "onion:2,water:500,salt:1"},
                {"5", "Scrambled Eggs", "1. Whisk eggs. 2. Cook in oil.", "egg:2,oil:1,salt:1"},
                {"6", "Rice and Beans", "1. Boil rice. 2. Cook beans. 3. Combine.", "rice:200,beans:100,water:400"},
                {"7", "Cheese Toastie", "1. Butter bread. 2. Add cheese. 3. Grill.", "bread:2,cheese:2,butter:1"},
                {"8", "Potato Mash", "1. Boil potatoes. 2. Mash with butter.", "potato:3,butter:1,salt:1"},
                {"9", "Simple Salad", "1. Chop tomato and onion. 2. Toss.", "tomato:1,onion:1,oil:1"},
                {"10", "Chicken Rice", "1. Cook chicken. 2. Boil rice.", "chicken:200,rice:200,water:400"},
                {"11", "Banana Smoothie", "1. Blend banana with milk.", "banana:1,milk:200"},
                {"12", "Bread Omelette", "1. Dip bread in egg. 2. Fry.", "bread:2,egg:1,oil:1"},
                {"13", "Pasta Tomato Sauce", "1. Boil pasta. 2. Cook tomato.", "pasta:200,tomato:2,garlic:1"},
                {"14", "Carrot Stir-fry", "1. Slice carrot. 2. Stir-fry.", "carrot:2,oil:1,salt:1"},
                {"15", "Cheese Omelette", "1. Whisk eggs. 2. Add cheese.", "egg:2,cheese:1,oil:1"}
        };

        for (String[] r : seeds) {
            ContentValues cv = new ContentValues();
            cv.put("id", Integer.parseInt(r[0])); cv.put("name", r[1]); cv.put("instructions", r[2]);
            db.insert("recipes", null, cv);
            for (String ing : r[3].split(",")) {
                String[] parts = ing.split(":");
                ContentValues icv = new ContentValues();
                icv.put("recipe_id", Integer.parseInt(r[0]));
                icv.put("ingredient_name", parts[0]);
                icv.put("required_qty", Double.parseDouble(parts[1]));
                db.insert("recipe_ingredients", null, icv);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS pantry_items"); db.execSQL("DROP TABLE IF EXISTS recipes"); db.execSQL("DROP TABLE IF EXISTS recipe_ingredients");
        onCreate(db);
    }

    public long addPantryItem(String name, double qty, String unit) {
        ContentValues cv = new ContentValues(); cv.put("name", name); cv.put("quantity", qty); cv.put("unit", unit);
        return getWritableDatabase().insert("pantry_items", null, cv);
    }

    public void deletePantryItem(int id) {
        getWritableDatabase().delete("pantry_items", "id=?", new String[]{String.valueOf(id)});
    }

    public void updatePantryItem(int id, String name, double qty, String unit) {
        ContentValues cv = new ContentValues(); cv.put("name", name); cv.put("quantity", qty); cv.put("unit", unit);
        getWritableDatabase().update("pantry_items", cv, "id=?", new String[]{String.valueOf(id)});
    }

    public PantryItem getPantryItemById(int id) {
        Cursor c = getReadableDatabase().query("pantry_items", null, "id=?", new String[]{String.valueOf(id)}, null, null, null);
        if (c.moveToFirst()) { PantryItem p = new PantryItem(c.getInt(0), c.getString(1), c.getDouble(2), c.getString(3)); c.close(); return p; }
        c.close(); return null;
    }

    public List<PantryItem> getAllPantryItems() {
        List<PantryItem> list = new ArrayList<>();
        Cursor c = getReadableDatabase().query("pantry_items", null, null, null, null, null, "name ASC");
        if (c.moveToFirst()) { do { list.add(new PantryItem(c.getInt(0), c.getString(1), c.getDouble(2), c.getString(3))); } while (c.moveToNext()); }
        c.close(); return list;
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> list = new ArrayList<>();
        Cursor c = getReadableDatabase().query("recipes", null, null, null, null, null, "name ASC");
        if (c.moveToFirst()) { do { list.add(new Recipe(c.getInt(0), c.getString(1), c.getString(2))); } while (c.moveToNext()); }
        c.close(); return list;
    }

    public List<RecipeIngredient> getIngredientsForRecipe(int recipeId) {
        List<RecipeIngredient> list = new ArrayList<>();
        Cursor c = getReadableDatabase().query("recipe_ingredients", null, "recipe_id=?", new String[]{String.valueOf(recipeId)}, null, null, null);
        if (c.moveToFirst()) { do { list.add(new RecipeIngredient(c.getString(1), c.getDouble(2))); } while (c.moveToNext()); }
        c.close(); return list;
    }
}