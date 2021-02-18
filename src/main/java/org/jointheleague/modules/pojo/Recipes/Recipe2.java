
package org.jointheleague.modules.pojo.Recipes;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recipe2 {

    @SerializedName("vegetarian")
    @Expose
    private Boolean vegetarian;
    @SerializedName("vegan")
    @Expose
    private Boolean vegan;
    @SerializedName("glutenFree")
    @Expose
    private Boolean glutenFree;
    @SerializedName("dairyFree")
    @Expose
    private Boolean dairyFree;
    @SerializedName("veryHealthy")
    @Expose
    private Boolean veryHealthy;
    @SerializedName("cheap")
    @Expose
    private Boolean cheap;
    @SerializedName("veryPopular")
    @Expose
    private Boolean veryPopular;
    @SerializedName("sustainable")
    @Expose
    private Boolean sustainable;
    @SerializedName("weightWatcherSmartPoints")
    @Expose
    private Integer weightWatcherSmartPoints;
    @SerializedName("gaps")
    @Expose
    private String gaps;
    @SerializedName("lowFodmap")
    @Expose
    private Boolean lowFodmap;
    @SerializedName("aggregateLikes")
    @Expose
    private Integer aggregateLikes;
    @SerializedName("spoonacularScore")
    @Expose
    private Double spoonacularScore;
    @SerializedName("healthScore")
    @Expose
    private Double healthScore;
    @SerializedName("creditsText")
    @Expose
    private String creditsText;
    @SerializedName("license")
    @Expose
    private String license;
    @SerializedName("sourceName")
    @Expose
    private String sourceName;
    @SerializedName("pricePerServing")
    @Expose
    private Double pricePerServing;
    @SerializedName("extendedIngredients")
    @Expose
    private List<ExtendedIngredient> extendedIngredients = null;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("readyInMinutes")
    @Expose
    private Integer readyInMinutes;
    @SerializedName("servings")
    @Expose
    private Integer servings;
    @SerializedName("sourceUrl")
    @Expose
    private String sourceUrl;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("imageType")
    @Expose
    private String imageType;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("cuisines")
    @Expose
    private List<String> cuisines = null;
    @SerializedName("dishTypes")
    @Expose
    private List<String> dishTypes = null;
    @SerializedName("diets")
    @Expose
    private List<String> diets = null;
    @SerializedName("occasions")
    @Expose
    private List<Object> occasions = null;
    @SerializedName("winePairing")
    @Expose
    private WinePairing winePairing;
    @SerializedName("instructions")
    @Expose
    private String instructions;
    @SerializedName("analyzedInstructions")
    @Expose
    private List<AnalyzedInstruction> analyzedInstructions = null;
    @SerializedName("originalId")
    @Expose
    private Object originalId;
    @SerializedName("spoonacularSourceUrl")
    @Expose
    private String spoonacularSourceUrl;

    public Boolean getVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(Boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public Boolean getVegan() {
        return vegan;
    }

    public void setVegan(Boolean vegan) {
        this.vegan = vegan;
    }

    public Boolean getGlutenFree() {
        return glutenFree;
    }

    public void setGlutenFree(Boolean glutenFree) {
        this.glutenFree = glutenFree;
    }

    public Boolean getDairyFree() {
        return dairyFree;
    }

    public void setDairyFree(Boolean dairyFree) {
        this.dairyFree = dairyFree;
    }

    public Boolean getVeryHealthy() {
        return veryHealthy;
    }

    public void setVeryHealthy(Boolean veryHealthy) {
        this.veryHealthy = veryHealthy;
    }

    public Boolean getCheap() {
        return cheap;
    }

    public void setCheap(Boolean cheap) {
        this.cheap = cheap;
    }

    public Boolean getVeryPopular() {
        return veryPopular;
    }

    public void setVeryPopular(Boolean veryPopular) {
        this.veryPopular = veryPopular;
    }

    public Boolean getSustainable() {
        return sustainable;
    }

    public void setSustainable(Boolean sustainable) {
        this.sustainable = sustainable;
    }

    public Integer getWeightWatcherSmartPoints() {
        return weightWatcherSmartPoints;
    }

    public void setWeightWatcherSmartPoints(Integer weightWatcherSmartPoints) {
        this.weightWatcherSmartPoints = weightWatcherSmartPoints;
    }

    public String getGaps() {
        return gaps;
    }

    public void setGaps(String gaps) {
        this.gaps = gaps;
    }

    public Boolean getLowFodmap() {
        return lowFodmap;
    }

    public void setLowFodmap(Boolean lowFodmap) {
        this.lowFodmap = lowFodmap;
    }

    public Integer getAggregateLikes() {
        return aggregateLikes;
    }

    public void setAggregateLikes(Integer aggregateLikes) {
        this.aggregateLikes = aggregateLikes;
    }

    public Double getSpoonacularScore() {
        return spoonacularScore;
    }

    public void setSpoonacularScore(Double spoonacularScore) {
        this.spoonacularScore = spoonacularScore;
    }

    public Double getHealthScore() {
        return healthScore;
    }

    public void setHealthScore(Double healthScore) {
        this.healthScore = healthScore;
    }

    public String getCreditsText() {
        return creditsText;
    }

    public void setCreditsText(String creditsText) {
        this.creditsText = creditsText;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Double getPricePerServing() {
        return pricePerServing;
    }

    public void setPricePerServing(Double pricePerServing) {
        this.pricePerServing = pricePerServing;
    }

    public List<ExtendedIngredient> getExtendedIngredients() {
        return extendedIngredients;
    }

    public void setExtendedIngredients(List<ExtendedIngredient> extendedIngredients) {
        this.extendedIngredients = extendedIngredients;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getReadyInMinutes() {
        return readyInMinutes;
    }

    public void setReadyInMinutes(Integer readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<String> getCuisines() {
        return cuisines;
    }

    public void setCuisines(List<String> cuisines) {
        this.cuisines = cuisines;
    }

    public List<String> getDishTypes() {
        return dishTypes;
    }

    public void setDishTypes(List<String> dishTypes) {
        this.dishTypes = dishTypes;
    }

    public List<String> getDiets() {
        return diets;
    }

    public void setDiets(List<String> diets) {
        this.diets = diets;
    }

    public List<Object> getOccasions() {
        return occasions;
    }

    public void setOccasions(List<Object> occasions) {
        this.occasions = occasions;
    }

    public WinePairing getWinePairing() {
        return winePairing;
    }

    public void setWinePairing(WinePairing winePairing) {
        this.winePairing = winePairing;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public List<AnalyzedInstruction> getAnalyzedInstructions() {
        return analyzedInstructions;
    }

    public void setAnalyzedInstructions(List<AnalyzedInstruction> analyzedInstructions) {
        this.analyzedInstructions = analyzedInstructions;
    }

    public Object getOriginalId() {
        return originalId;
    }

    public void setOriginalId(Object originalId) {
        this.originalId = originalId;
    }

    public String getSpoonacularSourceUrl() {
        return spoonacularSourceUrl;
    }

    public void setSpoonacularSourceUrl(String spoonacularSourceUrl) {
        this.spoonacularSourceUrl = spoonacularSourceUrl;
    }

}
