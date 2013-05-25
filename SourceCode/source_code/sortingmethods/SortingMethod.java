package sortingmethods;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import steps.Step;
import utils.Logging;

public abstract class SortingMethod {
   public SortingMethod() {
   }

   //Sorting Classes will implement this method
   public abstract Step[][] solve(int ai[]);

   public String getName() {
      return getClass().getSimpleName();
   }

   public static ArrayList<SortingMethod> getAllSortingMethods() {
      ArrayList<SortingMethod> result = new ArrayList<SortingMethod>();
      Logging.debug("ClassPath= " + System.getProperty("java.class.path"));
      ArrayList<String> PossibleSortingMethodChildNames = new ArrayList<String>();
      // TODO Applet Testing, possible hard code class names
      for (String part : System.getProperty("java.class.path").split(";")) {
         // Is Classpath part a Jar or a Folder
         Logging
               .debug((new StringBuilder("Scanning ")).append(part).toString());
         if (part.endsWith(".jar")) {
            JarFile jarFile;
            try {
               jarFile = new JarFile(part);
               Enumeration entires = jarFile.entries();
               while (entires.hasMoreElements()) {
                  String entryInJar = ((JarEntry) entires.nextElement())
                        .toString().replace('/', '.').replace('\\', '.');
                  if (entryInJar.contains(".class")) {
                     entryInJar = entryInJar.substring(0, entryInJar
                           .indexOf(".class"));
                     if (entryInJar.length() != 0)
                        PossibleSortingMethodChildNames.add(entryInJar);
                  }
               }
            } catch (IOException e) {
               Logging.errorException(e);
            }
         } else {
            ArrayList<String> filePaths = new ArrayList<String>();
            File rootFolder = new File(part);
            ArrayList<File> folders = new ArrayList<File>();
            if (rootFolder.isDirectory()) {
               folders.add(rootFolder);
            }
            while (!folders.isEmpty()) {
               File folder = folders.get(0);
               folders.remove(0);
               for (File f : folder.listFiles()) {
                  if (f.isDirectory()) {
                     folders.add(f);
                  } else {
                     filePaths.add(f.getPath());
                  }
               }
            }
            String rootFolderPath = rootFolder.getPath();
            for (String filePath : filePaths) {
               if (filePath.contains(rootFolderPath)) {
                  String entryInFolder = filePath.substring(
                        filePath.indexOf(rootFolderPath)
                              + rootFolderPath.length()).replace('/', '.')
                        .replace('\\', '.');
                  if (entryInFolder.indexOf('.') == 0) {
                     entryInFolder = entryInFolder.substring(1);
                  }
                  if (entryInFolder.contains(".class")) {
                     entryInFolder = entryInFolder.substring(0, entryInFolder
                           .indexOf(".class"));
                     if (entryInFolder.length() != 0)
                        PossibleSortingMethodChildNames.add(entryInFolder);
                  }
               }
            }
         }
      }
      for (String className : PossibleSortingMethodChildNames) {
         try {
            Class c = SortingMethod.class.getClassLoader().loadClass(className);
            if (c.getSuperclass() != null
                  && c.getSuperclass().getCanonicalName().equals(
                        SortingMethod.class.getCanonicalName())) {
               Object o = c.newInstance();
               if (o instanceof SortingMethod) {
                  SortingMethod s = (SortingMethod) o;
                  result.add((SortingMethod) o);
               }
            }
         } catch (Throwable t) {
            Logging.debugException(t);
         }
      }
      Logging.debug("Found " + result.size() + " Sorting Methods");
      return result;
   }
}
