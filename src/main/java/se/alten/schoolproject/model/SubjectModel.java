package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Subject;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubjectModel implements Serializable {

    private Long id;
    private String title;

    public SubjectModel toModel(Subject subjectToAdd) {
        SubjectModel subjectModel = new SubjectModel();
        subjectModel.setTitle(subjectToAdd.getTitle());
        return subjectModel;
    }
}
