//Pentru crearea cailor
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

//INTERFATA de stergere a continutului unui fisier
public interface DeleteFolder { 
	Path AbsolutePath = Paths.get("C:\\Users\\Sergiu\\eclipse-workspace\\ProiectAWJ\\src");
}

// CLASA pentru stergerea continutului fisierului Output
class DeleteFolderOutput implements DeleteFolder { 
	public void OutputDelete() {

		//Golirea fisierului de Output
		File OutputFolder = new File(AbsolutePath.toString() + "\\Output\\");
		File[] files1 = OutputFolder.listFiles();
		for(File fileDelete: files1) fileDelete.delete();
		
	}
}
// CLASA pentru stergerea continutului fisierului Verificare
class DeleteFolderVerificare implements DeleteFolder {
	public void VerificareDelete() {
		
		//Golirea fisierului de Verificare
		File VerificareFolder = new File(AbsolutePath.toString() + "\\Verificare\\");
		File[] files2 = VerificareFolder.listFiles();
		for(File fileDelete: files2) fileDelete.delete();	
	}
}
