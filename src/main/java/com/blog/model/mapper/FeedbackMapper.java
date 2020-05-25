package com.blog.model.mapper;

import org.mapstruct.Mapper;

import com.blog.model.Feedback;
import com.blog.model.dto.FeedbackDTO;
import com.blog.model.form.FeedbackForm;

@Mapper
public interface FeedbackMapper {

    Feedback feedbackDtoToFeedback(FeedbackDTO feedbackDTO);
    FeedbackDTO feedbackToFeedbackDto(Feedback feedback);
	Feedback feedbackFormToFeedback(FeedbackForm feedbackForm);

}
