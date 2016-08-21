package com.holler.bean;

import com.holler.holler_dao.entity.Faqs;
import com.holler.holler_dao.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

public class FaqsDTO {
    private String questions;
    private String answers;

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public static List<FaqsDTO> getFaqsDTO(List<Faqs> faqsList) {
        List<FaqsDTO> faqsDTOs = new ArrayList<FaqsDTO>();
        for (Faqs faq : CommonUtil.safe(faqsList)) {
            FaqsDTO faqsDTO = new FaqsDTO();
            faqsDTO.setQuestions(faq.getQuestion());
            faqsDTO.setAnswers(faq.getAnswer());
            faqsDTOs.add(faqsDTO);
        }
        return faqsDTOs;
    }

}
