package bg.web.radoweb.controller;


import bg.web.radoweb.exceptions.UrlIsNotCorrect;
import bg.web.radoweb.exceptions.UsernameAlreadyExistsException;
import bg.web.radoweb.model.Dto.PredictionMatchDto;
import bg.web.radoweb.model.Dto.VideoUrlDto;
import bg.web.radoweb.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> addPrediction(@Valid @RequestBody PredictionMatchDto predictionMatchDto) {
        try {
            adminService.addPredict(predictionMatchDto);
            return ResponseEntity.ok("Predict registered successfully.");
        }catch (UsernameAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

    }
    @GetMapping("/predictions")
    public ResponseEntity<List<PredictionMatchDto>> getAllPredictions() {
        List<PredictionMatchDto> predictions = adminService.allPredict();
        return ResponseEntity.ok(predictions);
    }

    @PostMapping("/url")
    public ResponseEntity<String> addUrl(@RequestBody VideoUrlDto videoUrlDto){
        try {
            adminService.registerVideoUrl(videoUrlDto);
            return ResponseEntity.ok("Url registered successfully.");
        }catch (UrlIsNotCorrect e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    @GetMapping("/adminUrl")
    public ResponseEntity<List<VideoUrlDto>> getAllVideo() {
        List<VideoUrlDto> videoUrlDtos = adminService.allVideoNotConfirm();
        return ResponseEntity.ok(videoUrlDtos);
    }
    @PutMapping("{videoId}/approve")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> approveVideo(@PathVariable Long videoId){
        boolean approveVideo = adminService.approveVideo(videoId);
        if (approveVideo){
            return ResponseEntity.ok("Video is approve");
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Video is not approve");
        }

    }
    @DeleteMapping("/adminUrl{videoId}")
    public ResponseEntity<String> deleteVideo(@PathVariable Long videoId){
        boolean approveVideo = adminService.deleteVideo(videoId);
        if (approveVideo){
            return ResponseEntity.ok("Video is delete");
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Video is not delete");
        }

    }


}
