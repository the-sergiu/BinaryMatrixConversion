// Pentru operatii de importare a unui fisier
import java.io.File;
//Pentru operatii pe fisier de tip imagine (IMAGINE->GRAYSCALE)
import java.awt.Graphics2D;
import java.awt.Image;
//Pentru a scrie/citi ByteArray-uri (codificare/decodificare)
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
//Pentru citire/scriere in fisier
import java.io.FileInputStream;
import java.io.FileOutputStream;
//Pentru prinderea erorilor
import java.io.IOException;
//Pentru masurarea timpului de executie
//import java.util.concurrent.TimeUnit;

//PENTRU CONVERSIA BYTE ARRAY -> IMAGINE (VERIFICARE)
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
//Pentru crearea cailor
import java.nio.file.Path;
import java.nio.file.Paths;
//Pentru citirea de la consola
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
import java.util.Scanner;

//TEMA NUMARUL 9 - Convert Gray-Scale Image to Binary image (static threshold)

//AL TREILEA NIVEL DE MOSTENIRE
public class Main extends ConvertImageToGrayscale{
	
	public static void main(String[] args) throws IOException  {		
		
		//Inceperea monitorizarii timpului de executie
		long startTime = System.currentTimeMillis();
		//Preluarea numelui complet al utilizatorului
		System.out.println("Introduceti numele complet al utilizatorului:");
		Scanner in = new Scanner(System.in);
	    String str = in.nextLine();
	    String[] strArr = str.split(" ");
	    //Functie varargs - afisare nume
	    Intro(strArr);
		
		//Calea absoluta a surselor
		Path AbsolutePath = Paths.get("C:\\Users\\Sergiu\\eclipse-workspace\\ProiectAWJ\\src");
		
		//Golirea fisierului de Output
		DeleteFolderOutput obj = new DeleteFolderOutput();
		obj.OutputDelete();
		
		//Golirea fisierului de Verificare
		DeleteFolderVerificare obj2 = new DeleteFolderVerificare();
		obj2.VerificareDelete();
		
	
		//Cream un obiect de tip clasa Grayscale pentru a putea stoca imaginea transformata
		ConvertImageToGrayscale obj3 = new ConvertImageToGrayscale();
		//Citim imaginea originala
		BufferedImage image = obj3.ReceiveOriginalImage();
		
		//Inceputul operatiei de conversie Grayscale
		long transfTime = System.currentTimeMillis();
		
		// Conversia la Grayscale a imaginii originale
		image = obj3.ConvertToGrayscale(image);
		//Scrierea in fisier a transformarii
		File file = obj3.WriteGrayscaleToFile(image);
		
		//Sfaristul operatiei de conversie Grayscale
		long timeElapsed = System.currentTimeMillis();
		System.out.println("Timp executie transformare imaginii originale in Grayscale: " + (timeElapsed - transfTime) + "ms\n");
		
		//Inceputul conversiei din Grayscale la fisier binar
		long convTime = System.nanoTime();
		//CONVERSIE IMAGINE -> BYTE ARRAY 
		FileInputStream fis = new FileInputStream(file);
		//Creeaza FileInputStream care obtine octetii de input dintr-un fisier al sistemului
		//FileInputStream e folosit pentru citirea datelor neprelucrate precum octetii unei imagini.
		
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                //Writes to this byte array output stream
                bos.write(buf, 0, readNum); 
            }
           
            System.out.print("Conversia din GRAYSCALE la BYTE_ARRAY s-a realizat cu succes!\n" );
            
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        byte[] bytes = bos.toByteArray();
      //Sfarstiul operatiei de transformare dintr-o imagine la o matrice binara
        long timeElapsed2 = System.nanoTime();
        System.out.println("Timp de scrierea imaginii Grayscale in matrice binara: " + (timeElapsed2 - convTime) + "ns\n");
        
        System.out.println("Specificati numele fisierului de output impreuna cu extensia sa (se recomanda .bin):");
        Scanner scanner = new Scanner(System.in);
        String inputString = scanner.nextLine();
        //Scriem ByteArray in Fisier
        File someFile = new File(AbsolutePath.toString() + "\\Output\\" + inputString);
        FileOutputStream fos = new FileOutputStream(someFile);
        fos.write(bytes);
        fos.flush();
        fos.close();
        
        //VERIFICARE //////////////////////
        
        //CONVERSIE BYTE ARRAY -> IMAGINE
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        
        //ImageIO e o clasa ce contine metode statice pentru localizarea ImageReaders
        //si ImageWriters, care efectueaza operatii simple de codificare/decodificare
        Iterator<?> readers = ImageIO.getImageReadersByFormatName("bmp");
        
        
        ImageReader reader = (ImageReader) readers.next();
        Object source = bis; 
        ImageInputStream iis = ImageIO.createImageInputStream(source); 
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
 
        //primeste un fisier de tip imagine
        Image imageConverted = reader.read(0, param);
       
        //bufferedImage este RenderedImage care urmeaza sa fie scrisa
        BufferedImage bufferedImage = new BufferedImage(imageConverted.getWidth(null), imageConverted.getHeight(null), BufferedImage.TYPE_INT_RGB);
        
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.drawImage(imageConverted, null, null);
 
        try {
        	File imageFile = new File(AbsolutePath.toString() + "\\Verificare\\" + "ConversionBack.bmp");
            ImageIO.write(bufferedImage, "bmp", imageFile);
            System.out.println("VERIFICARE: Conversia din BYTE_ARRAY la GRAYSCALE s-a realizat cu succes!");
            //Afisam calea
            System.out.println();
            System.out.println("VERIFICARE: Imaginea reconstruita din BYTE_ARRAY in GRAYSCALE se afla la calea: ");
            System.out.println(imageFile.getPath());
		}catch(IOException e) {
			System.out.println("EROARE! Verificarea pentru conversia din BYTE_ARRAY in GRAYSCALE a esuat! " + e);
		}
        
        //Sfarsitul monitorizarii executiei programului
        long endTime = System.currentTimeMillis();
		
		System.out.println("\nTimp executie totala a programului: " + (endTime - startTime) + "ms\n");	
	}
	
	//Functie ce utilizeaza varargs
	//Preia numele complet al utilizatorului, indiferent de numarul de parametrii (string-uri)
	static void Intro(String ...a)
	{
		System.out.print("Buna, ");
		for(String i: a)
			System.out.print(i + " ");
		System.out.println("!\n");
	}

}
