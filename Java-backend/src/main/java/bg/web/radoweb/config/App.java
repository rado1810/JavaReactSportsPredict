package bg.web.radoweb.config;

import bg.web.radoweb.model.Comment;
import bg.web.radoweb.model.Dto.CommentDto;

import bg.web.radoweb.model.Dto.PredictionMatchDto;
import bg.web.radoweb.model.PredictMach;
import bg.web.radoweb.model.User;

import bg.web.radoweb.repository.UserRepository;
import bg.web.radoweb.service.Impl.CustomUserDetailsService;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class App {
    private final UserRepository userRepository;

    private final CustomUserDetailsService customUserDetailsService;

    public App(UserRepository userRepository,  CustomUserDetailsService customUserDetailsService) {
        this.userRepository = userRepository;

        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public ModelMapper modelMapper(){


        final ModelMapper modelMapper = new ModelMapper();
        Provider<Comment> CommentDtooCommentProvider = ctx -> {
            User user = userRepository.findByUsername(customUserDetailsService.getCurrentUser().getUsername());
            Comment comment = new Comment();
            comment.setAuthor(user);
            return comment;
        };

        modelMapper.createTypeMap(CommentDto.class, Comment.class)
                .setProvider(CommentDtooCommentProvider);

        Provider<PredictMach> PredictMachDtoPredictMachProvider = ctx ->{
            PredictionMatchDto predictMachDto = (PredictionMatchDto) ctx.getSource();
            User user = userRepository.findByUsername(predictMachDto.getAuthor());

            PredictMach predictMach =new PredictMach();
            predictMach.setPredict(predictMachDto.getPredict());
            predictMach.setBet(predictMachDto.getBet());
            predictMach.setSport(predictMach.getSport());
            predictMach.setPotentialWin(predictMachDto.getPotentialWin());
            predictMach.setAuthor(user);
            return predictMach;
        };
        modelMapper.createTypeMap(PredictionMatchDto.class, PredictMach.class)
                .setProvider(PredictMachDtoPredictMachProvider);


        return  modelMapper;
    }


}
