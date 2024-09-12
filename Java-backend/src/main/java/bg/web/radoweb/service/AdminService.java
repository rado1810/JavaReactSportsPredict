package bg.web.radoweb.service;

import bg.web.radoweb.model.Dto.PredictionMatchDto;
import bg.web.radoweb.model.Dto.VideoUrlDto;

import java.util.List;

public interface AdminService {
    void addPredict(PredictionMatchDto predictionMatchDto);
    List<PredictionMatchDto> allPredict();
    void registerVideoUrl(VideoUrlDto videoUrlDto);
    List<VideoUrlDto> allVideoNotConfirm();

    boolean approveVideo(Long videoId);
    boolean deleteVideo(Long videoId);
}
