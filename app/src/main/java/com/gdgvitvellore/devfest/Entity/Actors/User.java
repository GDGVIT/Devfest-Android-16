package com.gdgvitvellore.devfest.Entity.Actors;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Prince Bansal Local on 10/8/2016.
 */

/**
 * @see <a href="https://github.com/GDGVIT/devfest-portal/wiki">API Reference</a>
 */

public class User {

    @SerializedName("auth_token")
    private String authToken;
    private String name;
    private String email;
    @SerializedName("reg_no")
    private String registrationNumber;
    private String phone;
    @SerializedName("block_room")
    private String blockAndRoom;
    private String gender;
    @SerializedName("linkedin_url")
    private String linkedInUrl;
    @SerializedName("github_url")
    private String githubUrl;
    @SerializedName("behance_url")
    private String behanceUrl;
    private boolean isAdmin;
    private List<String> skills;
    @SerializedName("slot_last_used")
    private long slotLastTime;
    @SerializedName("slot_tries")
    private int slotTries;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBlockAndRoom() {
        return blockAndRoom;
    }

    public void setBlockAndRoom(String blockAndRoom) {
        this.blockAndRoom = blockAndRoom;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLinkedInUrl() {
        return linkedInUrl;
    }

    public void setLinkedInUrl(String linkedInUrl) {
        this.linkedInUrl = linkedInUrl;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public String getBehanceUrl() {
        return behanceUrl;
    }

    public void setBehanceUrl(String behanceUrl) {
        this.behanceUrl = behanceUrl;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public long getSlotLastTime() {
        return slotLastTime;
    }

    public void setSlotLastTime(long slotLastTime) {
        this.slotLastTime = slotLastTime;
    }

    public int getSlotTries() {
        return slotTries;
    }

    public void setSlotTries(int slotTries) {
        this.slotTries = slotTries;
    }
}
