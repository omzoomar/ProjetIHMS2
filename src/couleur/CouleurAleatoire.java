package couleur;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tooltip;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import modele.GenerateurDeCouleurs;
import java.util.ArrayList;
import java.util.List;

public class CouleurAleatoire {
	/**
	 * Méthode retournant toutes les palettes de couleurs
	 */
	public VBox palettesDeCouleursAleatoires(GenerateurDeCouleurs generateur) {
		/*
		  Création du contenu
		 */
	    VBox vbox = new VBox();
	    vbox.setPadding(new Insets(10, 800, 20, 10));
	    vbox.setSpacing(8);
        vbox.setAlignment(Pos.CENTER);

		/*
		  Création d'une ArrayList contenant les palettes de couleurs
		 */

        generateur.genererEchantillon(10);

		List<HBox> box = new ArrayList<>();
	    for(Color c: generateur.getCouleurs())
	       box.add(addCouleur(c));

		/*
		  Ajout de chaque palette au contenu
		 */
	    for(HBox b: box)
	        vbox.getChildren().add(b);

	    return vbox;
	}

	/**
	 * Affichage d'une palette, de la couleur, du niveau de gris et du RGB correspondant
	 */
	private HBox addCouleur(Color color) {
		Rectangle recColor;
		Rectangle recGray;
		Text titleRGB = new Text();
		Text titleHSB = new Text();
		Text titleRGBHexa = new Text();
		Text titleHSBHexa = new Text();
		Clipboard clipboard = Clipboard.getSystemClipboard();
		ClipboardContent content = new ClipboardContent();
		Tooltip tooltip = new Tooltip();

		/*
		  Création d'une barre horizontale
		 */
	    HBox hbox = new HBox();
	    hbox.setPadding(new Insets(5));
	    hbox.setSpacing(8);
	    hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setStyle("-fx-border-style: none none solid none; -fx-border-width: 1; -fx-border-color: #cfc6ca;");

		/*
		  Création d'un rectangle affichant la couleur et d'un rectangle affichant le niveau de gris correspondant
		 */
		recColor = new Rectangle(305, 25);
		recGray = new Rectangle(305, 25);
		StackPane stackColor = new StackPane();
		StackPane stackGray = new StackPane();

		/*
			Création d'une info-bulle informant que la couleur a bien été copiée.
		 */
		tooltip.setText("Code couleur copié !");

		/*
			Ajout de la couleur et du niveau de gris aux rectangles.
		 */
		recColor.setFill(color);
		recGray.setFill(color.grayscale());

		/*
			Ajout du code couleur en RGB
		 */
	    titleRGB.setText("RGB : "+Math.round(color.getRed()*255)
	        +", "+Math.round(color.getGreen()*255)
	        +", "+Math.round(color.getBlue()*255));
		/*
			Ajout du code couleur en hexadécimal
		 */
	    titleRGBHexa.setText(color.toString().replace("0x","#").toUpperCase());
		/*
			Copie lors du clic sur le texte
		 */
        titleRGBHexa.setOnMouseClicked(e -> {
            content.putString(color.toString().replace("0x","#").toUpperCase());
			clipboard.setContent(content);
			Tooltip.install(titleRGBHexa, tooltip);
        });

		/*
			Ajout du code couleur en HSB
		 */
	    titleHSB.setText("HSB : "+Math.round(color.getHue())
	        +", "+Math.round(color.getSaturation()*255)
	        +", "+Math.round(color.getBrightness()*255));
		/*
			Ajout du code couleur en hexadécimal
		 */
	    titleHSBHexa.setText(color.grayscale().toString().replace("0x","#").toUpperCase());
		/*
			Copie lors du clic sur le texte
		 */
        titleHSBHexa.setOnMouseClicked(e -> {
            content.putString(color.grayscale().toString().replace("0x","#").toUpperCase());
			clipboard.setContent(content);
			Tooltip.install(titleHSBHexa, tooltip);
        });

		/*
			VBox contenant les valeurs RGB et HSB
		 */
		VBox vboxTitle = new VBox();
	    titleRGB.setFont(Font.font("Verdana", 12));
	    titleRGB.setFill(Color.GREEN);
		titleHSB.setFont(Font.font("Verdana", 12));
		titleHSB.setFill(Color.RED);
		vboxTitle.getChildren().addAll(titleRGB, titleHSB);

		/*
			StackPane permettant l'affichage du texte dans les rectangles
		 */
		stackColor.getChildren().addAll(recColor, titleRGBHexa);
		stackGray.getChildren().addAll(recGray, titleHSBHexa);

		/*
		  Ajout des éléments au noeud
		 */
	    hbox.getChildren().addAll(stackColor, stackGray, vboxTitle);

		return hbox;
	}
}