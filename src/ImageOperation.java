import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

//CLASA ABSTRACTA
public abstract class ImageOperation { 
	abstract BufferedImage ReadFileName();

}
//PRIMUL NIVEL DE MOSTENIRE
class ImageRead extends ImageOperation {
	
	Path AbsolutePath = Paths.get("C:\\Users\\Sergiu\\eclipse-workspace\\ProiectAWJ\\src");
	BufferedImage image = null;
	File f = null;
	File file = null;
	int height;
	int width;
	
	BufferedImage ReadFileName() {
		//citirea imaginii cu sistem de prindere al erorilor
		try {
			//Timpul la care se porneste procesarea imaginii
			
			System.out.print("Calea absoluta a proiectului: ");
			System.out.println(AbsolutePath.toString());
			System.out.println();
			System.out.println("Introduceti numele imaginii ce doriti sa fie convertita: ");
			//Numele imaginii originale
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	        String name = reader.readLine();
			f = new File(AbsolutePath.toString() + "\\Input\\" +  name);
	        //Obiectul imagine
			image = ImageIO.read(f);
			
			System.out.println("\nScrierea imaginii ORIGINALE s-a realizat cu succes! ");
		} catch(IOException e) {
			System.out.println("EROARE! Asigurati-va ca ati scris numele si extensia corect! " + e);
		}
		//dimensiunea imaginii
		width = image.getWidth();
		height = image.getHeight();
		return image;
	}
}
//AL DOILEA NIVEL DE MOSTENIRE
class ConvertImageToGrayscale extends ImageRead{
	
	BufferedImage image;
	
	BufferedImage ReceiveOriginalImage() {
		long writeTime = System.currentTimeMillis();
		//citirea imaginii cu sistem de prindere al erorilor
		ImageRead obj3 = new ImageRead();
		image = obj3.ReadFileName();
		long timeElapsed = System.currentTimeMillis();
		System.out.println("Timp executie stadiu citirea si scrierea imaginii ORIGINALE: " + (timeElapsed - writeTime) +"ms\n");
		return image;
	}
	
	BufferedImage ConvertToGrayscale(BufferedImage image) {
		// CONVERSIA GRAYSCALE
		// vom prelucra fiecare pixel al imaginii in transformarea grayscale
		// imaginea este impartita in RGB si transparenta(alpha)
		for(int i = 0; i < image.getHeight(); ++i){
			for(int j = 0; j < image.getWidth(); ++j){
				// p este pixelul de pe pozitia i,j
				// continand valori independente pt campurile R G B pe 24 biti
				int p = image.getRGB(j,i);
			
				//primii 8 biti reprezinta valoarea de la 0 la 255 a lui RED
				int r = (p>>16)&0xff; //0xff reprez 255 in hexa
				
				//a 2-a secventa de 8 biti reprezinta valoarea de la 0 la 255 a lui GREEN
				int g = (p>>8)&0xff;
				
				//a 3-a secventa 8 biti reprezinta valoarea de la 0 la 255 a lui BLUE
				int b = p & 0xff;
				
				int avg = (r + g + b)/3; //calculeaza media celor 3 valori
				
				//inlocuieste valoarea RGB cu avg
				p = (avg << 16) | (avg << 8) | avg;
				
				image.setRGB(j, i, p);
			}
		}
		return image;
	}
	
	File WriteGrayscaleToFile(BufferedImage image) {
		//scrierea imaginii in fisier cu sistem de prindere al erorilor
		try {
			
			file = new File(AbsolutePath.toString() + "\\Output\\" +  "Grayscale.bmp");
			ImageIO.write(image, "bmp", file);
			System.out.println("Scrierea imaginii GRAYSCALE s-a realizat cu succes!");
		}catch(IOException e) {
			System.out.println("EROARE! Scrierea imaginii GRAYSCALE a esuat! " + e);
		}
		return file;
	}
}