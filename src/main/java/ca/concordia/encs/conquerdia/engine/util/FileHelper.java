package ca.concordia.encs.conquerdia.engine.util;

import org.apache.commons.io.FilenameUtils;

/**
 * Provides various helper method for storage system
 * 
 * @author Mosabbir
 */
public class FileHelper {

    /**
     * Returns the absolute path of the resource
     *
     * @param resource The resource file name
     * @return The full path to the resource
     */
    public static String getResourcePath(String resource) {
        return ClassLoader.getSystemClassLoader().getResource(resource).getPath();
    }

    /**
     * Combines two strings with a separator
     *
     * @param parent The name of the parent folder
     * @param child  the name of the folder or file which is under the parent
     * @return The combination of parent and child forming a path
     */
    public static String combinePath(String parent, String child) {
        return parent + "/" + child;
    }

    /**
     * Gets the base name, minus the full path and extension, from a full filename.
     * <p>
     * This method will handle a file in either Unix or Windows format.The text
     * after the last forward or backslash and before the last dot is returned.
     * a/b/c.txt --> c a.txt --> a a/b/c --> c a/b/c/ --> ""
     * <p>
     * The output will be the same irrespective of the machine that the code is
     * running on.
     *
     * @param filename the filename to query, null returns null
     * @return the name of the file without the path, or an empty string if none
     * exists
     */
    public static String getFileNameWithoutExtension(String filename) {
        return FilenameUtils.getBaseName(filename);
    }
}
