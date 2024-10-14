package al.training.management.service.klas;

import al.training.management.dto.ClassDto;
import al.training.management.exception.AlreadyExistsException;
import al.training.management.exception.ResourceNotFoundException;
import al.training.management.model.BlockOfClasses;
import al.training.management.model.Klas;
import al.training.management.repository.BlockOfClassesRepository;
import al.training.management.repository.KlasRepository;
import al.training.management.request.CreateClassRequest;
import al.training.management.request.UpdateClassRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class KlasService implements IKlasService{
    private final KlasRepository klasRepository;
    private final BlockOfClassesRepository blockRepository;

    private final ModelMapper modelMapper;

    @Override
    public Klas getClassById(Long classId) {
        return klasRepository.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));
    }

    @Override
    public Klas addClass(CreateClassRequest request) {
        if (classExists(request.getSubject(), request.getDate())){
            throw new AlreadyExistsException("Class with subject" + request.getSubject()
                    + " already exists, you may update this class instead!");
        }
        BlockOfClasses block = Optional.ofNullable(blockRepository.findByName(request.getBlockOfClasses().getName()))
                .orElseGet(() -> {
                    BlockOfClasses newBlock = new BlockOfClasses(request.getBlockOfClasses().getName());
                    return blockRepository.save(newBlock);
                });
        request.setBlockOfClasses(block);
        return klasRepository.save(createClass(request, block));
    }

    private Klas createClass(CreateClassRequest request, BlockOfClasses block) {
        return new Klas(
                request.getSubject(),
                request.getDate(),
                block
        );
    }

    @Override
    public void deleteClass(Long classId) {
        klasRepository.findById(classId)
                .ifPresentOrElse(klasRepository::delete,
                        () -> {throw new ResourceNotFoundException("Class not found!");});
    }

    private boolean classExists(String subject , Date date) {
        return klasRepository.existsBySubjectandDate(subject, date);
    }

    @Override
    public Klas updateClass(UpdateClassRequest request, Long classId) {
        return klasRepository.findById(classId)
                .map(existingClass -> updateExistingClass(existingClass,request))
                .map(klasRepository :: save)
                .orElseThrow(()-> new ResourceNotFoundException("Class not found!"));
    }

    private Klas updateExistingClass(Klas existingClass, UpdateClassRequest request) {
        existingClass.setSubject(request.getSubject());
        existingClass.setDate(String.valueOf(request.getDate()));

        BlockOfClasses block = blockRepository.findByName(request.getBlockOfClasses().getName());
        existingClass.setBlockOfClasses(block);
        return existingClass;

    }

    @Override
    public List<Klas> getAllClasses() {
        return klasRepository.findAll();
    }

    @Override
    public List<Klas> getClassesByBlock(String block) {
        return klasRepository.findByBlock(block);
    }

    @Override
    public List<Klas> getClassBySubject(String subject) {
        return klasRepository.findBySubject(subject);
    }

    @Override
    public List<Klas> getClassByBlockAndSubject(String block, String subject) {
        return klasRepository.findByBlockAndSubject(block, subject);
    }

    @Override
    public ClassDto convertClassToDto(Klas klas) {
        return modelMapper.map(klas, ClassDto.class);
    }

    @Override
    public List<ClassDto> getConvertedClasses(List<Klas> classes) {
        return classes.stream().map(this::convertClassToDto).toList();
    }
}
