package com.example.midtermproject;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
@Dao
public interface ProductDao {
    @Query("SELECT * FROM products")
    List<Product> getAllProducts();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProduct(Product product);
    @Update
    void updateProduct(Product product);
    @Delete
    void deleteProduct(Product product);
    @Query("DELETE FROM products")
    void deleteAll();
}
