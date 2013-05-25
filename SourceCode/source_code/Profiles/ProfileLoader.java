package Profiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import utils.Logging;
import values.constant.FolderSettings;

//TODO Loading Profiles From Jar
public class ProfileLoader {
   private static ArrayList<Profile> theProfiles = new ArrayList<Profile>();

   public static void add(Profile toAdd) {
      for (Profile p : theProfiles) {
         if (p.equal(toAdd)) {
            Logging.info(toAdd + " Already Exists");
            return;
         }
      }
      theProfiles.add(toAdd);
   }

   public static final void save() {
      File folder = new File(FolderSettings.PROFILE_FOLDER);
      if (!folder.exists()) {
         try {
            folder.createNewFile();
         } catch (IOException e) {
            Logging.errorException(e);
         }
      }
      for (File f : folder.listFiles()) {
         f.delete();
      }
      if (folder.isDirectory()) {
         int i = 0;
         for (Profile p : theProfiles) {
            File dest = new File(folder, i + ".profile");
            FileOutputStream fos = null;
            ObjectOutputStream out = null;
            try {
               fos = new FileOutputStream(dest);
               out = new ObjectOutputStream(fos);
               out.writeObject(p);
            } catch (IOException ex) {
               Logging.errorException(ex);
            } finally {
               if (out != null) {
                  try {
                     out.close();
                  } catch (IOException e) {
                     Logging.errorException(e);
                  }
               }
               if (fos != null) {
                  try {
                     fos.close();
                  } catch (IOException e) {
                     Logging.errorException(e);
                  }
               }
            }
         }
      }
   }

   public static void load() {
      try {
         File folder = new File(FolderSettings.PROFILE_FOLDER);
         if (folder.isDirectory()) {
            for (File f : folder.listFiles()) {
               FileInputStream fis;
               ObjectInputStream in;
               fis = new FileInputStream(f);
               in = new ObjectInputStream(fis);
               try {
                  Profile p = (Profile) in.readObject();
                  add(p);
               } catch (ClassNotFoundException e) {
                  Logging.errorException(e);
               } catch (IOException ex) {
                  Logging.errorException(ex);
               } finally {
                  if (in != null) {
                     try {
                        in.close();
                     } catch (IOException e) {
                        Logging.errorException(e);
                     }
                  }
                  if (fis != null) {
                     try {
                        fis.close();
                     } catch (IOException e) {
                        Logging.errorException(e);
                     }
                  }
               }
            }
         }
      } catch (IOException ex) {
         Logging.errorException(ex);
      }
   }
}
