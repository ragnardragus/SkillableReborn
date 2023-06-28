package com.ragnardragus.skillablereborn.common.capabilities.jobs;

import com.ragnardragus.skillablereborn.api.Jobs;
import net.minecraft.nbt.CompoundTag;

public class JobData implements IJobData {

    private String lastMerchantJob = Jobs.NONE.displayName;
    private int currentJobIndex = 0;
    private int[] jobLevels = {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    private int[] jobProgress = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};


    @Override
    public String getLastMerchantJob() {
        return this.lastMerchantJob;
    }

    @Override
    public void setLastMerchantJob(String jobName) {
        this.lastMerchantJob = jobName;
    }

    @Override
    public int getCurrentJobIndex() {
        return this.currentJobIndex;
    }

    @Override
    public void setCurrentJobIndex(int jobIndex) {
        this.currentJobIndex = jobIndex;
    }

    @Override
    public int[] getJobLevels() {
        return jobLevels;
    }

    @Override
    public void setJobLevels(int[] jobLevels) {
        this.jobLevels = jobLevels;
    }

    @Override
    public int getJobLevel(Jobs jobs) {
        return jobLevels[jobs.index];
    }

    @Override
    public void setJobLevel(Jobs jobs, int level) {
        jobLevels[jobs.index] = level;
    }

    @Override
    public void increaseJobLevel(Jobs jobs) {
        jobLevels[jobs.index]++;
    }

    @Override
    public void decreaseJobLevel(Jobs jobs) {
        jobLevels[jobs.index]--;
    }

    @Override
    public int[] getJobProgress() {
        return jobProgress;
    }

    @Override
    public void setJobProgress(int[] jobProgress) {
        this.jobProgress = jobProgress;
    }

    @Override
    public int getJobProgressAmount(Jobs jobs) {
        return jobProgress[jobs.index];
    }

    @Override
    public void setJobProgressAmount(Jobs jobs, int level) {
        jobProgress[jobs.index] = level;
    }

    @Override
    public void increaseJobProgressAmount(Jobs jobs) {
        jobProgress[jobs.index]++;
    }

    @Override
    public void decreaseJobProgressAmount(Jobs jobs) {
        jobProgress[jobs.index]--;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compound = new CompoundTag();

        compound.putString("last_merchant_job", this.getLastMerchantJob());
        compound.putInt("current_job", this.getCurrentJobIndex());

        for(int i = 0; i < Jobs.values().length; i++) {
            compound.putInt(Jobs.values()[i].displayName + "_level", jobLevels[Jobs.values()[i].index]);
            compound.putInt(Jobs.values()[i].displayName + "_progress", jobProgress[Jobs.values()[i].index]);
        }

        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.setLastMerchantJob(nbt.getString("last_merchant_job"));
        this.setCurrentJobIndex(nbt.getInt("current_job"));

        for(int i = 0; i < Jobs.values().length; i++) {
            jobLevels[i] = nbt.getInt(Jobs.values()[i].displayName + "_level");
            jobProgress[i] = nbt.getInt(Jobs.values()[i].displayName + "_progress");
        }
    }
}
