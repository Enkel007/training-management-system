package al.training.management.service.block;

import al.training.management.exception.AlreadyExistsException;
import al.training.management.exception.ResourceNotFoundException;
import al.training.management.model.BlockOfClasses;
import al.training.management.repository.BlockOfClassesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BlockOfClassesService implements IBlockOfClassesService{
    private final BlockOfClassesRepository blockOfClassesRepository;

    @Override
    public BlockOfClasses getBlockById(Long id) {
        return blockOfClassesRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Block of Classes not found!"));
    }

    @Override
    public BlockOfClasses getBlockByName(String name) {
        return blockOfClassesRepository.findByName(name);
    }

    @Override
    public List<BlockOfClasses> getAllBlocks() {
        return blockOfClassesRepository.findAll();
    }

    @Override
    public BlockOfClasses addBlockOfClasses(BlockOfClasses block) {
        return  Optional.of(block).filter(b -> !blockOfClassesRepository.existsByName(b.getName()))
                .map(blockOfClassesRepository :: save)
                .orElseThrow(() -> new AlreadyExistsException(block.getName()+" already exists"));
    }

    @Override
    public BlockOfClasses updateBlockOfClasses(BlockOfClasses block, Long id) {
        return Optional.ofNullable(getBlockById(id)).map(oldBlock -> {
            oldBlock.setName(block.getName());
            return blockOfClassesRepository.save(oldBlock);
        }) .orElseThrow(()-> new ResourceNotFoundException("Bock of classes not found!"));
    }

    @Override
    public void deleteBlockById(Long id) {
        blockOfClassesRepository.findById(id)
                .ifPresentOrElse(blockOfClassesRepository::delete, () -> {
                    throw new ResourceNotFoundException("Block of classes not found!");
                });
    }
}
