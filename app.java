import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class app {
    private static final String IMAGE_URL = "https://assets.science.nasa.gov/dynamicimage/assets/science/missions/webb/outreach/migrated/2025/STScI-01K5F9XJYK3GQM9MX99XCXMMVV.jpg?w=1024&h=576&fit=crop&crop=faces%2Cfocalpoint";

    public static void main(String[] args) {
        System.out.println("Downloading NASA Image...");
        try {
            URL url = new URL(IMAGE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
                     FileOutputStream fileOutputStream = new FileOutputStream("nasa_image.jpg")) {

                    byte[] dataBuffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                        fileOutputStream.write(dataBuffer, 0, bytesRead);
                    }
                }
                System.out.println("Image downloaded successfully as nasa_image.jpg");

                // Open the image
                File imageFile = new File("nasa_image.jpg");
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(imageFile);
                    System.out.println("Image opened successfully.");
                } else {
                    System.out.println("Desktop not supported, cannot open image.");
                }
            } else {
                System.out.println("Error: HTTP " + responseCode);
            }
        } catch (IOException e) {
            System.out.println("Error fetching or opening data: " + e.getMessage());
        }
    }
}
