package com.example.easyfind.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.example.easyfind.database.CoordinateConverter;
import com.example.easyfind.database.CategoryListConverter;
import com.example.easyfind.database.LocationConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@Entity(tableName = "business_table")
public class Business {

    @PrimaryKey @NonNull
    @SerializedName("id")
    @Expose private String id;

    @ColumnInfo(name = "isFav")
    private boolean isFav;

    @ColumnInfo(name = "alias")
    @SerializedName("alias")
    @Expose private String alias;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose private String name;

    @ColumnInfo(name = "image_url")
    @SerializedName("image_url")
    @Expose private String imageUrl;

    @ColumnInfo(name = "is_closed")
    @SerializedName("is_closed")
    @Expose private Boolean isClosed;

    @ColumnInfo(name = "url")
    @SerializedName("url")
    @Expose private String url;

    @ColumnInfo(name = "review_count")
    @SerializedName("review_count")
    @Expose private Integer reviewCount;

    @TypeConverters(CategoryListConverter.class)
    @ColumnInfo(name = "categories")
    @SerializedName("categories")
    @Expose private List<Category> categories = null;

    @ColumnInfo(name = "rating")
    @SerializedName("rating")
    @Expose private Double rating;

    @TypeConverters(CoordinateConverter.class)
    @ColumnInfo(name = "coordinates")
    @SerializedName("coordinates")
    @Expose private Coordinates coordinates;

    @ColumnInfo(name = "price")
    @SerializedName("price")
    @Expose private String price;

    @TypeConverters(LocationConverter.class)
    @ColumnInfo(name = "location")
    @SerializedName("location")
    @Expose private BusinessLocation location;

    @ColumnInfo(name = "phone")
    @SerializedName("phone")
    @Expose private String phone;

    @ColumnInfo(name = "display_phone")
    @SerializedName("display_phone")
    @Expose private String displayPhone;

    @ColumnInfo(name = "distance")
    @SerializedName("distance")
    @Expose private Double distance;

    @Ignore
    @SerializedName("photos")
    @Expose private List<String> photos = null;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public BusinessLocation getLocation() {
        return location;
    }

    public void setLocation(BusinessLocation location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDisplayPhone() {
        return displayPhone;
    }

    public void setDisplayPhone(String displayPhone) {
        this.displayPhone = displayPhone;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }
}
