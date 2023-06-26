package com.ragnardragus.skillablereborn.serialization;

import com.google.gson.*;
import com.ragnardragus.skillablereborn.api.Stats;
import com.ragnardragus.skillablereborn.api.Requirement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;

public class RequirementsJsonListener extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private Map<ResourceLocation, Requirement[]> skillsDataMap = new HashMap<>();
    private Map<ResourceLocation, String> stageDataMap = new HashMap<>();

    private Map<ResourceLocation, Boolean> isArrowDataMap = new HashMap<>();

    private static String SKILL = "attribute";
    private static String LEVEL = "level";

    private static String REQUIREMENTS = "requirements";
    private static String STAGE = "stage";

    private static String ARROW = "is_bow_projectile";

    public RequirementsJsonListener() {
        super(GSON, "skills");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> data, ResourceManager resourceManager, ProfilerFiller profiler) {
        data.entrySet().forEach(e -> {
            ResourceLocation key = e.getKey();
            JsonElement value = e.getValue();
            JsonObject jsonObject = value.getAsJsonObject();

            if(jsonObject.has(STAGE)) {
                String stage = jsonObject.get(STAGE).getAsString();

                if(!stage.isEmpty())
                    stageDataMap.put(new ResourceLocation(key.getPath().replace("/", ":")), stage);
            }

            if(jsonObject.has(ARROW)) {
                Boolean isArrow = jsonObject.get(ARROW).getAsBoolean();
                isArrowDataMap.put(new ResourceLocation(key.getPath().replace("/", ":")), isArrow != null ? isArrow : false);
            } else {
                isArrowDataMap.put(new ResourceLocation(key.getPath().replace("/", ":")), false);
            }

            if(jsonObject.has(REQUIREMENTS)) {
                JsonArray requirementsJson = jsonObject.getAsJsonArray(REQUIREMENTS);
                Requirement[] requirements = tryParse(requirementsJson);
                if(requirements.length != 0)
                    skillsDataMap.put(new ResourceLocation(key.getPath().replace("/", ":")), requirements);
            }
        });
    }

    private Requirement[] tryParse(JsonArray requirementsJson) {
        Requirement[] requirements = new Requirement[requirementsJson.size()];

        for(int i = 0; i < requirementsJson.size(); i++) {
            JsonObject jsonObject = requirementsJson.get(i).getAsJsonObject();

            if(jsonObject.has(SKILL) && jsonObject.has(LEVEL)) {

                Stats attributes = Stats.valueOf(jsonObject.get(SKILL).getAsString().toUpperCase());
                int level = jsonObject.get(LEVEL).getAsInt();

                Requirement requirement = new Requirement(attributes, level);
                requirements[i] = requirement;
            }
        }

        return requirements;
    }

    public Requirement[] getRequirements(ResourceLocation resourceLocation) {
        return skillsDataMap.get(resourceLocation);
    }

    public String getRequirementStageName(ResourceLocation resourceLocation) {
        return stageDataMap.get(resourceLocation);
    }

    public boolean isBowProjectile(ResourceLocation resourceLocation) {
        return isArrowDataMap.get(resourceLocation);
    }
}
