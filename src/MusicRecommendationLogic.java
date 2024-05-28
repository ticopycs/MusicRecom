import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class MusicRecommendationLogic {

    private FIS fis;

    public MusicRecommendationLogic() {
        String fileName = "src/music_recommendation.fcl";
        fis = FIS.load(fileName, true);

        if (fis == null) {
            System.err.println("Error loading FCL file: " + fileName);
            System.exit(1);
        }
    }

    public double getRecommendation(double mood, double time, double genre) {
        fis.setVariable("mood", mood);
        fis.setVariable("time", time);
        fis.setVariable("genre", genre);

        fis.evaluate();

        Variable recommendation = fis.getVariable("recommendation");
        return recommendation.getValue();
    }

    public String getPlaylist(double value) {
        if (value >= 0 && value < 25) {
            return "Playlist 1";
        } else if (value >= 25 && value < 50) {
            return "Playlist 2";
        } else if (value >= 50 && value < 75) {
            return "Playlist 3";
        } else if (value >= 75 && value <= 100) {
            return "Playlist 4";
        } else {
            return "Sin recomendación";
        }
    }
}
