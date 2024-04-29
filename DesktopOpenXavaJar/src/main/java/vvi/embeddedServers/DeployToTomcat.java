package vvi.embeddedServers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;



/**
 * @author vvi
 */
public class DeployToTomcat {

	/**
	 * Copies application {@code .war} file from resources to current working folder.
	 * <p>The {@code .war} file must have the name defined by {@link vvi.runOpenXava.RunWithServers#APP_NAME}

	 * @param appName name of the web application 
	 * @param skipExistingFile if true, do not override existing file
	 * @throws IOException
	 */
	public void deployToWorkingFolder(String appName, boolean skipExistingFile) throws IOException {
		String currentPath = Paths.get("").toAbsolutePath().toString();
		String warFileName = appName + ".war";
		File warFileToRun = new File(currentPath, warFileName);
		if(warFileToRun.exists()) {
			System.out.println(warFileName + " is already in working folder");
			if(skipExistingFile) {
				System.out.println("Existing file is skipped");
				return;
			} else {
				System.out.println("Existing file will be overwritten");
			}
		}
		System.out.println("Copying " + warFileName);
		Path destinationPath = warFileToRun.toPath();
		copyFileFromResources(warFileName, destinationPath);
	}

	
	private void copyFileFromResources(String warFileName, Path destinationPath) throws IOException {
		InputStream fileAsIOStream = getFileAsIOStream(warFileName);
	    java.nio.file.Files.copy(
	    		fileAsIOStream, 
	    		destinationPath, 
	    	      StandardCopyOption.REPLACE_EXISTING);
	    fileAsIOStream.close();
	}

	
    private   InputStream getFileAsIOStream(final String fileName) {
        InputStream ioStream = this.getClass()
            .getClassLoader()
            .getResourceAsStream(fileName);
        
        if (ioStream == null) {
            throw new IllegalArgumentException(fileName + " is not found");
        }
        return ioStream;
    }
	
}
