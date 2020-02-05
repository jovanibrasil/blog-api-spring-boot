package com.blog.mappers;

import com.blog.dtos.FeedbackDTO;
import com.blog.models.Feedback;
import org.mapstruct.Mapper;

@Mapper
public interface FeedbackMapper {

    Feedback feedbackDtoToFeedback(FeedbackDTO feedbackDTO);
    FeedbackDTO feedbackToFeedbackDto(Feedback feedback);

}
