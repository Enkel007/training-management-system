package al.training.management.service.block;

import al.training.management.model.BlockOfClasses;

import java.util.List;

public interface IBlockOfClassesService {
    BlockOfClasses getBlockById(Long id);

    BlockOfClasses getBlockByName(String name);

    List<BlockOfClasses> getAllBlocks();

    BlockOfClasses addBlockOfClasses(BlockOfClasses block);

    BlockOfClasses updateBlockOfClasses(BlockOfClasses block, Long id);

    void deleteBlockById(Long id);
}
