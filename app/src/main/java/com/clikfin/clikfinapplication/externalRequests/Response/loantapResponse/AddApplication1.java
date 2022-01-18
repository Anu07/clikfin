
package com.clikfin.clikfinapplication.externalRequests.Response.loantapResponse;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AddApplication1 {

    @SerializedName("answer")
    private Answer mAnswer;
    @SerializedName("question")
    private Question mQuestion;

    public Answer getAnswer() {
        return mAnswer;
    }

    public void setAnswer(Answer answer) {
        mAnswer = answer;
    }

    public Question getQuestion() {
        return mQuestion;
    }

    public void setQuestion(Question question) {
        mQuestion = question;
    }

}
