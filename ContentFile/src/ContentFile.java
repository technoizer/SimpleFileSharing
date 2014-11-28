
import java.io.Serializable;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Muhammad Izzuddin
 */
public class ContentFile implements Serializable{
    private byte [] byteFile;
    private String namafile;
    private int ukuran;
    public ContentFile(int ukuran){
        this.byteFile = new byte [ukuran];
    }

    /**
     * @return the byteFile
     */
    public byte[] getByteFile() {
        return byteFile;
    }

    /**
     * @param byteFile the byteFile to set
     */
    public void setByteFile(byte[] byteFile) {
        this.byteFile = byteFile;
    }

    /**
     * @return the namafile
     */
    public String getNamafile() {
        return namafile;
    }

    /**
     * @param namafile the namafile to set
     */
    public void setNamafile(String namafile) {
        this.namafile = namafile;
    }

    /**
     * @return the ukuran
     */
    public int getUkuran() {
        return ukuran;
    }

    /**
     * @param ukuran the ukuran to set
     */
    public void setUkuran(int ukuran) {
        this.ukuran = ukuran;
    }
}
