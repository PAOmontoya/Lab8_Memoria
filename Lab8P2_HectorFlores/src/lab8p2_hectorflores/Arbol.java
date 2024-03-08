/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab8p2_hectorflores;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Comparator;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author hecto
 */
public class Arbol {
    
    JTree arbol;
    
    //Constructor para definir el arbol en swing de donde se tomaran los datos 
    //(incluir al crear objeto tipo Arbol para llamar metodos)
    public Arbol(JTree arbol){
        this.arbol = arbol;
    }
    
    //Los siguientes 4 metodos reciben el directorio del cual se desea ordenar los archivos dentro
    
    public DefaultMutableTreeNode ordenarPorNombre (File file){
        DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(file.getName());
        
        if (file.isDirectory()){
            File [] archivos = file.listFiles();
            
            if (archivos != null){
                Arrays.sort(archivos, (file1, file2) -> file.getName().compareToIgnoreCase(file2.getName()));
                
                for (File child : archivos){
                    nodo.add(ordenarPorNombre(child));
                }
            }
        }
        
        return nodo;
    }
    
    public DefaultMutableTreeNode ordenarPorFecha (File file){
        DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(file.getName());
        
        if (file.isDirectory()){
            File [] archivos = file.listFiles();
            
            if (archivos != null){
                Arrays.sort(archivos, (file1, file2) -> Long.compare(file1.lastModified(), file2.lastModified()));
                
                for (File child : archivos){
                    nodo.add(ordenarPorNombre(child));
                }
            }
        }
        
        return nodo;
    }

    public DefaultMutableTreeNode ordenarPorTipo (File file) {
        DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(file.getName());

        if (file.isDirectory()) {

            File[] files = file.listFiles();

            if (files != null) {

                Arrays.sort(files, new Comparator<File>() {
                    @Override
                    public int compare(File archivo1, File archivo2) {

                        if (archivo1.isDirectory() && !archivo2.isDirectory()) {

                            return -1;

                        } else if (!archivo1.isDirectory() && archivo2.isDirectory()) {
                            return 1;
                        } else if (archivo1.isDirectory() && archivo2.isDirectory()) {
                            return archivo1.getName().compareToIgnoreCase(archivo2.getName());
                        } else {

                            String extension1 = extendionDe(archivo1);
                            String extension2 = extendionDe(archivo2);

                            int compararExtension = extension1.compareToIgnoreCase(extension2);

                            if (compararExtension != 0) {
                                return compararExtension;
                            } else {

                                return archivo1.getName().compareToIgnoreCase(archivo2.getName());
                            }
                        }
                    }

                    private String extendionDe(File file) {

                        String nombreFile = file.getName();

                        int lastIndexOf = nombreFile.lastIndexOf(".");

                        if (lastIndexOf == -1) {
                            return "";
                        }

                        return nombreFile.substring(lastIndexOf + 1);
                    }
                });

                for (File childFile : files) {
                    nodo.add(ordenarPorTipo(childFile));
                }
            }
        }

        return nodo;
    }
    
    public DefaultMutableTreeNode ordenarPorSize (File file){
        DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(file.getName());
        
        if (file.isDirectory()){
            File [] archivos = file.listFiles();
            
            if (archivos != null){
                Arrays.sort(archivos, new Comparator<File>(){
                    @Override
                    public int compare(File archivo1, File archivo2) {
                        
                        if (archivo1.isDirectory() && !archivo2.isDirectory()) {
                            
                            return -1;
                            
                        } else if (!archivo1.isDirectory() && archivo2.isDirectory()) {
                            
                            return 1;
                            
                        } else if (archivo1.isDirectory() && archivo2.isDirectory()) {
                            
                            return archivo1.getName().compareToIgnoreCase(archivo2.getName());
                            
                        } else {
                            
                            long size1 = archivo1.length();
                            long size2 = archivo2.length();
                            
                            return Long.compare(size1, size2);
                        }
                    }
                });
                
                for (File child : archivos){
                    nodo.add(ordenarPorSize(child));
                }
            }
        }
        
        return nodo;
    }
    
    //Recibe el nodo seleccionado y el path de la carpeta
    public DefaultMutableTreeNode crearCarpeta (DefaultMutableTreeNode nodoSeleccionado, String pathDeCarpeta){
        
        File folder = new File(pathDeCarpeta);
        
        folder.mkdirs();
        
        DefaultMutableTreeNode nodoNuevo = new DefaultMutableTreeNode(folder.getName());
        
        nodoSeleccionado.add(nodoNuevo);
        
        return nodoSeleccionado;
    }
    
    public DefaultMutableTreeNode crearUnArchivo (DefaultMutableTreeNode nodeSeleccionado, String path, String extension)  throws IOException{
        if (extension.equals("txt")){
            crearFile(nodeSeleccionado, path);
        } else {
            crearCustomFile(nodeSeleccionado, path);
        }
        
        return null;
    }
    
    public DefaultMutableTreeNode crearFile (DefaultMutableTreeNode nodoSeleccionado, String pathDeArchivoTXT) throws IOException{
        if (pathDeArchivoTXT != null && !pathDeArchivoTXT.isEmpty()){
            File archivo = new File (pathDeArchivoTXT);
            archivo.createNewFile();

            DefaultMutableTreeNode nodoNuevo = new DefaultMutableTreeNode(pathDeArchivoTXT);

            nodoSeleccionado.add(nodoNuevo);
            
            return nodoSeleccionado;
        }
        
        return null;
    }
    
    //Recibe el nodo seleccionado y el path del Archivo
    public DefaultMutableTreeNode crearCustomFile (DefaultMutableTreeNode nodoSeleccionado, String pathDeArchivoComercial) throws IOException{
       
        if (pathDeArchivoComercial != null && !pathDeArchivoComercial.isEmpty()){
            RandomAccessFile comercial = new RandomAccessFile(pathDeArchivoComercial, "rw");
        
            DefaultMutableTreeNode nodoNuevo = new DefaultMutableTreeNode(pathDeArchivoComercial);

            nodoSeleccionado.add(nodoNuevo);

            return nodoSeleccionado;
        }
        
        return null;
    }
    
    //Recibe el path del  archivo que debe venir del nodo seleccionado desde el swing
    public void writeText (String path) throws IOException{
        String extension = path.substring(path.lastIndexOf(".") + 1);
        
        File targetFile = new File (path);
        
        if (extension.equals("txt") && targetFile.isFile()){ 
            String datosAEscribir = JOptionPane.showInputDialog("Ingrese lo que va a escribir en el archivo:");
            
            FileWriter writer = new FileWriter(targetFile);
            
            writer.write(datosAEscribir);
            writer.close();
            
        } else {
            JOptionPane.showMessageDialog(null, "El archivo debe de ser un archivo con extension .txt");
        }
    }
}
