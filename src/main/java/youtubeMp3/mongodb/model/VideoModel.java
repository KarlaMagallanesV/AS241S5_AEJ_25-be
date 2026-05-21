package youtubeMp3.mongodb.model;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@Document(collection = "Download")
@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoModel {
    @Id
    private String id;
    private String videoId;
    private String link;
    private String title;
    private String thumbnail;
    private int progress;
    private double duration;
    private String status;
    private String msg;
    private boolean deleted = false;
    private LocalDateTime deletedAt;
    private boolean favorite = false;
    private LocalDateTime createdAt = LocalDateTime.now();
}
