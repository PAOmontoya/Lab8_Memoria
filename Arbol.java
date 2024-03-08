/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab8p2_hectorflores;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author hecto
 */
public class Arbol {
    
    JTree arbol;
    
    public Arbol(JTree arbol){
        this.arbol = arbol;
    }
    
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

    public DefaultMutableTreeNode organizarPorTipo (File file) {
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
                    nodo.add(organizarPorTipo(childFile));
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
    
}
