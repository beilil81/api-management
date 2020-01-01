package com.humanup.matrix.ui.apimanagement.vo;

import java.util.Date;

public class InterviewVO {

    private String interviewTitle;
    private String interviewDescription;
    private Date interviewDate;

    public InterviewVO() {
    }

    public InterviewVO(String interviewTitle, String interviewDescription, Date interviewDate) {
        this.interviewTitle = interviewTitle;
        this.interviewDescription = interviewDescription;
        this.interviewDate = interviewDate;
    }

    public String getInterviewTitle() {
        return interviewTitle;
    }
    public String getInterviewDescription() {
        return interviewDescription;
    }
    public Date getInterviewDate() {
        return interviewDate;
    }


    public static class Builder {
        private String interviewTitle;
        private String interviewDescription;
        private Date interviewDate;

        public Builder() {
        }

        public Builder setInterviewTitle(String interviewTitle) {
            this.interviewTitle = interviewTitle;
            return this;
        }
        public Builder setInterviewDescription(String interviewDescription) {
            this.interviewDescription = interviewDescription;
            return this;
        }
        public Builder setInterviewDate(Date interviewDate) {
            this.interviewDate = interviewDate;
            return this;
        }


        public InterviewVO build() {
            return new InterviewVO(interviewTitle,interviewDescription,interviewDate);
        }

    }

}
