package bg.web.radoweb.service.Impl;

import bg.web.radoweb.exceptions.UrlIsNotCorrect;
import bg.web.radoweb.model.Dto.PredictionMatchDto;
import bg.web.radoweb.model.Dto.VideoUrlDto;
import bg.web.radoweb.model.PredictMach;
import bg.web.radoweb.model.VideoUrl;
import bg.web.radoweb.repository.PredictMachRepository;
import bg.web.radoweb.repository.VideoUrlRepository;
import bg.web.radoweb.service.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service

public class AdminServiceImpl implements AdminService {
    private final PredictMachRepository predictMachRepository;
    private final ModelMapper modelMapper;
    private final VideoUrlRepository videoUrlRepository;



    public AdminServiceImpl(PredictMachRepository predictMachRepository, ModelMapper modelMapper, VideoUrlRepository videoUrlRepository) {
        this.predictMachRepository = predictMachRepository;
        this.modelMapper = modelMapper;
        this.videoUrlRepository = videoUrlRepository;
    }


    @Override
    public void addPredict(PredictionMatchDto predictionMatchDto) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean roleAdmin = authorities.stream().anyMatch(e -> e.getAuthority().equals("ROLE_ADMIN"));
        String author = predictionMatchDto.getAuthor();
        if (roleAdmin){
            PredictMach predictMach=modelMapper.map(predictionMatchDto,PredictMach.class);
            predictMachRepository.save(predictMach);
        }else {
          throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access denied");
        }


    }

    @Override
    public List<PredictionMatchDto> allPredict() {
        List<PredictMach> predictions = predictMachRepository.findAll();
        return predictions.stream()
                .map(prediction -> mapToDto(prediction))
                .collect(Collectors.toList());
    }
    private PredictionMatchDto mapToDto(PredictMach prediction) {
        PredictionMatchDto dto = new PredictionMatchDto();
        dto.setPredict(prediction.getPredict());
        dto.setSport(prediction.getSport());
        dto.setBet(prediction.getBet());
        dto.setAuthor(prediction.getAuthor().getUsername());
        dto.setPotentialWin(prediction.getPotentialWin());
        return dto;
    }
    @Override
    public void registerVideoUrl(VideoUrlDto videoUrlDto) {

        String url = videoUrlDto.getUrl();
        if (validateYoutubeUrl(url)){
            String pattern="v=([a-zA-Z0-9_-]{11})";
            Pattern compiledPattern =Pattern.compile(pattern);
            Matcher matcher=compiledPattern.matcher(url);
            if (matcher.find()) {
                String videoUrlCorrect = matcher.group(1);

                VideoUrl videoUrl = new VideoUrl();
                videoUrl.setConfirmedByAdmin(false);
                videoUrl.setUrl(videoUrlCorrect);

                videoUrlRepository.save(videoUrl);
            }

        }else {
            throw new UrlIsNotCorrect("Url is not correct!");
        }
    }
    private boolean validateYoutubeUrl(String url) {
        return url.matches("https://www\\.youtube\\.com/watch\\?v=[a-zA-Z0-9_-]{11}.*");
    }

    @Override
    public List<VideoUrlDto> allVideoNotConfirm() {
        List<VideoUrl> allVideoNotConfirmByAdmin= videoUrlRepository
                .findAll()
                .stream()
                .filter(videoUrl -> !videoUrl.isConfirmedByAdmin()).toList();

        return allVideoNotConfirmByAdmin.stream().map(this::mapToVideoDto).collect(Collectors.toList());
    }
    private VideoUrlDto mapToVideoDto(VideoUrl videoUrl){
        VideoUrlDto videoUrlDto =new VideoUrlDto();
        videoUrlDto.setId(videoUrl.getId());
        videoUrlDto.setUrl("https://www.youtube.com/watch?v="+videoUrl.getUrl());
        videoUrlDto.setConfirmedByAdmin(videoUrl.isConfirmedByAdmin());
        return videoUrlDto;
    }

    @Override
    public boolean approveVideo(Long videoId) {
        VideoUrl videoUrl = videoUrlRepository.findById(videoId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        videoUrl.setConfirmedByAdmin(true);
        videoUrlRepository.save(videoUrl);
        return true;


    }

    @Override
    public boolean deleteVideo(Long videoId) {
        if (videoUrlRepository.existsById(videoId)) {
            videoUrlRepository.deleteById(videoId);
            return true;
        }
        return false;
    }

}
