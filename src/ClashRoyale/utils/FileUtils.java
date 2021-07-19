package ClashRoyale.utils;

import java.io.*;

public class FileUtils {
    public void save(String name, Object data){

        try{
            File file = new File(name);
            FileOutputStream pos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(pos);
            oos.writeObject(data);
            pos.close();
            oos.close();

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void delete(String name){
        File file = new File(name);
        file.delete();
    }
    public Object load(String name) {
        Object res = null;
        File file = new File(name);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            res = objectInputStream.readObject() ;

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return res;
    }


}
