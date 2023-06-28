package com.ragnardragus.skillablereborn.common.capabilities.jobs;

import com.ragnardragus.skillablereborn.api.Jobs;
import net.minecraft.nbt.CompoundTag;

public interface IJobData {

    String getLastMerchantJob();
    void setLastMerchantJob(String jobName);

    int getCurrentJobIndex();
    void setCurrentJobIndex(int jobIndex);

    int[] getJobLevels();

    void setJobLevels(int[] jobLevels);

    int getJobLevel(Jobs jobs);

    void setJobLevel(Jobs jobs, int level);

    void increaseJobLevel(Jobs jobs);

    void decreaseJobLevel(Jobs jobs);

    int[] getJobProgress();

    void setJobProgress(int[] jobProgress);

    int getJobProgressAmount(Jobs jobs);

    void setJobProgressAmount(Jobs jobs, int level);

    void increaseJobProgressAmount(Jobs jobs);

    void decreaseJobProgressAmount(Jobs jobs);

    CompoundTag serializeNBT();

    void deserializeNBT(CompoundTag nbt);
}
