package youtubeMp3.mongodb.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlParserUtil {
    
    private static final Pattern YOUTUBE_PATTERN = Pattern.compile(
        "(?:youtube\\.com/watch\\?v=|youtu\\.be/)([a-zA-Z0-9_-]{11})"
    );
    
    private static final Pattern SPOTIFY_PATTERN = 
    Pattern.compile("spotify\\.com(?:/[a-z]{2,5}(?:-[a-z]{2,4})?)?/track/([a-zA-Z0-9]{22})");
    
    public static String extractYoutubeId(String urlOrId) {
        if (urlOrId == null || urlOrId.trim().isEmpty()) {
            return null;
        }
        
        Matcher matcher = YOUTUBE_PATTERN.matcher(urlOrId);
        if (matcher.find()) {
            return matcher.group(1);
        }
        
        return urlOrId.trim();
    }
    
    public static String extractSpotifyId(String urlOrId) {
        if (urlOrId == null || urlOrId.trim().isEmpty()) {
            return null;
        }
        
        Matcher matcher = SPOTIFY_PATTERN.matcher(urlOrId);
        if (matcher.find()) {
            return matcher.group(1);
        }
        
        return urlOrId.trim();
    }
    
    public static String generateYoutubeThumbnail(String videoId) {
        return "https://img.youtube.com/vi/" + videoId + "/maxresdefault.jpg";
    }
}
