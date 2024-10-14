package al.training.management.service.klas;

import al.training.management.dto.ClassDto;
import al.training.management.model.Klas;
import al.training.management.request.CreateClassRequest;
import al.training.management.request.UpdateClassRequest;

import java.util.List;

public interface IKlasService {
    Klas getClassById(Long classId);

    Klas addClass(CreateClassRequest request);

    Klas updateClass(UpdateClassRequest request, Long classId);

    void deleteClass(Long classId);

    List<Klas> getAllClasses();

    List<Klas> getClassesByBlock(String block);

    List<Klas> getClassBySubject(String subject);

    List<Klas> getClassByBlockAndSubject(String block, String subject);


    ClassDto convertClassToDto(Klas klas);


    List<ClassDto> getConvertedClasses(List<Klas> classes);
}
