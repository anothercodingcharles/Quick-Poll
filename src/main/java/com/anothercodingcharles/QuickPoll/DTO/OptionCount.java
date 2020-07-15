package com.anothercodingcharles.QuickPoll.DTO;

public class OptionCount {

    private int count;
    private Long optionId;

    public OptionCount() {

    }

    public OptionCount(int optionCount, Long optionId) {
        this.count = optionCount;
        this.optionId = optionId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    @Override
    public String toString() {
        return "OptionCount{" +
                "optionCount=" + count +
                ", optionId=" + optionId +
                '}';
    }
}
