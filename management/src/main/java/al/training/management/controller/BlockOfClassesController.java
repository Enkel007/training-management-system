package al.training.management.controller;

import al.training.management.exception.AlreadyExistsException;
import al.training.management.exception.ResourceNotFoundException;
import al.training.management.model.BlockOfClasses;
import al.training.management.response.ApiResponse;
import al.training.management.service.block.IBlockOfClassesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/blocks")
public class BlockOfClassesController {
    private final IBlockOfClassesService blockOfClassesService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllBlocks() {
        try {
            List<BlockOfClasses> blocks = blockOfClassesService.getAllBlocks();
            return  ResponseEntity.ok(new ApiResponse("Found!", blocks));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error:", INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/block/{id}/block")
    public ResponseEntity<ApiResponse> getBlockById(@PathVariable Long id){
        try {
            BlockOfClasses theBlock = blockOfClassesService.getBlockById(id);
            return  ResponseEntity.ok(new ApiResponse("Found", theBlock));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{name}/category")
    public ResponseEntity<ApiResponse> getBlockByName(@PathVariable String name){
        try {
            BlockOfClasses theBlock = blockOfClassesService.getBlockByName(name);
            return  ResponseEntity.ok(new ApiResponse("Found", theBlock));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addBlock(@RequestBody BlockOfClasses name) {
        try {
            BlockOfClasses theBlock = blockOfClassesService.addBlockOfClasses(name);
            return  ResponseEntity.ok(new ApiResponse("Success", theBlock));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/block/{id}/delete")
    public ResponseEntity<ApiResponse> deleteBlock(@PathVariable Long id){
        try {
            blockOfClassesService.deleteBlockById(id);
            return  ResponseEntity.ok(new ApiResponse("Found", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/block/{id}/update")
    public ResponseEntity<ApiResponse> updateBlock(@PathVariable Long id, @RequestBody BlockOfClasses block) {
        try {
            BlockOfClasses updatedBlock = blockOfClassesService.updateBlockOfClasses(block, id);
            return ResponseEntity.ok(new ApiResponse("Update successful!", updatedBlock));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

}
