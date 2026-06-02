

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@Document(collection = "SpotifyDownloads")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyDownloadModel {
    @Id
    private String id;
    private String songId;
    private String downloadLink;
    private String title;
    private String artist;
    private String coverImage;
    private String status;
    private boolean deleted = false;
    private LocalDateTime deletedAt;
    private boolean favorite = false;
    private LocalDateTime createdAt = LocalDateTime.now();
}